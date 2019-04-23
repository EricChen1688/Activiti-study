package cn.eccto.activiti.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 19:03
 */
public class TaskServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("cn/eccto/activiti/test/api/activiti.cfg.xml");


//    public ProcessEngine processEngine;
//    public RuntimeService runtimeService ;
//    public TaskService taskService;
//    public FormService formService;
//    public RepositoryService repositoryService;
//    public IdentityService identityService;
//    public HistoryService historyService;


//    @Before
//    public void init(){
//        processEngine = activitiRule.getProcessEngine();
//        runtimeService = activitiRule.getRuntimeService();
//        taskService = activitiRule.getTaskService();
//        formService = activitiRule.getFormService();
//        repositoryService = activitiRule.getRepositoryService();
//        identityService = activitiRule.getIdentityService();
//        historyService = activitiRule.getHistoryService();
//    }

    /**
     * 测试使用 变量
     */
    @Test
    public void testStartProcess() {

        //部署流程定义文件
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("task/test_service_variable.xml");
        deploymentBuilder.deploy();
        ProcessInstance instance = activitiRule.getRuntimeService().startProcessInstanceByKey("second_approve");
        TaskService taskService = activitiRule.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            LOGGER.info("task = {}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
            LOGGER.info("task.description = {}", task.getDescription());
        }
        String taskId = "";


    }

    /**
     * 测试变量替换
     */
    @Test
    @Deployment(resources = {"cn/eccto/activiti/test/api/task_service_variable_bpmn20.xml"})
    public void testVarableReplace() {
        HashMap<String, Object> var = Maps.newHashMap();
        var.put("message", "这是一个测试信息");
        var.put("key1", "ecTestValue");
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey("ec_test", var);
        TaskService taskService = activitiRule.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            LOGGER.info("task : {}", ToStringBuilder.reflectionToString(task, ToStringStyle.JSON_STYLE));
            LOGGER.info("task description : {}", task.getDescription());
        }

    }

    /**
     * 测试变量可见性
     */
    @Test
    @Deployment(resources = {"cn/eccto/activiti/test/api/task_service_variable2_bpmn20.xml"})
    public void testVarable2() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey("ec_test");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        //测试全局变量
        taskService.setVariable(task.getId(), "public", "publicV");
        //测试本地变量
        taskService.setVariableLocal(task.getId(), "local", "localV");

        Map<String, Object> variables = taskService.getVariables(task.getId());
        Map<String, Object> variablesLocal = taskService.getVariablesLocal(task.getId());

        Map<String, Object> variables1 = runtimeService.getVariables(task.getExecutionId());
        Map<String, Object> processVariables = instance.getProcessVariables();
        LOGGER.info("到达userTask1 后获取变量{}", variables);
        LOGGER.info("到达userTask2 后获取本地变量 {}", variablesLocal);
        LOGGER.info("到达userTask2 后获取Runtime 执行流对应的变量 {}", variables1);
        LOGGER.info("到达userTask2 后获取流程实例变量{}", processVariables);

        taskService.complete(task.getId());
        Task userTask2 = taskService.createTaskQuery().singleResult();
        Map<String, Object> variables2 = taskService.getVariables(userTask2.getId());
        Map<String, Object> variablesLocal2 = taskService.getVariablesLocal(userTask2.getId());

        Map<String, Object> variables22 = runtimeService.getVariables(userTask2.getExecutionId());
        Map<String, Object> processVariables2 = instance.getProcessVariables();
        LOGGER.info("到达userTask2 后获取变量{}", variables2);
        LOGGER.info("到达userTask2 后获取本地变量 {}", variablesLocal2);
        LOGGER.info("到达userTask2 后获取Runtime 执行流对应的变量 {}", variables22);
        LOGGER.info("到达userTask2 后获取流程实例变量{}", processVariables2);
    }

    /**
     * 测试评论
     */
    @Test
    @Deployment(resources = {"cn/eccto/activiti/test/api/task_service_comment_bpmn20.xml"})
    public void testComment() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey("ec_test");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        //存储 Comment
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "comment-1");
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "comment-2");

        //event 会记录包括 Comment 以内的其他所有操作

        taskService.createAttachment("url",
                task.getId(),
                task.getProcessInstanceId(),
                "my-attchment",
                "my-attachment-desc",
                "/url/test");

        taskService.setOwner(task.getId(), "EricChen");
        //获取评论
        List<Comment> taskComments = taskService.getTaskComments(task.getId());
        for (Comment comment : taskComments) {
            LOGGER.info("task comment -> {}", ToStringBuilder.reflectionToString(comment, ToStringStyle.JSON_STYLE));

        }
        List<Event> taskEvents = taskService.getTaskEvents(task.getId());
        for (Event event : taskEvents) {
            LOGGER.info("task event -> {}", ToStringBuilder.reflectionToString(event, ToStringStyle.JSON_STYLE));
        }
    }
}

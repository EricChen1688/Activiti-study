package cn.eccto.activiti.test.api;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
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
    public ActivitiRule activitiRule = new ActivitiRule();


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
        HashMap<String, Object> var = Maps.newHashMap();
        var.put("key1", "ecTestValue");
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
}

package cn.eccto.activiti.hello;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/20 15:25
 */
public class DemoMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        LOGGER.info("启动我们的程序");
        //创建流程引擎 ,基于内存的流程殷勤
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration();

        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;

        LOGGER.info("流程引擎名称:{} , 版本:{}", name, version);
        //部署流程定义文件
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        String path = DemoMain.class.getClassLoader().getResource("").getPath();
        LOGGER.info(path);
        deploymentBuilder.addClasspathResource("second_approve.bpmn20.xml");
        Deployment deploy = deploymentBuilder.deploy();
        String deployId = deploy.getId();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployId).singleResult();
        LOGGER.info("流程定义文件{},流程 ID:{}", processDefinition.getName(), processDefinition.getId());


        //启动流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动流程{},流程定义 Key 为{}", instance.getName(), instance.getProcessDefinitionKey());

        //处理流程任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        FormService formService = processEngine.getFormService();
        HashMap<String, Object> varable = Maps.newHashMap();
        for (Task task : list) {
            LOGGER.info("待处理的任务:{}", task.getName());
            TaskFormData taskFormData = formService.getTaskFormData(task.getId());
            List<FormProperty> formProperties = taskFormData.getFormProperties();
            for (int i = 0; i < formProperties.size(); i++) {
                LOGGER.info("解析第{}个属性开始", i);
                FormProperty formProperty = formProperties.get(i);
                String name1 = formProperty.getName();
                FormType type = formProperty.getType();
                String id = formProperty.getId();
                String value = formProperty.getValue();
                LOGGER.info(name1);
                LOGGER.info(type.getName());
                LOGGER.info(id);
                LOGGER.info(value);
                LOGGER.info("解析第{}个属性结束", i);
            }
            taskService.complete(task.getId());

        }
        LOGGER.info("待处理任务数量:{}", list.size());

        LOGGER.info("结束我们的程序");
    }
}

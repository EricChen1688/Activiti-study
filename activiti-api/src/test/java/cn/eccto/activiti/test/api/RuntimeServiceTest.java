package cn.eccto.activiti.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 14:17
 */
public class RuntimeServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();


    public RuntimeService runtimeService;
    public TaskService taskService;
    public FormService formService;
    public RepositoryService repositoryService;
    public IdentityService identityService;
    public HistoryService historyService;

    public RuntimeService getRuntimeService() {
        return activitiRule.getRuntimeService();
    }


    public TaskService getTaskService() {
        return activitiRule.getTaskService();
    }

    public FormService getFormService() {
        return activitiRule.getFormService();
    }

    public RepositoryService getRepositoryService() {
        return activitiRule.getRepositoryService();
    }

    public IdentityService getIdentityService() {
        return activitiRule.getIdentityService();
    }

    public HistoryService getHistoryService() {
        return activitiRule.getHistoryService();
    }

    /**
     * 测试使用 RuntimeService启动
     */
    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testStartProcess() {
        HashMap<String, Object> var = Maps.newHashMap();
        var.put("ecTestKey", "ecTestValue");
        RuntimeService runtimeService = getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("second_approve", var);

        //获取执行流
        List<Execution> executions = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .listPage(0, 100);
        for (Execution e : executions) {
            LOGGER.info("执行流 -> {}", e);
        }

    }

    /**
     * 测试使用 ProcessInstanceBuilder
     */
    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testProcessInstanceBuilder() {
        ProcessInstanceBuilder processInstanceBuilder = getRuntimeService()
                .createProcessInstanceBuilder();
        HashMap<String, Object> var = Maps.newHashMap();
        var.put("ecTestKey", "ecTestValue");
        ProcessInstance instance = processInstanceBuilder
                .businessKey("process-whatever")
                .processDefinitionKey("second_approve")
                .variables(var)
                .start();
        LOGGER.info("processInstance = {}", instance);
    }

    /**
     * 测试使用 变量获取
     */
    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testVarables() {
        ProcessInstanceBuilder processInstanceBuilder = getRuntimeService()
                .createProcessInstanceBuilder();
        HashMap<String, Object> var = Maps.newHashMap();
        var.put("ecTestKey", "ecTestValue");
        ProcessInstance instance = processInstanceBuilder
                .businessKey("process-whatever")
                .processDefinitionKey("second_approve")
                .variables(var)
                .start();
        LOGGER.info("processInstance = {}", instance);
        //查询流程执行对象
        Map<String, Object> variables = getRuntimeService().getVariables(instance.getId());
        LOGGER.info("varibale :{}", variables);
    }


    /**
     * 测试使用 trigger
     */
    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testTrigger() {
        ProcessInstance instance = getRuntimeService().startProcessInstanceByKey("ectest");
        //获取指定流并触发
        Execution execution = getRuntimeService().createExecutionQuery()
                .activityId("task1")
                .singleResult();
        LOGGER.info("execution -> {}", execution);
        getRuntimeService().trigger(execution.getId());
        //再次查询看看还有没有
        execution = getRuntimeService().createExecutionQuery()
                .activityId("task1")
                .singleResult();

        LOGGER.info("execution -> {}", execution);
    }


    /**
     * 测试使用 trigger
     */
    @Test
    @Deployment(resources = "second_approve.bpmn20.xml")
    public void testSingal() {
        ProcessInstance instance = getRuntimeService().startProcessInstanceByKey("ectest");
        //获取指定流并触发
        Execution execution = getRuntimeService().createExecutionQuery()
                .activityId("task1")
                .singleResult();
        LOGGER.info("execution -> {}", execution);
        getRuntimeService().trigger(execution.getId());
        //再次查询看看还有没有
        execution = getRuntimeService().createExecutionQuery()
                .activityId("task1")
                .singleResult();

        LOGGER.info("execution -> {}", execution);
    }


}

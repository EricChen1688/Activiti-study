package cn.eccto.activiti.config;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.logging.LogMDC;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/20 20:01
 */
public class ConfigMDCTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMDCTest.class);

    //测试test方法
//    @Rule
//    public ActivitiRule activitiRule = new ActivitiRule("");

    //    测试 testMDCInvoker 方法
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.mdc.cfg.xml");

    @Test
    @Deployment(resources = {"second_approve.bpmn20.xml"})
    public void test() {
        //开启 MDC
        LogMDC.setMDCEnabled(true);
        ProcessInstance processInstance = activitiRule
                .getRuntimeService()
                .startProcessInstanceByKey("second_approve");
        assert (processInstance != null);
        List<Task> taskList = activitiRule.getTaskService().createTaskQuery().list();
        TaskService taskService = activitiRule.getTaskService();
        for (Task task : taskList) {
            LOGGER.info("正常执行不会存储到 MDC : task name:{}", task.getName());
        }
    }

    @Test
    @Deployment(resources = {"second_approve.bpmn20.xml"})
    public void testMDCInvoker() {
        ProcessInstance processInstance = activitiRule
                .getRuntimeService()
                .startProcessInstanceByKey("second_approve");
        assert (processInstance != null);
        List<Task> taskList = activitiRule.getTaskService().createTaskQuery().list();
        List<EventLogEntry> eventLogEntries = activitiRule.getManagementService()
                .getEventLogEntriesByProcessInstanceId(processInstance.getProcessInstanceId());
    }
}

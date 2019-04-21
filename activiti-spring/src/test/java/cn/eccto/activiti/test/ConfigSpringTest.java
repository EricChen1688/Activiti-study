package cn.eccto.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 12:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:activiti-context.xml"})
public class ConfigSpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigSpringTest.class);

    @Autowired
    private ActivitiRule activitiRule;

    @Test
    public void test() {
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("second_approve.bpmn20.xml")
                .deploy();
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("second_approve");
        assert (processInstance != null);
    }

}

package cn.eccto.activiti.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/20 17:08
 */
public class ConfigTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);


    @Test
    public void testConfig1() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();
        LOGGER.info("" + configuration);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        LOGGER.info("获取流程引擎:{}", processEngine.getName());
        processEngine.close();
    }

}

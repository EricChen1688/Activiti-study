package cn.eccto.activiti.config;

import java.util.List;

import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/20 17:08
 */
public class ConfigJobTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigJobTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.job.cfg.xml");


    @Test
    @Deployment(resources = {"job.bpmn20.xml"})
    public void test() {
        List<Job> jobs = activitiRule.getManagementService().createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            LOGGER.info("EC测试-定时任务 :{} , 默认重试次数 {}", job, job.getRetries());
        }
        LOGGER.info("job 共有 {} 个", jobs.size());

    }

}

package cn.eccto.activiti.config;


import cn.eccto.activiti.config.event.CustomEventListener;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventImpl;
import org.activiti.engine.runtime.ProcessInstance;
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
public class ConfigInterceptorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigEventListenerTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.interceptor.cfg.xml");


    @Test
    @Deployment(resources = {"second_approve.bpmn20.xml"})
    public void test() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        runtimeService.addEventListener(new CustomEventListener());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("second_approve");
        assert (processInstance != null);

        //手动触发事件
        runtimeService.dispatchEvent(new ActivitiEventImpl(ActivitiEventType.CUSTOM));
    }
}

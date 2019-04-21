package cn.eccto.activiti.config.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 11:29
 */
public class JobEventListener implements ActivitiEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        //监听 JOB和 定时 Timer 事件
        String name = type.name();
        if (name.startsWith("TIMER") || name.startsWith("JOB")) {
            LOGGER.info("EC测试 : 流程定义类型 {} , 流程定义 ID :{}", type, event.getProcessDefinitionId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }


}

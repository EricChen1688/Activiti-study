package cn.eccto.activiti.config.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 08:55
 */
public class CustomEventListener implements ActivitiEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessEventListener.class);


    public void onEvent(ActivitiEvent event) {
        if (ActivitiEventType.CUSTOM.equals(event.getType())) {
            LOGGER.info("触发了自定义的监听器");
        }
    }

    public boolean isFailOnException() {
        return false;
    }
}

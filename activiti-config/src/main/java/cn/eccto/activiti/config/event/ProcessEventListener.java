package cn.eccto.activiti.config.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 流程监听器
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 08:31
 */
public class ProcessEventListener implements ActivitiEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessEventListener.class);


    /**
     * 开启
     *
     * @param event
     */
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        String executionId = event.getExecutionId();
        String processDefinitionId = event.getProcessDefinitionId();
        String processInstanceId = event.getProcessInstanceId();
        LOGGER.info("流程启动: type:[{}],executionId:[{}],processDefinitionId[{}],processInstanceId:[{}]"
                , type, executionId, processDefinitionId, processInstanceId);
    }

    /**
     * @return
     */
    public boolean isFailOnException() {
        return false;
    }
}

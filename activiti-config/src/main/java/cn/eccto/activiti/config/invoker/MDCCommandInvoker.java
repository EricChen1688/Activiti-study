package cn.eccto.activiti.config.invoker;

import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 */
public class MDCCommandInvoker extends DebugCommandInvoker {

    private static final Logger logger = LoggerFactory.getLogger(DebugCommandInvoker.class);

    @Override
    public void executeOperation(Runnable runnable) {
        //获取原设置
        boolean mdcEnabled = LogMDC.isMDCEnabled();
        LogMDC.setMDCEnabled(true);
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation) runnable;

            if (operation.getExecution() != null) {
                //记录可操作对象,放入 MDC 上下文信息
                LogMDC.putMDCExecution(operation.getExecution());
            }

        }
        super.executeOperation(runnable);
        //清空上下文
        LogMDC.clear();
        if (!mdcEnabled) {
            //还原原设置
            LogMDC.setMDCEnabled(false);
        }
    }

}

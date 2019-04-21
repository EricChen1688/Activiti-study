package cn.eccto.activiti.config.interceptor;


import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author ericchen.vip@foxmail.com 2019/04/21 10:14
 */
public class MyCommandInterceptor extends AbstractCommandInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCommandInterceptor.class);


    public <T> T execute(CommandConfig config, Command<T> command) {
        LOGGER.info("自定义的命令拦截器开始");
        try {
            return this.getNext().execute(config, command);
        } finally {
            LOGGER.info("自定义的命令拦截器结束");
        }
    }
}

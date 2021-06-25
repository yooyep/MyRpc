package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.NacosUtil;

/**
 * @author yooyep
 * @create 2021-06-25 15:36
 */
public class ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);
    // 单例创建对象
    private static final ShutdownHook shutdownHook = new ShutdownHook();
    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    /**
     * 服务关闭时，注销该服务器能提供的服务
     */
    public void addShutdownHook (){
        logger.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
        }));
    }

}

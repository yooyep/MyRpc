package registry;

import java.net.InetSocketAddress;

/**
 * 服务端 注册服务
 * @author yooyep
 * @create 2021-06-21 15:10
 */
public interface ServicesRegitry {
    /**
     * 将服务注册到注册表中
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}

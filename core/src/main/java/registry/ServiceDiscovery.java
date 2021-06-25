package registry;

import java.net.InetSocketAddress;

/**
 * 客户端 发现服务
 * @author yooyep
 * @create 2021-06-25 15:52
 */
public interface ServiceDiscovery {
    /**
     * 根据服务名称查找服务实体
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);
}

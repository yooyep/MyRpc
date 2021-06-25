package registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import enumeration.RpcError;
import exception.RpcException;
import loadbalance.LoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 管理nacos 服务的注册、注销
 * @author yooyep
 * @create 2021-06-25 15:09
 */
public class NacosUtil {
    private static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);
    private static final String NACOS_ADDR = "127.0.0.1:8848";

    private static final NamingService namingService;
    private static final Set<String> serviceNames = new HashSet<>();
    private static InetSocketAddress address;

    static {
        try {
            namingService = NamingFactory.createNamingService(NACOS_ADDR);
        } catch (NacosException e) {
            logger.info("连接到Nacos时有错误发生: ",e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public static NamingService getNamingService() {
        return namingService;
    }

    /**
     * 注册服务，并将服务名、ip地址记录下来，方便注销
     */
    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
        // 保存服务名、ip地址
        serviceNames.add(serviceName);
        NacosUtil.address = address;
    }


    /**
     * 当服务器关闭时，注销该服务器注册的服务（serviceNames）
     */
    public static void clearRegistry(){
        if (address != null && !serviceNames.isEmpty()){
            String host = address.getHostName();
            int port = address.getPort();
            Iterator<String> iterator = serviceNames.iterator();
            while (iterator.hasNext()) {
                String serviceName = iterator.next();
                try {
                    namingService.deregisterInstance(serviceName, host, port);
                    logger.info("注销服务 {} 成功", serviceName);
                } catch (NacosException e) {
                    logger.error("注销服务 {} 失败", serviceName, e);
                }
            }
        }
    }

}

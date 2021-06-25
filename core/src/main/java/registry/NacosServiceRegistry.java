package registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import enumeration.RpcError;
import exception.RpcException;
import loadbalance.LoadBalance;
import loadbalance.PollLoadBalance;
import loadbalance.RandomLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * nacos的服务注册中心、查找服务
 * @author yooyep
 * @create 2021-06-21 15:12
 */
public class NacosServiceRegistry implements ServicesRegitry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    /**
     * 将服务注册到注册表中
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName,inetSocketAddress);
        } catch (NacosException e) {
            logger.info("注册服务时有错误发生: ",e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }
}

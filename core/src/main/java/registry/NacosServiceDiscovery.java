package registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import loadbalance.LoadBalance;
import loadbalance.PollLoadBalance;
import loadbalance.RandomLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author yooyep
 * @create 2021-06-25 15:51
 */
public class NacosServiceDiscovery implements ServiceDiscovery{
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private final LoadBalance loadBalance;
    private static final NamingService namingService;

    public NacosServiceDiscovery(){
        loadBalance = new PollLoadBalance();
    }

    public NacosServiceDiscovery(LoadBalance loadBalance){
        if (loadBalance == null){
            this.loadBalance = new RandomLoadBalance();
        } else {
            this.loadBalance = loadBalance;
        }
    }

    static {
        namingService = NacosUtil.getNamingService();
    }

    /**
     * 查找能提供服务的服务器，并负载均衡选一个
     * @param serviceName 服务名称
     * @return
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            // 根据负载均衡策略 确定一个服务器
            Instance instance = loadBalance.select(allInstances);
//            Instance instance = allInstances.get(0);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        // 找不到对应的服务器，返回null
        return null;
    }
}

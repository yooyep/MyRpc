package loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡
 * @author yooyep
 * @create 2021-06-25 14:50
 */
public interface LoadBalance {
    Instance select(List<Instance> instances);
}

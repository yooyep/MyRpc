package loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡 - 随机
 * @author yooyep
 * @create 2021-06-25 14:51
 */
public class RandomLoadBalance implements LoadBalance{
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}

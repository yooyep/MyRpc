package loadbalance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡 - 轮询查找
 * @author yooyep
 * @create 2021-06-25 14:54
 */
public class PollLoadBalance implements LoadBalance{

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if (index >= instances.size()){
            // 超过 归零
            index = 0;
        }
        return instances.get(index++);
    }
}

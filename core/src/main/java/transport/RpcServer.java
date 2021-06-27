package transport;

import serializer.CommonSerializer;

/**
 * @author yooyep
 * @create 2021-06-18 17:42
 */
public interface RpcServer {
    void start();
    // 设置序列化机制
    void setSerializer(CommonSerializer serializer);
    /**
     * 注册 发布服务
     * @param service 服务实例
     * @param serviceClass 服务类
     * @param <T>
     */
    <T> void publishService(Object service, Class<T> serviceClass);
}

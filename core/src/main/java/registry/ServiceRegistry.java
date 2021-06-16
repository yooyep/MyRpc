package registry;

/**
 * @author yooyep
 * @create 2021-06-15 20:21
 */
public interface ServiceRegistry {
    /**
     * 将服务注册到注册表中
     * @param service 待注册的服务实体类
     * @param <T>
     */
    <T> void register(T service);

    /**
     * 根据服务名，找到服务实体
     * @param serviceName
     * @return
     */
    Object getService(String serviceName);
}

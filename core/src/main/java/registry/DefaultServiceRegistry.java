package registry;

import enumeration.RpcError;
import exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yooyep
 * @create 2021-06-15 20:23
 */
public class DefaultServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    private final Map<String,Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName))
            return;
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            //抛出找不到对应服务
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}

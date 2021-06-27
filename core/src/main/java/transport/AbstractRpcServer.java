package transport;

import annotation.Service;
import annotation.ServiceScan;
import enumeration.RpcError;
import exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.ServiceProvider;
import registry.ServicesRegitry;
import transport.netty.client.NettyClient;
import util.ReflectUtil;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author yooyep
 * @create 2021-06-26 21:04
 */
public abstract class AbstractRpcServer implements RpcServer{
    protected Logger logger = LoggerFactory.getLogger(NettyClient.class);

    protected String host;
    protected int port;

    protected ServicesRegitry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices(){
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass = null;
        try {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class)) {
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            logger.error("未知类");
        }
        // 1.获取scan的包 路径
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackage)) {
            // 没有包路径，默认启动类的所在包
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        // 2.看各个类，是否有@Service注解；有则发布服务
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for (Class<?> clazz : classSet) {
            if (clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object service;
                try {
                    service = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("创建类实例" + clazz + "失败");
                    continue;
                }
                if ("".equals(serviceName)){
                    // 没有指定name，则用接口名发布服务
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        publishService(service, anInterface);
                    }
                } else {
                    // 如果是按规定的serviceName注册，那rpcRequest里面 也要动态修改；
                    // 暂时放这
//                    publishService(service, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        // 向本地注册表中，注册接口 - 服务
        serviceProvider.addServiceProvider(service);
        // 向注册中心，注册服务 - 地址
        serviceRegistry.register(serviceClass.getCanonicalName(),new InetSocketAddress(host,port));
    }
}

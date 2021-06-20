import cjc.api.HelloService;
import registry.DefaultServiceRegistry;
import netty.server.NettyServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
public class TestNettyServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();
        defaultServiceRegistry.register(helloService); //向注册中心 注册服务

        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9000);
    }
}

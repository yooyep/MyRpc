import cjc.api.HelloService;
import registry.DefaultServiceRegistry;
import socket.server.SocketServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
public class TestSocketServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();
        defaultServiceRegistry.register(helloService); //向注册中心 注册服务

        SocketServer rpcServer = new SocketServer(defaultServiceRegistry);
        rpcServer.start(9000);
    }
}

package test;

import cjc.api.HelloService;
import provider.ServiceProviderImpl;
import transport.socket.server.SocketServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
public class TestSocketServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProviderImpl defaultServiceRegistry = new ServiceProviderImpl();
        defaultServiceRegistry.addServiceProvider(helloService); //向注册中心 注册服务

        SocketServer rpcServer = new SocketServer("127.0.0.1",9000,defaultServiceRegistry);
        rpcServer.start();
    }
}

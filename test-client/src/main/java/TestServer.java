import cjc.api.HelloService;
import server.RpcServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}

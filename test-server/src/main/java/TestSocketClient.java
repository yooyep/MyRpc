import cjc.api.HelloObject;
import cjc.api.HelloService;
import client.RpcClientProxy;
import client.SocketClient;

/**
 * @author yooyep
 * @create 2021-06-14 16:04
 */
public class TestSocketClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(8818, "this is a message");
        // rpc远程调用
        String result = helloService.hello(object);
        System.out.println(result);
    }
}

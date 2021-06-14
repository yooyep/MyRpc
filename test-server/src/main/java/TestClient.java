import cjc.api.HelloObject;
import cjc.api.HelloService;
import client.RpcClientProxy;

/**
 * @author yooyep
 * @create 2021-06-14 16:04
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(888, "this is a message");
        String result = helloService.hello(object);
        System.out.println(result);
    }
}

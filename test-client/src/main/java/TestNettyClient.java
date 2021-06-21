import cjc.api.HelloObject;
import cjc.api.HelloService;
import serializer.KryoSerializer;
import transport.RpcClientProxy;
import transport.netty.client.NettyClient;

/**
 * @author yooyep
 * @create 2021-06-18 17:39
 */
public class TestNettyClient {
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.setSerializer(new KryoSerializer()); //设置 序列化
        RpcClientProxy proxy = new RpcClientProxy(nettyClient);

        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(888, "this is a message");
        // rpc远程调用
        String result = helloService.hello(object);
        System.out.println(result);
    }
}

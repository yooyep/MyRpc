import cjc.api.HelloService;
import serializer.KryoSerializer;
import transport.netty.server.NettyServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
public class TestNettyServer2 {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();

        NettyServer nettyServer = new NettyServer("127.0.0.1",9001);
        nettyServer.setSerializer(new KryoSerializer()); //设置 序列化机制
        nettyServer.publishService(helloService, HelloService.class);
        nettyServer.start();
    }
}

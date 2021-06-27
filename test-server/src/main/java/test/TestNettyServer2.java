package test;

import annotation.ServiceScan;
import cjc.api.HelloService;
import serializer.KryoSerializer;
import transport.netty.server.NettyServer;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
@ServiceScan
public class TestNettyServer2 {
    public static void main(String[] args) {
//        HelloService helloService = new test.HelloServiceImpl();
//
//        NettyServer nettyServer = new NettyServer("127.0.0.1",9000);
//        nettyServer.setSerializer(new KryoSerializer()); //设置 序列化机制
//        nettyServer.publishService(helloService, HelloService.class);
//        nettyServer.start();
        NettyServer nettyServer = new NettyServer("127.0.0.1", 9001);
        nettyServer.start();
    }
}

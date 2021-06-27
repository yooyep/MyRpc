package test;

import annotation.ServiceScan;
import cjc.api.HelloService;
import serializer.KryoSerializer;
import transport.netty.server.NettyServer;
import provider.ServiceProviderImpl;

/**
 * @author yooyep
 * @create 2021-06-14 15:57
 */
@ServiceScan
public class TestNettyServer {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer("127.0.0.1", 9000);
        nettyServer.start();
    }
}

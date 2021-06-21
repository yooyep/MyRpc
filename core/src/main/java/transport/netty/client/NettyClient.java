package transport.netty.client;


import codec.CommonDecoder;
import codec.CommonEncoder;
import enumeration.RpcError;
import exception.RpcException;
import registry.NacosServiceRegistry;
import registry.ServicesRegitry;
import serializer.CommonSerializer;
import transport.RpcClient;
import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializer.KryoSerializer;

import java.net.InetSocketAddress;

/**
 * NIO方式 构建客户端
 * @author yooyep
 * @create 2021-06-18 14:14
 */
public class NettyClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static final Bootstrap bootstrap;
    private static final ServicesRegitry servicesRegitry = new NacosServiceRegistry();
    private static CommonSerializer serializer;

    static {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                // 开启tcp底层的心跳机制
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder());
                        pipeline.addLast(new CommonEncoder(serializer));
//                        pipeline.addLast(new CommonEncoder(new JsonSerializer()));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
    }

    /**
     * 向服务器发送rpcRequest，返回Object为response.getData()
     * @param rpcRequest
     * @return
     */
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }

        try {
            InetSocketAddress inetSocketAddress = servicesRegitry.lookupService(rpcRequest.getInterfaceName());
            ChannelFuture future = bootstrap.connect(inetSocketAddress.getHostName(), inetSocketAddress.getPort()).sync();
//            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务器（注册版） {}:{}", inetSocketAddress.getHostName(), inetSocketAddress.getPort());
            Channel channel = future.channel();
            if (channel != null){
                // 客户端 发送消息
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        logger.info(String.format("netty客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();

                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                // 返回服务器响应体的data数据
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}

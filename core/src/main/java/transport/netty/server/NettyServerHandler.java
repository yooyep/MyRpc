package transport.netty.server;

import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.RequestHandler;
import provider.ServiceProvider;
import provider.ServiceProviderImpl;


/**
 * 处理传输过来的RpcRequest 的Handler
 * @author yooyep
 * @create 2021-06-18 17:30
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceProvider serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new ServiceProviderImpl();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务器接收到请求: {}", msg.toString());
            // 根据接口名 获取 已注册的服务
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            // 动态代理 调用方法
            Object result = requestHandler.handle(msg, service);
            // 发送回 调用的结果
            RpcResponse<Object> response = RpcResponse.success(result);
            ChannelFuture future = ctx.writeAndFlush(response);
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}

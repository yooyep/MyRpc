package codec;

import entity.RpcRequest;
import enumeration.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import serializer.CommonSerializer;

import javax.print.DocFlavor;

/**
 * 编码器
 * @author yooyep
 * @create 2021-06-18 14:35
 */
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer){
        this.serializer = serializer;
    }

    /*
        进行编码
        1.魔数 2.标记（请求or响应） 3.序列化器的编号 4.data长度 5.data
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(MAGIC_NUMBER);
        // 2.区分请求体、响应体
        if (obj instanceof RpcRequest){
            byteBuf.writeInt(PackageType.REQUEST.getCode());
        } else {
            byteBuf.writeInt(PackageType.RESPONSE.getCode());
        }
        byteBuf.writeInt(serializer.getCode());
        byte[] data = serializer.serialize(obj); //序列化 data
        byteBuf.writeInt(data.length);
        // 5.写入数据
        byteBuf.writeBytes(data);
    }
}

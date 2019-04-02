package com.common.coder;

import com.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author cai
 * 编码器
 *
 * */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz){
        this.clazz =clazz;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if(clazz.isInstance(o)){
            byte[] bytes = SerializationUtil.serialize(o);
            byteBuf.writeByte(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}

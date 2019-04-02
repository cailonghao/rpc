package com.common.coder;

import com.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author cai
 * 解码器
 * */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcDecoder(Class<?> clazz){
        this.clazz =clazz;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<4){
            return;
        }
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();
        if(byteBuf.readableBytes()<len){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[len];
        byteBuf.readBytes(data);
        list.add(SerializationUtil.deserialize(data,clazz));
    }
}

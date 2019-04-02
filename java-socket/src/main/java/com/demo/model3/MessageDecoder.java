package com.demo.model3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author toms
 * 消息解碼
 * */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    private static final int HEADER_SIZE = 5;
    /** 數據類型**/
    private byte type;
    /**消息長度*/
    private int len;
    /**要发送的数据*/
    private String data;
    /**
     *
     * @param maxFrameLength   网络字节序，默认为大端字节序
     * @param lengthFieldOffset 消息中长度字段偏移的字节数
     * @param lengthFieldLength 数据帧的最大长度
     * @param lengthAdjustment 该字段加长度字段等于数据帧的长度
     * @param initialBytesToStrip 从数据帧中跳过的字节数
     */
    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if(in == null){
            return null;
        }
        if(in.readableBytes()<=HEADER_SIZE){
            return null;
        }
        type = in.readByte();
        len = in.readByte();
        if(in.readableBytes()<len){
            throw new Exception();
        }
        ByteBuf buf = in.readBytes(len);
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        data = new String(bytes,"UTF-8");
        Message message = new Message(type,len,data);
        return message;
    }
}

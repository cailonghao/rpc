package com.demo.model3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @author toms
 * 編碼器
 * */
public class ClientEncode extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        System.out.println("-------- 編碼-----------");
        if(message == null ){
            throw new Exception("NULL");
        }
        String data = message.toString();
        byte[] body = data.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeByte(message.getType());
        byteBuf.writeByte(body.length);
        byteBuf.writeBytes(body);
    }
}

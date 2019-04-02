package com.demo.model3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author toms
 *   一个最常用的Handler。这个Handler的作用就是处理接收到数据时的事件，
 *  也就是说，我们的业务逻辑一般就是写在这个Handler里面的
 * */
public class ServerHandle extends ChannelInboundHandlerAdapter {

    private int count=1;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Message){
            Message message = (Message) msg;
            System.out.println(message);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}

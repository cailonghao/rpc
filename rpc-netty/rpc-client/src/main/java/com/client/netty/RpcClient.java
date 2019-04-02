package com.client.netty;

import com.common.bean.RpcRequest;
import com.common.bean.RpcResponse;
import com.common.coder.RpcDecoder;
import com.common.coder.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 客户端
 */
@Slf4j
public class RpcClient {

    /**
     *消息发布
     */
    public RpcResponse init() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //解码
                        pipeline.addLast(new RpcDecoder(RpcResponse.class));
                        //编码
                        pipeline.addLast(new RpcEncoder(RpcRequest.class));
                        //自定义处理类
                        pipeline.addLast(new RpcHandle(rpcResponse));
                    }
                })
        .option(ChannelOption.TCP_NODELAY,true);
        try {
            ChannelFuture future = bootstrap.connect(host,port).sync();
            Channel channel = future.channel();
            channel.writeAndFlush(rpcRequest);
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return rpcResponse;
    }
    /**
     * 配置handle
     * ChannelInboundHandlerAdapter
     */
    class RpcHandle extends SimpleChannelInboundHandler<RpcResponse>{

        private RpcResponse rpcResponse;

        public RpcHandle(RpcResponse rpcResponse){
            this.rpcResponse = rpcResponse;
        }
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
            this.rpcResponse=rpcResponse;
        }
    }

}

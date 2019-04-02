package com.server.netty;

import com.common.bean.RpcRequest;
import com.common.bean.RpcResponse;
import com.common.coder.RpcDecoder;
import com.common.coder.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cai
 * rpc服务端
 */
@Slf4j
public class RpcServer {
    /**单例*/
    private RpcServer(){}

    private static class RpcServerInstance{
        private static final RpcServer rpcServer= new RpcServer();
    }
    public static RpcServer getInstance(){
        return RpcServerInstance.rpcServer;
    }
    /**
     * 启动服务
     */
    public void init(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //解码
                        pipeline.addLast(new RpcDecoder(RpcResponse.class));
                        //编码
                        pipeline.addLast(new RpcEncoder(RpcRequest.class));
                    }
                })
                .option(ChannelOption.SO_BACKLOG,100)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
        }catch (Exception e){

        }
    }
    /**
     * 业务类
     * */
    class ServerHandle extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error(cause.getMessage());
        }
    }
}

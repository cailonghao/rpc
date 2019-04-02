package com.demo.model3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author toms
 * 服务器端
 */
public class NettyServer {

    /**
     * 启动服务
     */
    public void init() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInit())
                //服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                .option(ChannelOption.SO_BACKLOG, 128)
                //是否启用心跳保活机制
                .childOption(ChannelOption.SO_KEEPALIVE,true);

        try {
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     *
     */
    class ServerChannelInit extends ChannelInitializer<SocketChannel> {
        /**
         * 最大長度
         */
        private int MAX_FRAME_LENGTH = 1024 * 1024;
        /**
         * Message类中的length的长度，int占4位
         */
        private int LENGTH_FIELD_LENGTH = 4;
        /**
         * 偏移多少位之后才是我们的消息体
         */
        private int LENGTH_FIELD_OFFSET = 4;
        /**
         * 数据帧的长度
         */
        private int LENGTH_ADJUSTMENT = 0;
        /**
         * 跳过数据帧中的字节数
         */
        private int INITIAL_BYTES_TO_STRIP = 0;

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            //解码与编码
            pipeline.addLast(new MessageDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP));
            //自己的逻辑handle
            pipeline.addLast("handler", new ServerHandle());
        }
    }

    public static void main(String[] args) {
        new NettyServer().init();
    }
}

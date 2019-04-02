package com.demo.model2;

import com.demo.model1.Pool;
import com.demo.model1.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 文件通道
 */
public class MyChannel {

    private Pool pool = new Pool();

    /**
     * 启动服务器
     */
    public void init() throws IOException, InterruptedException {
        System.out.println("NIO服务器启动,绑定8090端口...");
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(8080));
        //非阻塞
        serverSocket.configureBlocking(false);
        //注册选择器
        Selector selector = Selector.open();
        //接收请求
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        Handle handle = new Handle(1024);
        while (true) {
            //轮询
            if (selector.select() > 0) {
                //获取待处理的选择键集合
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    //判断是什么请求,选择对应的处理方法
                    if (selectionKey.isAcceptable()) {
                        handle.accept(selectionKey);
                    }
                    //读请求
                    if (selectionKey.isReadable()) {
                        handle.read(selectionKey);
                        keyIterator.remove();
                    }
                }
            }
        }
    }


    /**
     * 业务类
     */
    class Handle implements Runnable {

        private int bufferSize;

        public Handle(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public void accept(SelectionKey selectionKey) throws IOException {
            //获取连接
            SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            //设置为非阻塞模式
            socketChannel.configureBlocking(false);
            //注册到服务器上
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }
        public void read(SelectionKey selectionKey) throws IOException {
            SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            int len = 0;
            while ((len = socketChannel.read(buffer))>0){
                buffer.flip();
                byte[] bytes = new byte[len];
                System.out.println(new String(bytes,0,len));
                buffer.clear();
            }
        }

        /**
         * 业务方法
         */
        @Override
        public void run() {

        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //服务器启动
        new Server().init();
    }
}

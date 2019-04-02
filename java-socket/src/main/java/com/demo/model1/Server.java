package com.demo.model1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @author toms
 * */
public class Server{

    private Pool pool = new Pool();
    /**
     * 启动服务器
     * */
    public void init() throws IOException, InterruptedException {
        System.out.println("服务器启动...");
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            //为每个客户端开一个线程
            Socket socket = serverSocket.accept();
            Jieshou jieshou = new Jieshou(socket.getInputStream(),socket.getOutputStream());
            pool.execute(jieshou);
        }
    }


    /**
     * 业务类
     * */
    class Jieshou implements Runnable{


        private InputStream inputStream;
        private OutputStream outputStream;

        public Jieshou(InputStream inputStream, OutputStream outputStream){
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }
        /**
         * 业务方法
         * */
        @Override
        public void run() {
            try {
                byte[] bytes = new byte[1024];
                int len = inputStream.read(bytes);
                String message = new String(bytes,0,len);
                System.out.println(message);
                outputStream.write("i get you".getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //服务器启动
        Server server = new Server();
        server.init();
    }

}

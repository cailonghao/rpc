package com.demo.model1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author toms
 * */
public class Client implements Runnable{
    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1",8080);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hi socket server".getBytes());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            String message = new String(bytes,0,len);
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
}

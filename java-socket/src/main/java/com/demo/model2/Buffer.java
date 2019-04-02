package com.demo.model2;

import java.nio.ByteBuffer;
/**
 * @author toms
 * 缓冲区
 * */
public class Buffer {

    public static void main(String[] args) {
        //获取缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
       /* System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");*/
        //存放数据
        buffer.put("shazan".getBytes());
        /*System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");*/
        //开启读模式
        buffer.flip();
        byte[] bytes1 = new byte[buffer.limit()];
        buffer.get(bytes1);
        System.out.println(new String(bytes1,0,bytes1.length));
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");
        //重复读
        /*buffer.rewind();
        byte[] bytes2 = new byte[buffer.limit()];
        buffer.get(bytes2);
        System.out.println(new String(bytes2,0,bytes2.length));
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");*/
        //清空缓冲区,还能读到
       /* buffer.clear();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");;*/
        //标记
        buffer.rewind();
        byte[] bytes3 = new byte[buffer.limit()];
        buffer.get(bytes3,0,2);
        System.out.println(new String(bytes3,0,bytes3.length));
        System.out.println(buffer.position());
        buffer.get(bytes3,2,2);
        System.out.println(new String(bytes3,0,bytes3.length));
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.position()+"----------");
    }
}

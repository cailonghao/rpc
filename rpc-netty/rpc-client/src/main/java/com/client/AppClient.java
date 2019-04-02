package com.client;

import com.registry.consumer.RpcConsumer;

/**
 * @author cai
 * 启动类
 * */
public class AppClient {

    public void start(){
        RpcConsumer consumer = new RpcConsumer();
        String host = consumer.consumer("cainiao");
        System.out.println(host);
    }
    public static void main(String[] args) {
        AppClient appClient = new AppClient();
        appClient.start();
    }
}

package com.server;

import com.api.RpcService;
import com.registry.provider.RpcProvider;
import com.server.netty.RpcServer;
import com.server.service.RpcServiceImpl;

/**
 * @author cai
 * 服务端启动类
 * */
public class AppServer {
    private RpcProvider provider = new RpcProvider();
    private RpcServer server = RpcServer.getInstance();
    public AppServer(){
        provider.provider("cainiao","127.0.0.1:9000");
        server.init(9000);
    }
    /**
     * 测试方法
     * */
    public void start(){
        RpcService service = new RpcServiceImpl();
        service.add(1);
    }
    /**
     * 启动服务
     * */
    public static void main(String[] args) {
        System.out.println("服务器已启动...");
        AppServer app = new AppServer();
        app.start();
    }
}

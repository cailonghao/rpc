package com.client.proxy;
/**
 * @author cai
 * rpc代理
 * */
public class RpcProxy {

    private String address;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String address){
        this.address =address;
    }
    public RpcProxy(ServiceDiscovery serviceDiscovery){
        this.serviceDiscovery=serviceDiscovery;
    }
    /*public <T> T create(Class<T> clazz){
        return
    }*/
    class ServiceDiscovery{

    }
}

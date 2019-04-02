package com.client.discovery;

import com.registry.consumer.RpcConsumer;

/**
 *服务发现
 * */
public class RpcDiscovery {

    private RpcConsumer consumer;

    public RpcDiscovery() {
        consumer = new RpcConsumer();
    }
    public void init(String node){
        String host = consumer.consumer(node);
    }

}

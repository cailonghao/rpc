package com.registry.consumer;

import com.registry.zookeeper.RpcZookeeper;

/**
 * @author cai
 * 消费节点
 */
public class RpcConsumer {
    /**
     * 查看节点
     * */
    public String consumer(String node) {
        RpcZookeeper zookeeper = RpcZookeeper.getInstance();
        String path = "/"+node;
        return (String) zookeeper.get(path);
    }
}

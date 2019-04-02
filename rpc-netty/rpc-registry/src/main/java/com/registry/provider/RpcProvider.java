package com.registry.provider;

import com.registry.zookeeper.RpcZookeeper;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author cai
 * 注册服务
 */
public class RpcProvider {

    /**
     * 注册节点
     *提交节点之前的数据处理
     * @param node
     * @param object
     */
    public void provider(String node, Object object) {
        RpcZookeeper zookeeper = RpcZookeeper.getInstance();
        String path = "/"+node;
        if (object == null) {
            zookeeper.createPersistent(path);
        }
        zookeeper.createPersistent(path, object);
    }
}

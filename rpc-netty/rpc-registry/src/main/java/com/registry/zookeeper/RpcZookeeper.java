package com.registry.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author cai
 * zookeeper
 * */
@Slf4j
public class RpcZookeeper {
    /**单例*/
    private RpcZookeeper(){
        zkClient = new ZkClient(host,5000,5000);
    }

    private static class RpcServerInstance{
        private static final RpcZookeeper rpcServer= new RpcZookeeper();
    }
    public static RpcZookeeper getInstance(){
        return RpcServerInstance.rpcServer;
    }

    private static String host;
    private static String file = "rpc.properties";
    private ZkClient zkClient;

    static {
        try {
            InputStream inputStream = RpcZookeeper.class.getClassLoader().getResourceAsStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            host = properties.getProperty("host");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**创建永久节点*/
    public boolean createPersistent(String node,Object object){
        try {
            zkClient.createPersistent(node,object);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
    /**创建永久节点*/
    public boolean createPersistent(String node){
        try {
            zkClient.createPersistent(node);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
    /**创建临时节点*/
    public boolean createEphemeral(String node,Object object){
        try {
            zkClient.createEphemeral(node,object);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
    /**创建临时节点 无值*/
    public boolean createEphemeral(String node){
        try {
            zkClient.createEphemeral(node);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**删除节点*/
    public boolean delete(String node){
        try {
            zkClient.delete(node);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**修改节点*/
    public boolean update(String node,Object object){
        try {
            zkClient.writeData(node,object);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**是否存在*/
    public boolean exists(String node){
        try {
            zkClient.exists(node);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**读取节点*/
    public Object get(String node){
        Object object = null;
        try {
             object = zkClient.readData(node);
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }
    /**监听节点数据*/
    public void listenData(String node){
        zkClient.subscribeDataChanges(node, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点数据变化");
                log .info("节点变化");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("节点数据删除");
            }
        });
    };
    /**监听节点状态*/
    public void listenState(){
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                System.out.println("节点状态");
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("节点已断开");
            }
        });
    }
}

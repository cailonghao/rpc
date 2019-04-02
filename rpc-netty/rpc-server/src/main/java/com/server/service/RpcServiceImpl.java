package com.server.service;


import com.api.RpcService;

/**
 * @author cai
 * 实现类
 * */
public class RpcServiceImpl implements RpcService {
    @Override
    public void add(int id) {
        System.out.println("hello netty"+id);
    }
}

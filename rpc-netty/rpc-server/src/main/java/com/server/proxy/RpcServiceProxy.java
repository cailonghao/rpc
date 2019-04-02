package com.server.proxy;

import com.registry.provider.RpcProvider;
import com.server.service.RpcServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author cai
 * ldk动态代理
 * */
public class RpcServiceProxy<T> implements InvocationHandler {

    private RpcProvider provider = new RpcProvider();
    private Object target;

    public RpcServiceProxy(Object target) {
        this.target = target;
    }
    /**
     * 注册信息
     * */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*Class<?> clazz = method.getDeclaringClass();
        provider.provider("cainiao",clazz.getName());*/
        Object object = method.invoke(target,args);
        return object;
    }
    public Object newInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

}

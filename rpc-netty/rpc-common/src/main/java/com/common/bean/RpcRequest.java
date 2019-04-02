package com.common.bean;

import lombok.Data;

@Data
public class RpcRequest {
    /** 请求id*/
    private String requestID;
    /** 接口*/
    private String interfaceName;
    /** 服务版本*/
    private String serviceVersion;
    /** 方法名称*/
    private String methodName;
    /** 参数类型*/
    private Class<?> paramterTypes;
    /** 参数*/
    private Object[] parameters;
}

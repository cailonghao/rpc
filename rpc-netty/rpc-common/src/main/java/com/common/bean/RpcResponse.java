package com.common.bean;

import lombok.Data;

/**
 * @author cai
 * */
@Data
public class RpcResponse {

    /**请求id*/
    private String requestId;
    /**异常*/
    private Exception exception;
    /**返回值*/
    private Object result;
}

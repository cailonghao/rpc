package com.demo.model3;

import lombok.Data;

/**
 * @author toms
 * */
public class Message {

    /** 數據類型**/
    private byte type;
    /**消息長度*/
    private int len;
    /**要发送的数据*/
    private String data;

    public Message(byte type, int len, String data) {
        this.type =type;
        this.len = len;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", len=" + len +
                ", data='" + data + '\'' +
                '}';
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

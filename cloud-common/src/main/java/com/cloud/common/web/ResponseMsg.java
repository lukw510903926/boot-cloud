package com.cloud.common.web;

import java.io.Serializable;

/**
 * 消息
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-22 19:52
 **/
public class ResponseMsg implements Serializable {

    private static final long serialVersionUID = -8986972597184092775L;
    private int status;

    private String msg;

    public ResponseMsg(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
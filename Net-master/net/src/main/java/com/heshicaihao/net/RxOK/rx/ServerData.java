package com.heshicaihao.net.RxOK.rx;

/**
 * 服务器返回数据根节点
 * Created by lanlong on 2017/9/28.
 */

public class ServerData {
    private int code;
    private Object data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

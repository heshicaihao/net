package com.heshicaihao.net.RxOK.protocol;


import com.heshicaihao.net.RxOK.exception.ServerApiException;

/**
 * 聚合数据协议
 */

public class JuHeProtocol<T> implements BaseProtocol<T> {
    private int error_code;
    private String reason;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getError_code() {

        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Object startProtocol() {
        return handleResult(getError_code(), getReason());
    }

    protected Object handleResult(int result, String message) {
        if (result == 0) {
            return getResult();
        } else {
            return new ServerApiException(result, message);

        }
    }
}

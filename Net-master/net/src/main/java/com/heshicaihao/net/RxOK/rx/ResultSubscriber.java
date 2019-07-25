package com.heshicaihao.net.RxOK.rx;

import com.google.gson.JsonSyntaxException;
import com.heshicaihao.net.RxOK.exception.ServerApiException;
import com.heshicaihao.net.RxOK.netconstants.MyConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 封装的订阅模式,只返回处理结果
 */
public abstract class ResultSubscriber<T> extends Subscriber<T> {
    /**
     * 可供子类复写
     * 不是必须实现的
     */
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();  //打印异常信息
        ErrorBean errorBean = new ErrorBean();
        if (e instanceof HttpException) {  //网络异常
            int code = ((HttpException) e).code();
            if (code == 500) {
                errorBean.setErrMessage("服务器内部异常，加载数据失败");
            } else {
                errorBean.setErrMessage("加载数据失败");
            }
            errorBean.setErrCode(MyConstants.ServerCode.NET_ERR);
        } else if (e instanceof SocketTimeoutException) {
            errorBean.setErrCode(MyConstants.ServerCode.FAIL);
            errorBean.setErrMessage("链接超时");
        } else if (e instanceof JsonSyntaxException) {  //解析异常
            errorBean.setErrCode(MyConstants.ServerCode.FAIL);
            errorBean.setErrMessage("数据解析异常");
        } else if (e instanceof ServerApiException) {
            errorBean.setErrCode(((ServerApiException) e).getCode());
            errorBean.setErrMessage(e.getMessage());
        } else if (e instanceof ConnectException) {
            errorBean.setErrCode(MyConstants.ServerCode.NET_ERR);
            errorBean.setErrMessage("当前网络不可用，请检查网络");
        } else if (e instanceof UnknownHostException) {
            errorBean.setErrCode(MyConstants.ServerCode.NET_ERR);
            errorBean.setErrMessage("当前网络不可用，请检查网络");
        } else {
            errorBean.setErrCode(MyConstants.ServerCode.FAIL);
            errorBean.setErrMessage("未知异常错误");
        }

        onError(errorBean);
    }

    @Override
    public void onNext(T t) {
        onData(t);
    }

    public abstract void onError(ErrorBean errorBean);

    public abstract void onData(T t);

}

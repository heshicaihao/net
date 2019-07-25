package com.heshicaihao.net.RxOK.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 增加共有头部请求
 */
public class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                //         .addHeader("content-type", "application/json; charset = utf-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept", "*/*")
                .build();
        return chain.proceed(request);
    }
}

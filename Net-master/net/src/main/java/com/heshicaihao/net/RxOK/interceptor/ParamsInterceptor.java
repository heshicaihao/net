package com.heshicaihao.net.RxOK.interceptor;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 参数拦截器(添加共有参数)
 */
public class ParamsInterceptor implements Interceptor {

    private HashMap<String, String> mMap;

    public ParamsInterceptor() {
        mMap = new HashMap<>();
    }

    public ParamsInterceptor(String key, String value) {
        this();
        mMap.put(key, value);
    }

    @SuppressWarnings("unused")
    public ParamsInterceptor(HashMap hashMap) {
        this();
        mMap.putAll(hashMap);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        HttpUrl.Builder builder = originalRequest.url().newBuilder();

        //originalRequest.url().url
        if (originalRequest.method().equals("GET")) {
            for (String key : mMap.keySet()) {
                builder.addQueryParameter(key, mMap.get(key));
            }
        }
        request = originalRequest.newBuilder().url(builder.build()).build();
        return chain.proceed(request);
    }
}

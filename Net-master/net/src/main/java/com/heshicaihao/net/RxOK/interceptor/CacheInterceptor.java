package com.heshicaihao.net.RxOK.interceptor;

import android.content.Context;

import com.heshicaihao.net.RxOK.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 */
public class CacheInterceptor implements Interceptor {

    private int cacheTime = 0;
    private Context context;

    public CacheInterceptor(Context context, int cacheTime) {
        this.cacheTime = cacheTime;
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isConnect = NetWorkUtils.isNetworkAvailable(context);
        //强制缓存,所有接口
        if (!isConnect) {
            CacheControl cacheControl = request.cacheControl();
            //CacheControl.FORCE_CACHE
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response response = chain.proceed(request);
        if (!isConnect) {
            // 有网络时 缓存时间以缓存头的为主
            String cacheControl = request.cacheControl().toString();
            response = response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时，以自身的缓存头处理
            String cacheControl = request.cacheControl().toString();
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        }
        return response;
    }
}

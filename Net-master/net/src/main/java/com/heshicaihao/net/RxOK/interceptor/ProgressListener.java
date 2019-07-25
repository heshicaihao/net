package com.heshicaihao.net.RxOK.interceptor;

public interface ProgressListener {
    void progress(long bytesRead, long contentLength, boolean done);
}

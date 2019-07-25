package com.heshicaihao.net.RxOK.mvp;


import com.heshicaihao.net.RxOK.rx.ErrorBean;

/**
 */

public interface BaseView<P> {
    /**
     * 绑定presenter
     */
//    void setPresenter(P presenter);

    /**
     * 显示出错信息
     */
    void onLoadErrorInfo(ErrorBean bean);
}

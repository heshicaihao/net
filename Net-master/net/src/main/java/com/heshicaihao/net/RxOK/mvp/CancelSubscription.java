package com.heshicaihao.net.RxOK.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 处理rxjava的数据,取消网络访问
 */
public abstract class CancelSubscription {
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }

    /**
     * RxJava取消注册，以避免内存泄露
     */
    public void onUnSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}

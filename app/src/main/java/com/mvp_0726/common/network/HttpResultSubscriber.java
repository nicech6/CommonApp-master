package com.mvp_0726.common.network;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created  on 2018-03-20.
 * author:seven
 * email:seven2016s@163.com
 */

public abstract class HttpResultSubscriber<T> implements Subscriber<T> {
    protected abstract void onLoading(Disposable d);

    protected abstract void onSuccess(T o);

    protected abstract void onFail(Exception e);


    @Override
    public void onNext(T tResponseCustom) {
        onSuccess(tResponseCustom);
    }

    @Override
    public void onError(Throwable e) {
        onFail((Exception) e);
//        if (e instanceof Exception) {
//        } else {
//            this.onFail(new ApiException(e, UN_KNOWN_ERROR));
//        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }
}

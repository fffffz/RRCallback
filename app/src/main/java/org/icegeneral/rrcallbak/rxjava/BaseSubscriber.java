package org.icegeneral.rrcallbak.rxjava;

import android.content.Context;
import android.widget.Toast;

import org.icegeneral.rrcallbak.retrofit.DX168Exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;

/**
 * Created by jianjun.lin on 16/7/14.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    protected Context context;

    public BaseSubscriber() {

    }

    public BaseSubscriber(Context applicationContext) {
        this.context = applicationContext.getApplicationContext();
    }

    @Override
    public void onError(Throwable throwable) {
        Throwable e = throwable;
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            onNetworkException(e);
        } else if (e instanceof DX168Exception) {
            onDX168Exception((DX168Exception) e);
        } else {
            onUnknownException(e);
        }
    }

    @Override
    public abstract void onNext(T t);


    @Override
    public void onCompleted() {

    }

    public void onDX168Exception(DX168Exception e) {
        e.printStackTrace();
        if (context != null) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onNetworkException(Throwable e) {
        if (context != null) {
            Toast.makeText(context, "网络较慢，请稍候...", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUnknownException(Throwable e) {
        e.printStackTrace();
        if (context != null) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
package org.icegeneral.rrcallbak.rxjava;

import android.content.Context;
import android.widget.Toast;

import org.icegeneral.rrcallbak.retrofit.DX168Exception;
import org.icegeneral.rrcallbak.retrofit.DX168Response;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;

/**
 * Created by iceGeneral on 16/7/14.
 */

public abstract class DX168Subscriber<T> extends Subscriber<DX168Response> {

    private Context context;

    public DX168Subscriber(Context applicationContext) {
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
    public void onNext(DX168Response dx168Response) {
        Object data = dx168Response.getData();
        if (data == JSONObject.NULL) {
            data = null;
        }
        onSuccess((T) data);
    }

    public abstract void onSuccess(T data);

    @Override
    public void onCompleted() {

    }

    public void onDX168Exception(DX168Exception e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void onNetworkException(Throwable e) {
        Toast.makeText(context, "网络较慢，请稍候...", Toast.LENGTH_SHORT).show();
    }

    public void onUnknownException(Throwable e) {
        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
    }

}
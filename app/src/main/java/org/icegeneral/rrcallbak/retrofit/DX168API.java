package org.icegeneral.rrcallbak.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by iceGeneral on 16/7/14.
 */

public class DX168API {

    public static final int RESULT_OK = 1;

    private static DX168API instance;

    public static DX168Retrofit get() {
        return instance.retrofit;
    }

    public static void init(boolean printLog) {
        instance = new DX168API(printLog);
    }

    private DX168Retrofit retrofit;

    private DX168API() {

    }

    private DX168API(final boolean printLog) {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(DX168GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Response response;
                                MediaType contentType;
                                String body;
                                if (printLog) {
                                    long t1 = System.nanoTime();
                                    Log.d("DX168API", String.format("Send request %s%n%s",
                                            request.url(), request.headers()));
                                    response = chain.proceed(request);
                                    contentType = response.body().contentType();
                                    body = response.body().string();
                                    long t2 = System.nanoTime();
                                    Log.d("DX168API", String.format("Receive response for %s in %.1fms%n%s%s",
                                            response.request().url(), (t2 - t1) / 1e6d, response.headers(), body));
                                    ResponseBody responseBody = ResponseBody.create(contentType, body);
                                    response = response.newBuilder().body(responseBody).build();
                                } else {
                                    response = chain.proceed(request);
                                }
                                return response;
                            }
                        })
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build())
                .baseUrl("http://xxx/")
                .build()
                .create(DX168Retrofit.class);
    }


}

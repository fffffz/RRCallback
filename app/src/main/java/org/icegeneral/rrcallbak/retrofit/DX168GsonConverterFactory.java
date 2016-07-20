package org.icegeneral.rrcallbak.retrofit;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by iceGeneral on 16/7/14.
 */

public class DX168GsonConverterFactory extends Converter.Factory {

    public static DX168GsonConverterFactory create() {
        return create(new Gson());
    }

    public static DX168GsonConverterFactory create(Gson gson) {
        return new DX168GsonConverterFactory(gson);
    }

    private final Gson gson;

    private DX168GsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DX168GsonResponseBodyConverter<>(gson, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new DX168GsonResponseBodyConverter<>(gson, type);
    }


}

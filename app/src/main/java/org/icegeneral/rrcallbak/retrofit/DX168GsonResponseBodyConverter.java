package org.icegeneral.rrcallbak.retrofit;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by iceGeneral on 16/7/14.
 */

public class DX168GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    DX168GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String value = responseBody.string();
        try {
            JSONObject response = new JSONObject(value);
            int code = response.optInt("code");
            String msg = response.optString("msg");
            if (code == DX168API.RESULT_OK) {
                //如果返回结果是JSONObject或者DX168Response则无需经过Gson
                if (type.toString().equals(JSONObject.class.toString())) {
                    return (T) response;
                } else {
                    return gson.fromJson(value, type);
                }
            } else {
                //返回的code不是RESULT_OK时Toast显示msg
                throw new DX168Exception(code, msg, value);
            }
        } catch (JSONException e) {
            //服务端返回的不是JSON，服务端出问题
            throw new DX168Exception(-1, "", value);
        }
    }
}

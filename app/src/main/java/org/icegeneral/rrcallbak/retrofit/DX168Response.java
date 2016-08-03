package org.icegeneral.rrcallbak.retrofit;

/**
 * Created by iceGeneral on 16/7/14.
 */

public class DX168Response<T> {

    private int code = -1;

    private String msg;

    private Object data;

    public DX168Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        if (data == null) {
            return null;
        }
        return (T) data;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ", \"msg\":\"" + msg + "\", \"data\":" + data.toString() + "}";
    }
}

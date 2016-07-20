package org.icegeneral.rrcallbak.retrofit;

/**
 * Created by iceGeneral on 16/7/14.
 */

public class DX168Exception extends RuntimeException {

    private int code;
    private String response;

    public DX168Exception(int code, String msg, String response) {
        super(msg);
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}

package org.icegeneral.rrcallbak.retrofit;

import org.icegeneral.rrcallbak.bean.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by iceGeneral on 16/7/14.
 */

public interface DX168Retrofit {

    @GET("xxx/login/login")
    Observable<User> login(
            @Query("username") String usernamem,
            @Query("password") String password);

    @GET("xxx/register/getRegisterVerifyCode")
    Observable<DX168Response> getRegisterVerifyCode(
            @Query("phone") String phone);

}

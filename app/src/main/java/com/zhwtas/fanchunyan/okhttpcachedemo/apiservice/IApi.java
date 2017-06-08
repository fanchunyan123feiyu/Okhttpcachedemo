package com.zhwtas.fanchunyan.okhttpcachedemo.apiservice;

import com.zhwtas.fanchunyan.okhttpcachedemo.bean.Info;
import com.zhwtas.fanchunyan.okhttpcachedemo.bean.TokenResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FacChunYan on 2017/5/27.
 */
public interface IApi {
    @POST("Token")
    Observable<TokenResult> getToken(@Query("grant_type") String grant_type);

    @POST("category")
    Call<Info> getBookType2(@Query("grant_type") String grant_type);

    @GET("category")
    Observable<Info> getBookType(@Query("key") String key);

}

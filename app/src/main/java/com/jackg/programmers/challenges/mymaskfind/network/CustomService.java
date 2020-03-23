package com.jackg.programmers.challenges.mymaskfind.network;

import com.jackg.programmers.challenges.mymaskfind.model.RetroResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CustomService {
    @GET("storesByGeo/json")
    Call<RetroResult> getData(@QueryMap Map<String, Object> options);

    @GET("storesByAddr/json")
    Call<RetroResult> getData(@Query("address") String address);
}

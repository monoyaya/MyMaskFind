package com.jackg.programmers.challenges.mymaskfind.network;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.jackg.programmers.challenges.mymaskfind.R;
import com.jackg.programmers.challenges.mymaskfind.model.RetroResult;
import com.jackg.programmers.challenges.mymaskfind.model.Store;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultRepo {
    private CustomService service;
    private MutableLiveData<RetroResult> data;
    private Context context;

    public ResultRepo(Context context) {
        this.context = context;

        data = new MutableLiveData<>(new RetroResult());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CustomService.class);
    }

    public MutableLiveData<RetroResult> getData(String address, Location location) {
        service.getData(address).enqueue(new Callback<RetroResult>() {
            @Override
            public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                RetroResult rt = response.body();

                if (rt != null) {
                    for (Store store : rt.getStores()) {
                        float[] temp = new float[1];

                        Location.distanceBetween(location.getLatitude(), location.getLongitude(), store.getLat(), store.getLng(), temp);

                        store.setDist(String.format(Locale.getDefault(), "%d 미터", Math.round(temp[0])));
                        if (store.getRemain_stat() == null) continue;

                        switch (store.getRemain_stat()) {
                            case "plenty":
                                store.setRemain(100);
                                break;
                            case "some":
                                store.setRemain(30);
                                break;
                            case "few":
                                store.setRemain(2);
                                break;
                            case "empty":
                                store.setRemain(1);
                                break;
                            default:
                                store.setRemain(0);
                                break;
                        }
                    }
                }

                data.postValue(rt);
            }

            @Override
            public void onFailure(Call<RetroResult> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<RetroResult> getData(Location location, int m) {

        Map<String, Object> options = new HashMap<>();
        options.put("m", m);
        options.put("lat", location.getLatitude());
        options.put("lng", location.getLongitude());

        service.getData(options).enqueue(new Callback<RetroResult>() {
            @Override
            public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                RetroResult rt = response.body();

                if (rt != null) {
                    for (Store store : rt.getStores()) {
                        float[] temp = new float[1];

                        Location.distanceBetween(location.getLatitude(), location.getLongitude(), store.getLat(), store.getLng(), temp);

                        store.setDist(String.format(Locale.getDefault(), "%d 미터", Math.round(temp[0])));

                        if (store.getRemain_stat() == null) continue;

                        switch (store.getRemain_stat()) {
                            case "plenty":
                                store.setRemain(100);
                                break;
                            case "some":
                                store.setRemain(30);
                                break;
                            case "few":
                                store.setRemain(2);
                                break;
                            case "empty":
                                store.setRemain(1);
                                break;
                            default:
                                store.setRemain(0);
                                break;
                        }
                    }
                }

                data.postValue(rt);
            }

            @Override
            public void onFailure(Call<RetroResult> call, Throwable t) {
                data.postValue(null);
            }
        });

        return data;
    }
}

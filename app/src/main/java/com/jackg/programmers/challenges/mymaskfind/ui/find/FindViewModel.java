package com.jackg.programmers.challenges.mymaskfind.ui.find;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jackg.programmers.challenges.mymaskfind.model.RetroResult;
import com.jackg.programmers.challenges.mymaskfind.network.ResultRepo;

public class FindViewModel extends AndroidViewModel {
    private ResultRepo repo;
    private MutableLiveData<RetroResult> data;

    public FindViewModel(@NonNull Application application) {
        super(application);

        repo = new ResultRepo(application);
        data = new MutableLiveData<>();
    }

    LiveData<RetroResult> getData(String address, Location location) {
        data = repo.getData(address, location);
        return data;
    }

    LiveData<RetroResult> getData(Location location, int m) {
        data = repo.getData(location, m);
        return data;
    }
}
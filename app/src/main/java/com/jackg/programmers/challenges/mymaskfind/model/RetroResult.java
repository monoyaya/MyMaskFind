package com.jackg.programmers.challenges.mymaskfind.model;

import java.io.Serializable;
import java.util.List;

public class RetroResult implements Serializable {
    private String address;
    private int count;
    private List<Store> stores;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}

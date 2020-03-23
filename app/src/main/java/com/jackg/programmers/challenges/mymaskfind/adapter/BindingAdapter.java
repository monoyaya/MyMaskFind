package com.jackg.programmers.challenges.mymaskfind.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationServices;
import com.jackg.programmers.challenges.mymaskfind.R;
import com.jackg.programmers.challenges.mymaskfind.model.Store;
import com.jackg.programmers.challenges.mymaskfind.ui.find.CustomAdapter;

import java.util.List;
import java.util.Locale;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView rv, List<Store> stores) {
        CustomAdapter adapter = (CustomAdapter) rv.getAdapter();

        if (adapter == null) {
            rv.setAdapter(new CustomAdapter(stores));
        } else {
            adapter.setDataSet(stores);
            adapter.notifyDataSetChanged();
        }
    }

    @androidx.databinding.BindingAdapter("setCardBG")
    public static void setCardBG(CardView cv, String remain) {
        if (remain == null || remain.isEmpty()) {
            cv.setClickable(false);
            cv.setCardBackgroundColor(Color.DKGRAY);
            return;
        }

        cv.setClickable(true);

        Resources res = cv.getResources();

        switch (remain) {
            case "plenty":
                cv.setCardBackgroundColor(res.getColor(R.color.LightGreen));
                break;
            case "some":
                cv.setCardBackgroundColor(res.getColor(R.color.LightYellow));
                break;
            case "few":
                cv.setCardBackgroundColor(res.getColor(R.color.LightCoral));
                break;
            case "empty":
                cv.setCardBackgroundColor(res.getColor(R.color.LightGray));
                cv.setClickable(false);
                break;
            case "break":
                cv.setCardBackgroundColor(Color.DKGRAY);
                cv.setClickable(false);
                break;
        }
    }

    @androidx.databinding.BindingAdapter("typeText")
    public static void typeText(TextView tv, String type) {
        switch (type) {
            case "01":
                tv.setText("약국");
                break;
            case "02":
                tv.setText("우체국");
                break;
            case "03":
                tv.setText("농협 하나로 마트");
                break;
        }
    }

    @androidx.databinding.BindingAdapter("setRemain")
    public static void setRemain(TextView tv, String remain) {
        if (remain == null || remain.isEmpty()) {
            tv.setText("알 수 없음");
            return;
        }

        switch (remain) {
            case "plenty":
                tv.setText(String.format(Locale.getDefault(), "%d개 이상", 100));
                break;
            case "some":
                tv.setText(String.format(Locale.getDefault(), "%d ~ %d개", 30, 100));
                break;
            case "few":
                tv.setText(String.format(Locale.getDefault(), "%d ~ %d개", 2, 30));
                break;
            case "empty":
                tv.setText("재고 없음");
                break;
            case "break":
                tv.setText("판매 중단");
                break;
        }
    }

    @androidx.databinding.BindingAdapter("setDate")
    public static void setDate(TextView tv, String date) {
        if (date == null || date.isEmpty()) {
            tv.setText("알 수 없음");
            return;
        }

        String[] temp = date.split(" ");
        String[] days = temp[0].split("/");
        String[] times = temp[1].split(":");

        String result = days[1] + "월 " + days[2] + "일\t"
                + times[0] + "시 " + times[1] + "분 ";

        tv.setText(result);
    }

    @androidx.databinding.BindingAdapter({"setLat", "setLng"})
    public static void getDist(TextView tv, float lat, float lng) {

        LocationServices.getFusedLocationProviderClient(tv.getContext())
                .getLastLocation()
                .addOnSuccessListener((Activity) tv.getContext(), location -> {

                    float[] dist = new float[1];

                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat, lng, dist);

                    tv.setText(String.format(Locale.getDefault(), "%dm", Math.round(dist[0])));
                });
    }
}

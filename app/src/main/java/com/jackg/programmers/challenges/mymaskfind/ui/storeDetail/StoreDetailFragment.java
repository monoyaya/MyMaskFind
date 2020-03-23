package com.jackg.programmers.challenges.mymaskfind.ui.storeDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.jackg.programmers.challenges.mymaskfind.R;
import com.jackg.programmers.challenges.mymaskfind.databinding.FragmentStoreDetailBinding;
import com.jackg.programmers.challenges.mymaskfind.model.Store;


public class StoreDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentStoreDetailBinding binding
                = DataBindingUtil.inflate(inflater, R.layout.fragment_store_detail, container, false);

        Store store = (Store) getArguments().getSerializable("storeData");

        binding.setData(store);
        binding.executePendingBindings();

        binding.bntBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.bntWeb.setOnClickListener(v -> {
            String[] addrs = store.getAddr().split(" ");

            String uriString = getString(R.string.web_base)
                                + addrs[0] + " " + addrs[1] + " " + store.getName();

            Uri uri = Uri.parse(uriString);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);
        });

        binding.bntMap.setOnClickListener(v -> {
            String uriString = getString(R.string.map_base) + store.getLat() + "," + store.getLng();

            Uri uri = Uri.parse(uriString);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);
        });

        return binding.getRoot();
    }
}

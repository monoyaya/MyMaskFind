package com.jackg.programmers.challenges.mymaskfind.ui.find;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jackg.programmers.challenges.mymaskfind.R;
import com.jackg.programmers.challenges.mymaskfind.databinding.StoreItemBinding;
import com.jackg.programmers.challenges.mymaskfind.model.Store;

import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<Store> mDataSet;

    public CustomAdapter(List<Store> mDataSet) {
        this.mDataSet = mDataSet;

        if (mDataSet != null && mDataSet.size() > 1) Collections.sort(mDataSet, Store::compareTo);
    }

    public void setDataSet(List<Store> mDataSet) {
        this.mDataSet = mDataSet;

        if (mDataSet != null && mDataSet.size() > 1) Collections.sort(mDataSet, Store::compareTo);
    }

    List<Store> getmDataSet() {
        return mDataSet;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        StoreItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.store_item, parent, false);

        return new CustomViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {
            holder.binding.setData(mDataSet.get(position));
            holder.binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        StoreItemBinding binding;

        CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(v -> {
                Store store = binding.getData();

                NavController controller = Navigation.findNavController(itemView);

                FindFragmentDirections.ActionNavigationFindToStoreDetailFragment action
                        = FindFragmentDirections.actionNavigationFindToStoreDetailFragment();

                action.setStoreData(store);

                controller.navigate(action);
            });
        }
    }
}

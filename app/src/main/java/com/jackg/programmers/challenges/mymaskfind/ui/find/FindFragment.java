package com.jackg.programmers.challenges.mymaskfind.ui.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jackg.programmers.challenges.mymaskfind.R;
import com.jackg.programmers.challenges.mymaskfind.databinding.FragmentFindBinding;
import com.jackg.programmers.challenges.mymaskfind.databinding.SearchDialogBinding;
import com.jackg.programmers.challenges.mymaskfind.model.RetroResult;
import com.jackg.programmers.challenges.mymaskfind.model.Store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FindFragment extends Fragment {

    private FindViewModel findViewModel;
    private FragmentFindBinding binding;
    private FusedLocationProviderClient client;

    private FloatingActionButton fabLoca, fabSear;

    private Animation fab_open, fab_close;
    private boolean isOpen = false;

    private int m = 1000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        findViewModel = new ViewModelProvider(requireActivity()).get(FindViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find, container, false);

        View root = binding.getRoot();

        binding.rvStore.setAdapter(new CustomAdapter(new ArrayList<>()));

        fabLoca = binding.fabLocaltion;
        fabSear = binding.fabSearch;

        fab_open = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close);

        binding.fabAdd.setOnClickListener(v -> animate());

        binding.fabLocaltion.setOnClickListener(v -> {
            setRadius();
            animate();
        });

        binding.fabSearch.setOnClickListener(v -> {
            findFromQuery();
            animate();
        });

        client = LocationServices.getFusedLocationProviderClient(requireActivity());

        findFromLocation();

        binding.toolbar.setOnMenuItemClickListener(item -> {
            List<Store> stores = ((CustomAdapter) binding.rvStore.getAdapter()).getmDataSet();

            if (item.getItemId() == R.id.sort_dist) {
                Collections.sort(stores, Store::compareTo);
            } else if (item.getItemId() == R.id.sort_dist_reverse) {
                Collections.sort(stores, Store::compareTo);
                Collections.reverse(stores);
            } else if (item.getItemId() == R.id.sort_remain) {
                Collections.sort(stores, (o1, o2) -> {
                    if (o1.getRemain() < o2.getRemain()) return 1;
                    else if (o1.getRemain() > o2.getRemain()) return -1;
                    else return (-1) * o2.compareTo(o1);
                });
            }

            binding.rvStore.getAdapter().notifyDataSetChanged();

            return true;
        });

        return root;
    }

    private void findFromLocation() {
        client.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                LiveData<RetroResult> temp = findViewModel.getData(location, m);
                temp.observe(getViewLifecycleOwner(), retroResult -> {
                    binding.setData(retroResult);
                    binding.executePendingBindings();
                });
            } else {
                Toast.makeText(requireContext(), "위치정보를 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRadius() {
        SeekBar sb = new SeekBar(requireContext());
        sb.setMax(5000);
        sb.setProgress(m);

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("위치 검색 범위 설정")
                .setMessage(String.format(Locale.getDefault(), "%d 미터", m))
                .setView(sb)
                .setPositiveButton("검색", (dialog1, which) -> findFromLocation())
                .create();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    m = progress;
                    dialog.setMessage(String.format(Locale.getDefault(), "%d 미터", m));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 1) {
                    Toast.makeText(requireContext(), "최소 검색범위는 1m 입니다.", Toast.LENGTH_SHORT).show();

                    m = 1;
                    dialog.setMessage(String.format(Locale.getDefault(), "%d 미터", m));
                }
            }
        });

        dialog.show();
    }

    private void findFromQuery() {
        SearchDialogBinding viewBinding = SearchDialogBinding.inflate(LayoutInflater.from(requireContext()));

        viewBinding.txt1.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String temp = ((TextInputEditText) v).getText().toString().trim();

                if (temp.equals("") || (!temp.endsWith("시") && !temp.endsWith("도"))) {
                    viewBinding.layout1.setError("정확히 입력해주세요.");
                } else {
                    viewBinding.layout1.setError(null);
                }
            } else {
                viewBinding.layout1.setError(null);
            }
        });

        viewBinding.txt2.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String temp = ((TextInputEditText) v).getText().toString().trim();

                if (temp.equals("") || (!temp.endsWith("시") && !temp.endsWith("군") && !temp.endsWith("구"))) {
                    viewBinding.layout2.setError("정확히 입력해주세요.");
                } else {
                    viewBinding.layout2.setError(null);
                }
            } else {
                viewBinding.layout2.setError(null);
            }
        });

        viewBinding.txt3.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String temp = ((TextInputEditText) v).getText().toString().trim();

                if (temp.equals("") || (!temp.endsWith("읍") && !temp.endsWith("면") && !temp.endsWith("동"))) {
                    viewBinding.layout3.setError("정확히 입력해주세요.");
                } else {
                    viewBinding.layout3.setError(null);
                }
            } else {
                viewBinding.layout3.setError(null);
            }
        });

        new MaterialAlertDialogBuilder(requireContext())
                .setView(viewBinding.getRoot())
                .setNeutralButton("취소", (dialog, which) -> dialog.cancel())
                .setPositiveButton("검색", (dialog, which) -> {
                    String metro = "";

                    if (viewBinding.layout1.getError() == null) {
                        if (viewBinding.txt1.getText() != null)
                            metro = viewBinding.txt1.getText().toString().trim();
                    }

                    String city = "";

                    if (viewBinding.layout2.getError() == null) {
                        if (viewBinding.txt2.getText() != null)
                            city = viewBinding.txt2.getText().toString().trim();
                    }

                    String local = "";

                    if (viewBinding.layout3.getError() == null) {
                        if (viewBinding.txt3.getText() != null) {
                            local = viewBinding.txt3.getText().toString().trim();
                        }
                    }

                    String qurey = metro + " " + city;

                    if (!local.equals("")) qurey = qurey + " " + local;

                    final String finalQurey = qurey;

                    client.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                        LiveData<RetroResult> queryRt = findViewModel.getData(finalQurey, location);

                        queryRt.observe(getViewLifecycleOwner(), retroResult -> {
                            if (retroResult.getCount() == 0) {
                                Toast.makeText(requireContext(), "검색된 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                            }

                            binding.setData(retroResult);
                            binding.executePendingBindings();
                        });
                    });


                })
                .show();
    }

    private void animate() {
        if (isOpen) {
            fabLoca.startAnimation(fab_close);
            fabSear.startAnimation(fab_close);
            fabLoca.setClickable(false);
            fabSear.setClickable(false);
            fabLoca.setFocusable(false);
            fabSear.setFocusable(false);
            isOpen = false;
        } else {
            fabLoca.startAnimation(fab_open);
            fabSear.startAnimation(fab_open);
            fabLoca.setClickable(true);
            fabSear.setClickable(true);
            fabLoca.setFocusable(true);
            fabSear.setFocusable(true);
            isOpen = true;
        }
    }
}

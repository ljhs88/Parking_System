package com.xiyou3G.parkingsystem.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.adapter.RentRecyclerAdapter;
import com.xiyou3G.parkingsystem.api.RentService;
import com.xiyou3G.parkingsystem.bean.RentItem;
import com.xiyou3G.parkingsystem.rentbean.Data;
import com.xiyou3G.parkingsystem.rentbean.RentResponse;
import com.xiyou3g.select.parking.util.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private List<RentItem> rentItems;
    private RentRecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_order_fragment, container, false);
        initView();
        request();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void request() {
        RentService rentService = retrofitManager.getRetrofit().create(RentService.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        rentService.getAllRent(userId).enqueue(new Callback<RentResponse>() {
            @Override
            public void onResponse(@NonNull Call<RentResponse> call, @NonNull Response<RentResponse> response) {
                if (rentItems == null) {
                    rentItems = new ArrayList<>();
                }
                rentItems.clear();
                assert response.body() != null;
                List<Data> list = response.body().getData();
                for (Data data : list) {
                    rentItems.add(new RentItem(data.getPayPrice(),
                            data.getStartTime().toString(),
                            data.getEndTime().toString(),
                            data.getIscancel(),
                            data.getId()));
                }
                if (recyclerAdapter == null) {
                    recyclerAdapter = new RentRecyclerAdapter(rentItems);
                }
                requireActivity().runOnUiThread(() -> {
                    if (recyclerView.getAdapter() == null) {
                        recyclerView.setAdapter(recyclerAdapter);
                    } else {
                        recyclerAdapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                });
            }

            @Override
            public void onFailure(@NonNull Call<RentResponse> call, @NonNull Throwable t) {

            }
        });

    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.id_reFresh);
        swipeRefreshLayout.setOnRefreshListener(this::request);
        recyclerView = view.findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

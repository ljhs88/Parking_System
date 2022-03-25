package com.xiyou3g.select.pay.fragment;

import android.annotation.SuppressLint;

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

import com.xiyou3g.select.pay.R;
import com.xiyou3g.select.pay.adapter.PayAdapter;
import com.xiyou3g.select.pay.api.PayService;
import com.xiyou3g.select.pay.bean.Data;
import com.xiyou3g.select.pay.bean.PayResponse;
import com.xiyou3g.select.pay.util.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private List<Data> list;
    private PayAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_pay_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.pay_swipe_fresh);
        swipeRefreshLayout.setOnRefreshListener(this::request);
        recyclerView = view.findViewById(R.id.pay_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void request() {

        PayService orderService = retrofitManager.getRetrofit().create(PayService.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        orderService.getPay(userId).enqueue(new Callback<PayResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<PayResponse> call, @NonNull Response<PayResponse> response) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.clear();
                assert response.body() != null;
                List<Data> dataList = response.body().getData();
                list.addAll(dataList);
                if (recyclerAdapter == null) {
                    recyclerAdapter = new PayAdapter(list);
                }
                getActivity().runOnUiThread(() -> {
                    if (recyclerView.getAdapter() == null) {
                        recyclerView.setAdapter(recyclerAdapter);
                    } else {
                        recyclerAdapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                });
            }

            @Override
            public void onFailure(@NonNull Call<PayResponse> call, @NonNull Throwable t) {

            }
        });
    }
}

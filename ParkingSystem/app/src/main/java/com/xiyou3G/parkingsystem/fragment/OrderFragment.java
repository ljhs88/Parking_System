package com.xiyou3G.parkingsystem.fragment;

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

import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.adapter.OrderRecyclerAdapter;
import com.xiyou3G.parkingsystem.api.OrderService;
import com.xiyou3G.parkingsystem.bean.AllOrderResponse;
import com.xiyou3G.parkingsystem.bean.Data;
import com.xiyou3G.parkingsystem.bean.OrderItem;
import com.xiyou3g.select.parking.util.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private List<OrderItem> orderItems;
    private OrderRecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_order_fragment, container, false);
        initView();
        request();
        return view;
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.id_reFresh);
        swipeRefreshLayout.setOnRefreshListener(this::request);
        recyclerView = view.findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void request() {

        OrderService orderService = retrofitManager.getRetrofit().create(OrderService.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        orderService.getAllOrder(userId).enqueue(new Callback<AllOrderResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllOrderResponse> call, @NonNull Response<AllOrderResponse> response) {
                if (orderItems == null) {
                    orderItems = new ArrayList<>();
                }
                orderItems.clear();
                assert response.body() != null;
                List<Data> list = response.body().getData();
                for (Data data : list) {
                    orderItems.add(new OrderItem(data.getAddress(),
                            data.getStartTime().toString(),
                            data.getEndTime().toString(),
                            data.getPayPrice(),
                            data.getIscancel(),
                            data.getPosId(),
                            data.getPosType()));
                }
                if (recyclerAdapter == null) {
                    recyclerAdapter = new OrderRecyclerAdapter(orderItems);
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
            public void onFailure(@NonNull Call<AllOrderResponse> call, @NonNull Throwable t) {

            }
        });
    }

}


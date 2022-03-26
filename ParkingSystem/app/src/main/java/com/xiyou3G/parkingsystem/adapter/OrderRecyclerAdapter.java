package com.xiyou3G.parkingsystem.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.api.OrderService;
import com.xiyou3G.parkingsystem.bean.OrderItem;
import com.xiyou3G.parkingsystem.bean.OrderResponse;
import com.xiyou3g.customer.CustomerActivity;
import com.xiyou3g.select.parking.util.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder> {

    private List<OrderItem> list;
    private View view;
    private Activity activity;

    public OrderRecyclerAdapter(List<OrderItem> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_order, parent, false);
        this.view = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem orderItem = list.get(position);
        holder.address.setText(orderItem.getAddress());
        holder.startTime.setText(orderItem.getStartTime());
        holder.endTime.setText(orderItem.getEndTime());
        holder.price.setText(String.valueOf(orderItem.getPrice()));
        if (orderItem.getStatus() == 0) {
            holder.status.setText("未完成");
        } else {
            holder.status.setText("已完成");
        }
        if (orderItem.getType() == 0) {
            holder.type.setText("停车位");
        } else {
            holder.type.setText("充电桩");
        }
        view.setOnClickListener(v -> {
            RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
            OrderService orderService = retrofitManager.getRetrofit().create(OrderService.class);
            orderService.getOrder(orderItem.getOrdersId()).enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {
                    Intent intent = new Intent(activity, CustomerActivity.class);
                    intent.putExtra("orderId", orderItem.getOrdersId());
                    activity.startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {

                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        TextView startTime;
        TextView endTime;
        TextView price;
        TextView status;
        TextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.item_address_text);
            startTime = itemView.findViewById(R.id.item_startTime_text);
            endTime = itemView.findViewById(R.id.item_endTime_text);
            price = itemView.findViewById(R.id.item_price_text);
            status = itemView.findViewById(R.id.item_status_text);
            type = itemView.findViewById(R.id.item_type_text);
        }
    }
}

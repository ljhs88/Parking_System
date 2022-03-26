package com.xiyou3G.parkingsystem.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.bean.RentItem;

import java.util.List;

public class RentRecyclerAdapter extends RecyclerView.Adapter<RentRecyclerAdapter.ViewHolder> {

    private List<RentItem> list;
    private View view;

    public RentRecyclerAdapter(List<RentItem> list) {
        Log.d("TAG", "RentRecyclerAdapter: " + list.size());
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_rent, parent, false);
        this.view = view;
        Log.d("TAG", "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RentItem rentItem = list.get(position);
        holder.price.setText(String.valueOf(rentItem.getPrice()));
        holder.startTime.setText(rentItem.getStartTime());
        holder.endTime.setText(rentItem.getEndTime());
        if (rentItem.getStatus() == 0) {
            holder.status.setText("未完成");
        } else {
            holder.status.setText("已完成");
        }
        String orderId = rentItem.getOrderId();
        Log.d("TAG", "onBindViewHolder: " + rentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: " + list.size());
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        TextView startTime;
        TextView endTime;
        TextView status;

        public ViewHolder(@NonNull View view) {
            super(view);
            price = view.findViewById(R.id.rent_price_text);
            startTime = view.findViewById(R.id.rent_startTime_text);
            endTime = view.findViewById(R.id.rent_endTime_text);
            status = view.findViewById(R.id.rent_status_text);
        }
    }
}

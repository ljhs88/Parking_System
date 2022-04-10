package com.xiyou3g.select.pay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiyou3g.select.pay.R;
import com.xiyou3g.select.pay.bean.Data;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> {

    private List<Data> list;
    private View view;

    public PayAdapter(List<Data> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pay_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = list.get(position);
        holder.price.setText(String.valueOf(data.getPayPrice()));
        holder.time.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.CHINA).format(data.getPayTime()));
        if (data.getPayType() == 0) {
            holder.type.setText("支付宝");
        } else if (data.getPayType() == 1) {
            holder.type.setText("微信");
        } else {
            holder.type.setText("其他");
        }
        if (data.getIscancel() == 0) {
            holder.status.setText("未完成");
        } else {
            holder.status.setText("已完成");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        TextView time;
        TextView type;
        TextView status;

        public ViewHolder(@NonNull View view) {
            super(view);
            price = view.findViewById(R.id.pay_price);
            time = view.findViewById(R.id.pay_time);
            type = view.findViewById(R.id.pay_type);
            status = view.findViewById(R.id.pay_status);
        }
    }
}

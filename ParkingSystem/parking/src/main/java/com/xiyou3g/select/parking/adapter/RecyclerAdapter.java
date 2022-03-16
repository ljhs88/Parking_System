package com.xiyou3g.select.parking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.services.help.Tip;
import com.xiyou3g.select.parking.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private  List<Tip> list;
    private Context context;
    private View view;

    public RecyclerAdapter(List<Tip> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tip tip = list.get(position);
        holder.nameText.setText(tip.getName());
        holder.districtText.setText(tip.getDistrict());
        view.setOnClickListener(v -> ARouter.getInstance().build("/map/MapActivity").withDouble("latitude", tip.getPoint().getLatitude()).withDouble("longitude", tip.getPoint().getLongitude()).withString("destination", tip.getName()).navigation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView districtText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            districtText = itemView.findViewById(R.id.district_text);
        }
    }
}

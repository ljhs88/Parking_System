package com.xiyou3g.select.parking.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiyou3g.select.parking.R;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.bean.stallbean;

import java.util.List;

public class showInformationAdapter extends RecyclerView.Adapter<showInformationAdapter.ViewHolder>  {

    private static final int CHARGE = 1;
    private static final int STALL = 3;
    private int STATUS;
    private Activity activity;
    private Context context;
    private List<stallbean> stallbeanList;
    private List<chargebean> chargebeanList;

    public showInformationAdapter(int STATUS, Activity activity, Context context,
                                  List<stallbean> stallbeanList, List<chargebean> chargebeanList) {
        this.STATUS = STATUS;
        this.activity = activity;
        this.context = context;
        this.stallbeanList = stallbeanList;
        this.chargebeanList = chargebeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_show_information, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (STATUS == STALL) {
            size = stallbeanList.size();
        } else if (STATUS == CHARGE) {
            size = chargebeanList.size();
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

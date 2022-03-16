package com.xiyou3g.select.parking.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.R;
import com.xiyou3g.select.parking.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpWindow implements Inputtips.InputtipsListener, SearchView.OnQueryTextListener {

    private PopupWindow popupWindow;
    private final Activity context;
    private BottomSheetDialog bottomSheetDialog;
    private List<Tip> adapterList;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private final String city;
    private RecyclerView searchListView;


    public UpWindow(Activity context, int viewId, int animId, int recyclerId, String city) {
        this.context = context;
        this.city = city;

        initUpWindow(viewId, animId);

        initDialog(recyclerId);

    }

    private void initDialog(int recyclerId) {

        InputtipsQuery inputtipsQuery = new InputtipsQuery("停车场", city);
        Inputtips inputtips = new Inputtips(context, inputtipsQuery);
        inputtips.setInputtipsListener(this);
        inputtips.requestInputtipsAsyn();

        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(recyclerId);

        recyclerView = bottomSheetDialog.getWindow().findViewById(R.id.bottom_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void initUpWindow(int viewId, int animId) {

        View contentView = LayoutInflater.from(context).inflate(viewId, null);
        SearchView searchView = contentView.findViewById(R.id.search_view);
        searchListView = contentView.findViewById(R.id.search_list_view);
        searchListView.setLayoutManager(new LinearLayoutManager(context));


        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("停车场");
        searchView.setOnQueryTextListener(this);

        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setAnimationStyle(animId);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("TAG", "onQueryTextChange: ");
        searchListView.setVisibility(View.VISIBLE);
        InputtipsQuery inputtipsQuery = new InputtipsQuery(newText, city);
        Inputtips inputtips = new Inputtips(context, inputtipsQuery);
        inputtips.setInputtipsListener(this);
        inputtips.requestInputtipsAsyn();
        return true;
    }


    @SuppressLint("InflateParams")
    public void show() {
        View parentView = LayoutInflater.from(context).inflate(R.layout.fragment_parking, null);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(parentView, Gravity.TOP|Gravity.CENTER, 0, 200);
        bottomSheetDialog.show();
    }

    public void hint() {
        popupWindow.dismiss();
        bottomSheetDialog.cancel();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        Log.d("TAG", "onGetInputtips: " + list);
        if (adapterList == null) {
            adapterList = new ArrayList<>(list);
        } else {
            adapterList.clear();
            adapterList.addAll(list);
        }
        if (recyclerAdapter != null) {
            Log.d("TAG", "onGetInputtips: " + "notifyDataSetChanged");
            recyclerAdapter.notifyDataSetChanged();
        } else {
            Log.d("TAG", "onGetInputtips: " + "notifyDataSetChanged" + " else");

            adapterList.remove(0);
            recyclerAdapter = new RecyclerAdapter(adapterList);

            recyclerView.setAdapter(recyclerAdapter);

            searchListView.setAdapter(recyclerAdapter);
        }
    }
}

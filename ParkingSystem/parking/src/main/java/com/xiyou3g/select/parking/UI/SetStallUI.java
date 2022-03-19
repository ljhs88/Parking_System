package com.xiyou3g.select.parking.UI;

import android.app.Activity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiyou3g.select.parking.R;

public class SetStallUI extends SetUI {

    private final Activity activity;

    public SetStallUI (Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setText() {
        TextView textView1 = activity.findViewById(R.id.parking_text1);
        TextView textView2 = activity.findViewById(R.id.parking_text2);
        TextView textView3 = activity.findViewById(R.id.parking_text3);
        TextView textView4 = activity.findViewById(R.id.parking_text4);
        TextView textView5 = activity.findViewById(R.id.parking_text5);
        TextView textView6 = activity.findViewById(R.id.parking_text6);
        TextView textView7 = activity.findViewById(R.id.parking_text7);
        View view = activity.findViewById(R.id.parking_view7);
        textView1.setText("拥有者");
        textView2.setText("拥有者电话");
        textView3.setText("车位信息");
        textView4.setText("车位价格(元/时)");
        textView5.setText("管理员");
        textView6.setText("管理员电话");
        textView7.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

    }

    @Override
    public void setEdit() {
        EditText editText1 = activity.findViewById(R.id.parking_edit1);
        EditText editText2 = activity.findViewById(R.id.parking_edit2);
        EditText editText3 = activity.findViewById(R.id.parking_edit3);
        EditText editText4 = activity.findViewById(R.id.parking_edit4);
        EditText editText5 = activity.findViewById(R.id.parking_edit5);
        EditText editText6 = activity.findViewById(R.id.parking_edit6);
        EditText editText7 = activity.findViewById(R.id.parking_edit7);
        editText1.setHint("拥有者");
        editText2.setHint("拥有者电话");
        editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText3.setHint("车位信息");
        editText4.setHint("车位价格(元/时)");
        editText4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        editText5.setHint("管理员");
        editText6.setHint("管理员电话");
        editText6.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText7.setVisibility(View.GONE);
    }




}

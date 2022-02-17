package com.xiyou3g.select.parking.UI;

import android.app.Activity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.xiyou3g.select.parking.R;

public class SetChargeUI extends SetUI {

    private final Activity activity;

    public SetChargeUI (Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setText() {
        TextView textView1 = activity.findViewById(R.id.parking_text1);
        TextView textView2 = activity.findViewById(R.id.parking_text2);
        TextView textView3 = activity.findViewById(R.id.parking_text3);
        textView1.setText("姓名");
        textView2.setText("充电桩数量(个)");
        textView3.setText("充电桩价格(元/时)");
    }

    @Override
    public void setEdit() {
        EditText editText1 = activity.findViewById(R.id.parking_edit1);
        EditText editText2 = activity.findViewById(R.id.parking_edit2);
        EditText editText3 = activity.findViewById(R.id.parking_edit3);
        EditText editText4 = activity.findViewById(R.id.parking_edit4);
        editText1.setHint("姓名");
        editText2.setHint("数量");
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText3.setHint("价格");
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText4.setHint("简介");
    }
}

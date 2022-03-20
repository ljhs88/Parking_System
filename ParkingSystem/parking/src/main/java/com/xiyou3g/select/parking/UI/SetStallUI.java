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
        TextView textView8 = activity.findViewById(R.id.parking_text8);
        View view = activity.findViewById(R.id.parking_view8);

        textView8.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

    }

    @Override
    public void setEdit() {
        EditText editText2 = activity.findViewById(R.id.parking_edit2);
        EditText editText5 = activity.findViewById(R.id.parking_edit5);
        EditText editText7 = activity.findViewById(R.id.parking_edit7);
        EditText editText8 = activity.findViewById(R.id.parking_edit8);
        editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText5.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        editText7.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText8.setVisibility(View.GONE);
    }




}

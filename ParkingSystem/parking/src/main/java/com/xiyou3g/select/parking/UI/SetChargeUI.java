package com.xiyou3g.select.parking.UI;

import android.app.Activity;
import android.text.InputFilter;
import android.widget.EditText;

import com.xiyou3g.select.parking.R;

public class SetChargeUI extends SetUI {

    private final Activity activity;

    public SetChargeUI (Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setText() {

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
        editText8.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

    }
}

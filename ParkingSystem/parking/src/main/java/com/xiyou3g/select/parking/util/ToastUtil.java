package com.xiyou3g.select.parking.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast = null;
    public static void getToast(Context context,String theText){
        if (toast == null) {
            toast = Toast.makeText(context, theText, Toast.LENGTH_SHORT);
        } else {
            toast.setText(theText);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
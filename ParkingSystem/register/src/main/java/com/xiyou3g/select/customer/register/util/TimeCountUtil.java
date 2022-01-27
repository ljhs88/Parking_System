package com.xiyou3g.select.customer.register.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.xiyou3g.select.customer.register.R;

public class TimeCountUtil extends CountDownTimer {

    private final Activity activity;
    private final Button button;

    public TimeCountUtil(long millisInFuture, long countDownInterval, Activity activity, Button button) {
        super(millisInFuture, countDownInterval);
        this.activity = activity;
        this.button = button;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long l) {
        button.setClickable(false);
        button.setText(l/1000 + "秒后重新发送");

        button.setBackground(activity.getDrawable(R.drawable.get_code_button));
        Spannable spannable = new SpannableString(button.getText().toString());

        spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        button.setText(spannable);
    }

    @Override
    public void onFinish() {
        button.setText("重新获取验证码");
        button.setClickable(true);
        button.setBackground(activity.getResources().getDrawable(R.drawable.get_code_button));
    }
}

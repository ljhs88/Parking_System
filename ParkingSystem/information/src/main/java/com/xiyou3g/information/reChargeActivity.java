package com.xiyou3g.information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.xiyou3g.information.Utility.ActivityCollector;
import com.xiyou3g.information.Utility.PayResult;
import com.xiyou3g.information.Utility.SignUtils;
import com.xiyou3g.information.Utility.toolBar;
import com.xiyou3g.information.retrofit.mRetrofit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class reChargeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText moneyText;
    private Button back;
    private Button reChargeButton;

    private String biz_content = null;
    private final int SDK_PAY_FLAG = 1;
    private final String APP_ID = "2021003124681877";
    private final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASC" +
            "BKgwggSkAgEAAoIBAQCjbSy8ArFFhJ6CUXAcKyEF3pY9tPGI65ZccR+Jq4H" +
            "vD3cRIZQgy1XpZ3/dMhtNxosLJjeTsb7EQM4pKgiiL3uc40B6RG0LjoVgHF" +
            "yPgQt5Aigcg5dsmU8Aj/o3Ha7Yri5IOh6hQxBMUJIQ51M8KWnIOtXGdccGG" +
            "cHrhP40h2bZzqKEDgunTIrTJSs6KzsL9BFOCBsUXss68feU13AD9GBTd+Pn" +
            "sdlBgOmRDytFZUyldpGKdLQszzjllZhVgDkuzOhzxbaIy5IQhKsO4qW9PVO" +
            "TyQ3FZgzkAMxHokHOwNWGSS3Z7cJLiz7p+jbH94tgOy8QgT+CNS3oXBvnwV" +
            "xdLOx3AgMBAAECggEAfWNnfj0mnCr7nwsy1Dn3FVTJu0CYTTBMZlLZGry/b" +
            "ZdVCIzV5S99lB44CZPHRS4/QmepHqWys0rxor3AcAe0dRQbRXQ4ojRQewRn" +
            "tCcTkV2pPu9sAAPEjK7rENyyaUeC9SG6nIoi+BqdZ2DFCRTRN7KEkMeO00x" +
            "x7+1TSSRWMmfRfstIpvqslgjjTzItj3UnCLJiIeR7xUxt/Jk8VMHChjHkZM" +
            "hC9oJffBbm05bH/hu9iG1REA+5fxroFKD7zRQk3ycRpOi6IlxD3+7TX1PN4" +
            "at9fdwiziWRGRd+wFgeKP3bCR/vh31pnOO542fVuGfFKip+9cyeitEf0qoC" +
            "VXKJqQKBgQDO4pbutNztyziofZWJjWGQZkrkH5L/gfAZjOATDvOoSfTk64C" +
            "Wz9/8ELQCZpxSEIZqrYL3BwU58ibKf+EqF1tZsZhwLNtT14RJJ3sA/b3+Z7" +
            "vXClW1HDmElY3W3bOblByckod5Yj0WODyImmOZUGcUUzz5YAtWKi8klS5av" +
            "wZWAwKBgQDKOWR2ODbO5T7SvTVej5amfTbS1OQ+0Ko90lRanCvezoc5S84V" +
            "px1pjQLEVlVJ9YECDECZSyeCB4aS5ro1QoaEr8cSAv5bCIGE5QYRx7F8Ies" +
            "Tmtf4TIOOTpcEgLHrIkfV8uqlrPcDPKb0WBSdSYzE20FoL9crnOGJdgJ+Oi" +
            "5PfQKBgQCtpcLJ2UUW0txbkA1Txzd83F8/2sCndYjx6Syq0oHxXyIOgx0Le" +
            "K+oAt0UpsvOldA8+iZS8bGRUY79qRh1G+WxAz/P59awKNUAEgNMca5nOuqP" +
            "ND18JpSglGbZUnzOuSLKyLKtT6e2Xr1wayXuKW5bwQIMznC1gfLP4edmg6/" +
            "twQKBgF7bHvGJGsRfqeFGGUOazMqZj17Dt7BzRnerMiAygy2G6M0KbsefVN" +
            "+O4k5gRK9ldjcUsjR1mTmX+BruGUgkRV716MXdG5xt1/sTRo6OggU9D7xpd" +
            "IamtUrKOHJHP62QreuAf2ppQJCHIbUZn8nLlzk7s2mAj0iI1Q4aib7V4aUt" +
            "AoGBALjCoScupefLGYusc0TIgRuHo7g/0rRAIUu5knVL74vgrDOcL17LQJP" +
            "CQz4duO+TAdsdqskPot9w3y6yPM9PU8zyuE5YhI2IH5m2O/wCTFpqYn2gyZ" +
            "O1lUejqoBzhjSASp6t0xzCBlK3NW2o91vuNrV/m23j8NOyS4USlVbzqQIE";
    private String orderInfo2 = "";
    private String orderInfo1 = "";

    private String walletId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_charge);

        // 添加activity
        ActivityCollector.addActivity(this);

        /*
         * 获取walletId
         */
        Intent intent = getIntent();
        walletId = intent.getStringExtra("walletId");

        setToolbar();

        setViewId();

    }

    /**
     * 点击空白输入键盘自动关闭
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (reChargeActivity.this.getCurrentFocus() != null) {
                if (reChargeActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(reChargeActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setViewId() {
        moneyText = findViewById(R.id.money);
        back = findViewById(R.id.back);
        reChargeButton = findViewById(R.id.re_charge);
        reChargeButton.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("充值");
        toolbar.setTitleTextColor(R.color.black);
        toolbar.setTitleMarginStart(toolBar.getAndroidScreenProperty(this));
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            onBackPressed();
        } else if (id == R.id.re_charge) {
            String money = String.valueOf(moneyText.getText());
            if (money.equals("")) {
                Toast.makeText(this, "请输入充值金额!", Toast.LENGTH_SHORT).show();
            } else {
                reCharge();
            }
        }
    }

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Map<String,String> result = (Map<String, String>) msg.obj;
                    PayResult payResult = new PayResult(result);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 上传充值信息
                        reCharge();
                        Toast.makeText(reChargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(reChargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(reChargeActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            //Toast.makeText(reChargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            Toast.makeText(reChargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        }
    };

    private void reCharge() {
        mRetrofit.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("walletId", walletId);
        map.put("amount", String.valueOf(moneyText.getText()));
        map.put("type", "1");
        mRetrofit.reCharge(reChargeActivity.this, map);
    }

    private void reChargeZFB(String price) {
        // 订单信息
        getOrderInfo("充值", "应用充值", price);
        String orderInfo = "app_id="+APP_ID+
                "&biz_content="+biz_content+
                orderInfo1+
                "&notify_url=https://api.xx.com/receive_notify.htm"+
                orderInfo2;
        // 对订单信息进行签名
        String sign = sign(orderInfo);
        String encodeBiz = null;
        String encodeUrl = null;
        String encodeSign = null;
        try {
            // 获得请求字符串
            encodeBiz = URLEncoder.encode(biz_content, "utf-8");
            encodeUrl = URLEncoder.encode("https://api.xx.com/receive_notify.htm", "utf-8");
            encodeSign = URLEncoder.encode(sign, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String payInfo = "app_id=" + APP_ID +
                "&biz_content=" +encodeBiz +
                orderInfo1 +
                "&notify_url="+ encodeUrl +
                orderInfo2 +
                "&sign=" + encodeSign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(reChargeActivity.this);
                Map <String,String> result = alipay.payV2(payInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private void getOrderInfo(String subject, String body, String price) {

        /*------请求参数-------*/

        // 业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
        //orderInfo = orderInfo + "&biz_content={";
        biz_content = "{";

        // 商品详情
        //orderInfo = orderInfo + "\"body\""+":"+ "\""+ body + "\"" + ",";
        biz_content = biz_content + "\"body\""+":"+ "\""+ body + "\"" + ",";

        // 商品的标题
        //orderInfo = orderInfo + "\"subject\""+":"+ "\""+ subject + "\"" + ",";
        biz_content = biz_content + "\"subject\""+":"+ "\""+ subject + "\"" + ",";

        // 客户网站唯一订单号
        String list = getOutTradeNo();
        //orderInfo = orderInfo + "\"out_trade_no\""+":"+ "\""+ list + "\"" + ",";
        biz_content = biz_content + "\"out_trade_no\""+":"+ "\""+ list + "\"" + ",";

        /*该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。
        m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
        该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。*/
        //orderInfo = orderInfo + "\"out_trade_no\""+":"+ "\""+ "90m" + "\"" + ",";
        biz_content = biz_content + "\"out_trade_no\""+":"+ "\""+ "1m" + "\"" + ",";

        //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        //orderInfo = orderInfo + "\"total_amount\""+":"+ "\""+ price + "\"" + ",";
        biz_content = biz_content + "\"total_amount\""+":"+ "\""+ price + "\"" + ",";

        // 销售产品码，商家和支付宝签约的产品码，APP支付功能中该值固定为： QUICK_MSECURITY_PAY
        //orderInfo = orderInfo + "\"product_code\""+":"+ "\""+ "QUICK_MSECURITY_PAY" + "\"" + "}";
        biz_content = biz_content + "\"product_code\""+":"+ "\""+ "QUICK_MSECURITY_PAY" + "\"" + "}";

        // 支付宝分配给开发者的应用ID
        //orderInfo = orderInfo + "app_id=" + APP_ID;

        /*------公共参数-------*/

        /*// 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + "2088032440615717" + "\"";*/

        /* 请求使用的编码格式，APP 支付建议使用 utf-8 格式，
        如使用gbk,gb2312 等格式会有“系统繁忙，请稍后再试”的报错*/
        orderInfo1 = orderInfo1 + "&charset=utf-8";

        // 接口名称
        orderInfo1 = orderInfo1 + "&method=alipay.trade.app.pay";


        // 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
        orderInfo2 = orderInfo2 + "&sign_type=RSA2";

        // 发送请求的时间
        Date date = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo2 = orderInfo2 + "&timestamp=" + String.valueOf(dateFormat.format(date));

        // 调用的接口版本，固定为1.0
        orderInfo2 = orderInfo2 + "&version=1.0";

        // 支付宝服务器主动通知商户服务器里指定的页面 http/https 路径，建议商户使用 https。
        //orderInfo = orderInfo + "&notify_url=https://api.xx.com/receive_notify.html";

    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE, true);
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

}
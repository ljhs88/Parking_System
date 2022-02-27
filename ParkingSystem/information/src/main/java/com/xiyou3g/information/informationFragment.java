package com.xiyou3g.information;

import com.bumptech.glide.Glide;
import com.xiyou3g.information.Utility.StringAndBitmap;
import com.xiyou3g.information.bean.informationBean;
import com.xiyou3g.information.bean.requestInformationBean;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.information.retrofit.Api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.xiyou3g.information.retrofit.Api;

import org.w3c.dom.Text;

public class informationFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button personal_button;
    private Button history_button;
    private ImageView image_head;
    private ImageButton backButton;
    private TextView textNickname;
    private TextView textId;

    private Bitmap backBitmap;
    private Retrofit retrofit;
    private Api api;

    private String userid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.information_fragment, container, false);
        Log.d("123", "onCreateView");
        // 获取控件实例
        getViewId();
        // 设置控件内容
        //获取网络数据
        getContent();
        // 设置点击事件
        personal_button.setOnClickListener(this);
        history_button.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return view;
    }

    private void getContent() {
        //步骤4:创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl("http://101.201.78.192:8888/") // 设置网络请求baseUrl
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        // 步骤5:创建网络请求接口的实例
        api = retrofit.create(Api.class);
        //步骤6：对发送请求进行封装:
        requestInformationBean request = new requestInformationBean("946468093863919616","CodePianist",
                "1", "2002-03-23T14:18:46.015+00:00", "中国", "陕西省", "西安市",
                "长安区", "手执烟火谋生活，心怀诗意以谋爱");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), request.toString());
        retrofit2.Call<informationBean> call = api.postBody(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                Log.d("123", "response");
                if (response.body()!=null) {
                    informationBean bean = response.body();
                    textNickname.setText(bean.getData().getNickname());
                    textId.setText(bean.getData().getId());
                    Bitmap headBitmap = StringAndBitmap.stringToBitmap(bean.getData().getFace());
                    image_head.setImageBitmap(headBitmap);
                    Bitmap backBitmap = StringAndBitmap.stringToBitmap(bean.getData().getBgImg());
                    backButton.setImageBitmap(backBitmap);
                    /*Glide.with(getContext()).load("https://img2.baidu.com/it/u=475888581,992476249&fm=" +
                                    "253&fmt=auto&app=138&f=JPEG?w=256&h=256").into(image_head);
                    Glide.with(getContext()).load(bean.getData().getBgImg()).into(backButton);*/
                    Log.d("123", "glide success");
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

    public void getViewId() {
        personal_button = view.findViewById ( R.id.personal_button );
        history_button = view.findViewById(R.id.history);
        image_head = view.findViewById(R.id.head);
        backButton = view.findViewById(R.id.back);
        textId = view.findViewById(R.id.user_account);
        textNickname = view.findViewById(R.id.username);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.personal_button) {
            Intent intent = new Intent(getContext(), personActivity.class);
            intent.putExtra("select fragment", "personal");
            startActivity(intent);
        } else if (id == R.id.history) {
            Intent intent1 = new Intent(getContext(), personActivity.class);
            intent1.putExtra("select fragment", "history");
            startActivity(intent1);
        } else if (id == R.id.back) {
            Intent intent2 = new Intent(Intent.ACTION_PICK, null);
            intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            //intent 待启动的Intent 100（requestCode）请求码，返回时用来区分是那次请求
            startActivityForResult(intent2, 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2) {
            if (data != null) {
                // 得到图片的路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == 3) {
            // 从剪切图片返回的数据
            if (data != null) {
                getPic(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        // 这里吐槽下：X Y的值在返回的是data的时候，不同的手机能够承受的上限是不一样的，
        // 举个例子：之前写到405：VIVO X6没有任何问题，而在小米note4上就抛了SecurityException！
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        //intent.putExtra("outputFormat", "PNG");// 图片格式
        //intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity
        startActivityForResult(intent, 3);
    }

    /**
     * @author xixili
     * created at 2016/2/27 14:32
     * 获取剪切之后的图片
     */
    private void getPic(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            backBitmap = extras.getParcelable("data");//转换为Bitmap类型

            if(backBitmap!=null){
                //aCache.put("ShakeBgFromUser",photo);    //保存在缓存里，ACache是我用的一个缓存框架
                backButton.setImageBitmap(backBitmap);//展示
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setBack(userid, "1", StringAndBitmap.bitmapToString(backBitmap));
    }

    private void setBack(String userid, String type, String headString) {
        retrofit2.Call<informationBean> call = api.postBody(userid, type, headString);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                Log.d("123", "response");
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

}

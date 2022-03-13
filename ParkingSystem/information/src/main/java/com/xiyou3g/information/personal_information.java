package com.xiyou3g.information;

import static android.app.Activity.RESULT_OK;

import static com.alibaba.android.arouter.compiler.utils.Consts.TAG;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.xiyou3g.information.Utility.StringAndBitmap;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.xiyou3g.information.bean.personal_inf;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiyou3g.information.Utility.StringAndBitmap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.xiyou3g.information.bean.requestInformationBean;
import com.xiyou3g.information.retrofit.Api;
import com.xiyou3g.information.bean.informationBean;
import com.xiyou3g.information.retrofit.mRetrofit;

public class personal_information extends Fragment implements View.OnClickListener {

    private View view;
    private Button back;

    private ImageView head_image;
    private Button head_button;
    private Bitmap headBitmap;

    private EditText edit_name;
    private EditText edit_male;
    private EditText edit_birthday;
    private EditText edit_phone;
    private EditText edit_location;
    private EditText edit_personality;

    private String userid;
    private String mobile;
    private String smsCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("123", "OnCreateView");
        view = inflater.inflate(R.layout.fragment_personnal_information, container, false);

        // 获取userid
        SharedPreferences pref = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userid","");

        userid = "946762136657330176";
        mobile = "18992020668";
        smsCode = "376646";

        getEditTextId();

        //getContent();

        head_button.setOnClickListener(this);
        back.setOnClickListener(this);
        return view;
    }

    private void getContent() {
        //步骤6：对发送请求进行封装:
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),"{\n" +
                "\t\"id\":\"" + mobile + "\",\n\n" +
                "\t\"nickname\":\"" + smsCode  + "\",\n\n" +
                "}");
        retrofit2.Call<informationBean> call = mRetrofit.getInstance().api.postBodyLogin(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                Log.d("123", "response1");
                if (response.body()!=null) {
                    Log.d("123", response.body().toString());
                    informationBean bean = response.body();
                    edit_name.setText(bean.getData().getNickname());
                    if (bean.getData().getSex() == 1) {
                        edit_male.setText("男");
                    } else {
                        edit_male.setText("女");
                    }
                    edit_birthday.setText(bean.getData().getBirthday());
                    edit_phone.setText(bean.getData().getMobile());
                    edit_location.setText(bean.getData().getDistrict());
                    edit_personality.setText(bean.getData().getDescription());
                    Glide.with(getContext()).load(bean.getData().getFace()).into(head_image);
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.head_change) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            //intent 待启动的Intent 100（requestCode）请求码，返回时用来区分是那次请求
            startActivityForResult(intent, 2);
        } else if (id == R.id.back) {
            String name = String.valueOf(edit_name.getText());
            String mobile = String.valueOf(edit_phone.getText());
            //String head = StringAndBitmap.bitmapToString(headBitmap);
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("mobile", mobile);
            intent.putExtra("head", headBitmap);
            getActivity().setResult(1, intent);
            Log.d("123", "back");
            getActivity().finish();
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

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
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
     * 获取剪切之后的图片
     */
    private void getPic(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            headBitmap = extras.getParcelable("data");//转换为Bitmap类型
            if(headBitmap!=null){
                Log.d("123", "展示头像");
                head_image.setImageBitmap(headBitmap);//展示
            }
        }
    }

    /**
     * 获取EditTextId
     */
    private void getEditTextId() {
        edit_name = view.findViewById(R.id.name);
        edit_male = view.findViewById(R.id.male);
        edit_phone = view.findViewById(R.id.phone_num);
        edit_birthday = view.findViewById(R.id.birthday);
        edit_location = view.findViewById(R.id.location);
        edit_personality = view.findViewById(R.id.personality);
        head_image = view.findViewById(R.id.head_photo);
        head_button = view.findViewById(R.id.head_change);
        back = view.findViewById(R.id.back);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("123", "ViewOnDestroy2");
        // 更新头像
        if (headBitmap != null) {
            mRetrofit retrofit = new mRetrofit();
            File file = StringAndBitmap.getFile(headBitmap);
            retrofit.setHttpPortrait(file.getAbsolutePath(), userid, "0");
        }
        // 更新内容
        setContent(userid);
    }

    /**
     * 更新信息
     * @param id
     */
    private void setContent(String id) {
        //步骤6：对发送请求进行封装:
        String sex = "0";
        if (String.valueOf(edit_male.getText()).equals("男")) {
            sex = "1";
        }
        requestInformationBean request = new requestInformationBean(id,String.valueOf(edit_name.getText()),
                sex, String.valueOf(edit_birthday.getText()), "中国", "陕西省", "西安市",
                String.valueOf(edit_location.getText()), String.valueOf(edit_personality.getText()));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), request.toString());
        mRetrofit.getInstance().setContent(requestBody);
    }

}
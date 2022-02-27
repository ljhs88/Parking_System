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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.xiyou3g.information.bean.personal_inf;

import org.litepal.LitePal;

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
    private Retrofit retrofit;
    private Api api;

    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("123", "OnCreateView");
        view = inflater.inflate(R.layout.fragment_personnal_information, container, false);

        // 获取userid
        SharedPreferences pref = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userid","");

        //步骤4:创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl("http://101.201.78.192:8888/") // 设置网络请求baseUrl
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        // 步骤5:创建网络请求接口的实例
        api = retrofit.create(Api.class);
        LitePal.getDatabase();

        getEditTextId();

        getContent();

        //setEditText();

        head_button.setOnClickListener(this);
        back.setOnClickListener(this);
        return view;
    }

    private void getContent() {
        //步骤6：对发送请求进行封装:
        requestInformationBean request = new requestInformationBean("946468093863919616","CodePianist",
                "1", "2002-03-23T14:18:46.015+00:00", "中国", "陕西省", "西安市",
                "长安区", "手执烟火谋生活，心怀诗意以谋爱");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), request.toString());
        retrofit2.Call<informationBean> call = api.postBody(requestBody);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                Log.d("123", "response");
                if (response.body()!=null) {
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

                    headBitmap = StringAndBitmap.stringToBitmap(bean.getData().getFace());
                    head_image.setImageBitmap(headBitmap);
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
            getActivity().onBackPressed();
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
     * @author xixili
     * created at 2016/2/27 14:32
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

    /*private void setEditText() {
        List<personal_inf> dbList = LitePal.findAll(personal_inf.class);
        if (dbList.size() > 0) {
            edit_name.setText(dbList.get(0).getName());
            edit_male.setText(dbList.get(0).getMale());
            edit_phone.setText(dbList.get(0).getPhone_member());
            edit_birthday.setText(dbList.get(0).getBirthday());
            edit_location.setText(dbList.get(0).getLocation());
            edit_personality.setText(dbList.get(0).getPerson_sign());
            headBitmap = StringAndBitmap.stringToBitmap(dbList.get(0).getHead_bitmap());
            head_image.setImageBitmap(headBitmap);
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("123", "onDestroy");
        /*LitePal.deleteAll(personal_inf.class);
        personal_inf db = new personal_inf();
        db.setName(String.valueOf(edit_name.getText()));
        db.setMale(String.valueOf(edit_male.getText()));
        db.setBirthday(String.valueOf(edit_birthday.getText()));
        db.setPhone_member(String.valueOf(edit_phone.getText()));
        db.setLocation(String.valueOf(edit_location.getText()));
        db.setPerson_sign(String.valueOf(edit_personality.getText()));
        String headString = StringAndBitmap.bitmapToString(headBitmap);
        db.setHead_bitmap(headString);
        db.save();*/
        // 更新头像
        setHead(userid, "0", StringAndBitmap.bitmapToString(headBitmap));
        setContent(userid);
    }

    /**
     * 更新头像
     * @param userid
     * @param type
     * @param headString
     */
    private void setHead(String userid, String type, String headString) {
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
        retrofit2.Call<informationBean> call = api.postBody(requestBody);
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
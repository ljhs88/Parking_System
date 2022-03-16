package com.xiyou3g.information;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xiyou3g.information.Utility.StringAndBitmap;
import com.xiyou3g.information.bean.informationBean;
import com.xiyou3g.information.bean.requestInformationBean;
import com.xiyou3g.information.Utility.HttpImgThread;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.xiyou3g.information.retrofit.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.xiyou3g.information.retrofit.mRetrofit;

@Route(path = "/information/informationFragment")
public class informationFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button personal_button;
    private Button history_button;
    private ImageView image_head;
    private ImageButton backButton;
    private TextView textNickname;
    private TextView textPhone;

    private Bitmap backBitmap;

    private String userid;
    private Button backToDesk_button;
    private Button changeAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.information_fragment, container, false);

        isGrantExternalRW(getActivity());

        userid = "946762136657330176";

        // 获取控件实例
        getViewId();
        //获取网络数据
        getContent(userid);
        // 设置点击事件
        personal_button.setOnClickListener(this);
        history_button.setOnClickListener(this);
        backToDesk_button.setOnClickListener(this);
        changeAccount.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return view;
    }

    /**
     * 获取个人信息
     * @param userid
     */
    private void getContent(String userid) {
        Retrofit retrofit = mRetrofit.getInstance();
        Api api = mRetrofit.api;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), userid);
        retrofit2.Call<informationBean> call = api.post(userid);
        //步骤7:发送网络请求(异步)
        call.enqueue(new Callback<informationBean>() {
            @Override
            public void onResponse(Call<informationBean> call, Response<informationBean> response) {
                if (response.body() == null)
                Log.d("123","GET：null");
                if (response.body()!=null) {
                    informationBean bean = response.body();
                    textNickname.setText(bean.getData().getNickname());
                    textPhone.setText(bean.getData().getMobile());
                    //Log.d("123", "faceImg:"+bean.getData().getFace());
                    Glide.with(getContext()).load("http"+bean.getData().getFace().substring(5)).into(image_head);
                    //Log.d("123", "bgImg:"+bean.getData().getBgImg());
                    Glide.with(getContext()).load("http"+bean.getData().getBgImg().substring(5)).into(backButton);
                    //Log.d("123", "glide success");
                }
            }

            @Override
            public void onFailure(Call<informationBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

    /**
     * 获取储存权限
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }

    public void getViewId() {
        backToDesk_button = view.findViewById(R.id.backToDesk);
        changeAccount = view.findViewById(R.id.changeAccount);
        personal_button = view.findViewById ( R.id.personal_button );
        history_button = view.findViewById(R.id.historyTo);
        image_head = view.findViewById(R.id.head);
        backButton = view.findViewById(R.id.back);
        textPhone = view.findViewById(R.id.user_account);
        textNickname = view.findViewById(R.id.username);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.personal_button) {
            Intent intent = new Intent(getContext(), personActivity.class);
            intent.putExtra("select fragment", "personal");
            startActivityForResult(intent, 1);
        } else if (id == R.id.historyTo) {
            Intent intent1 = new Intent(getContext(), personActivity.class);
            intent1.putExtra("select fragment", "history");
            startActivity(intent1);
        } else if (id == R.id.back) {
            Intent intent2 = new Intent(Intent.ACTION_PICK, null);
            intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            //intent 待启动的Intent 100（requestCode）请求码，返回时用来区分是那次请求
            startActivityForResult(intent2, 2);
        } else if (id == R.id.changeAccount) {
            getActivity().finish();
            ARouter.getInstance().build("/customer/Cus_LoginActivity").navigation();
        } else if (id == R.id.backToDesk) {
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Log.d("123", "onActivityResult");
        if (resultCode == 1) {
            String name = data.getStringExtra("name");
            String mobile = data.getStringExtra("mobile");
            //String head = data.getStringExtra("head");
            Bitmap headBitmap = (Bitmap) data.getParcelableExtra("head");
            if (headBitmap != null)
                image_head.setImageBitmap(headBitmap);
            if (!"".equals(name))
                textNickname.setText(name);
            if (!"".equals(mobile))
                textPhone.setText(mobile);
        } else if (requestCode == 2) {
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
        if (backBitmap != null) {
            //Log.d("123", "onDestroy:"+StringAndBitmap.getFile(backBitmap));
            Retrofit retrofit = mRetrofit.getInstance();
            File file = StringAndBitmap.getFile(backBitmap);
            //Log.d("123", "retrofit"+userid);
            //Log.d("123", file.getName() +","+ file.getAbsolutePath());
            mRetrofit.setHttpPortrait(file.getAbsolutePath(), userid, "1");
        }
    }


}

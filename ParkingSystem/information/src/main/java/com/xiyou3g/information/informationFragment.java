package com.xiyou3g.information;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xiyou3g.information.Utility.PhotoChoice;
import com.xiyou3g.information.Utility.StringAndBitmap;
import com.xiyou3g.information.bean.informationBean;
import com.xiyou3g.information.retrofit.Api;
import com.xiyou3g.information.retrofit.mRetrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Route(path = "/information/informationFragment")
public class informationFragment extends Fragment implements View.OnClickListener {

    private View view;

    private final int BACKGROUND_PHOTO = 2;
    private final int BACKGROUND_CROP = 3;

    private Button personal_button;
    private Button history_button;
    private Button history_button2;
    private Button id_cardButton;
    private Button walletButton;
    private Button settingButton;
    private ImageView image_head;
    private ImageButton backButton;
    private TextView textNickname;
    private TextView textPhone;

    private Bitmap backBitmap;

    private String userid;
    private String token;
    private String mobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.information_fragment, container, false);

        isGrantExternalRW(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        //userid = "946762136657330176";

        // 获取控件实例
        getViewId();
        //获取网络数据
        getContent(userid);

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
        settingButton = view.findViewById(R.id.setting_To);
        walletButton = view.findViewById(R.id.wallet_To);
        history_button2 = view.findViewById(R.id.historyTo2);
        id_cardButton = view.findViewById(R.id.Id_cardTo);
        personal_button = view.findViewById ( R.id.personal_button );
        history_button = view.findViewById(R.id.historyTo);
        image_head = view.findViewById(R.id.head);
        backButton = view.findViewById(R.id.background);
        textPhone = view.findViewById(R.id.user_account);
        textNickname = view.findViewById(R.id.username);
        personal_button.setOnClickListener(this);
        history_button.setOnClickListener(this);
        backButton.setOnClickListener(this);
        history_button2.setOnClickListener(this);
        id_cardButton.setOnClickListener(this);
        walletButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.personal_button) {
            Intent intent = new Intent(getContext(), personActivity.class);
            intent.putExtra("select fragment", "personal");
            startActivityForResult(intent, 1);
        } else if (id == R.id.historyTo) {
            ARouter.getInstance().build("/pay/Cus_PayActivity").navigation();
        } else if (id == R.id.historyTo2) {
            ARouter.getInstance().build("/pay/Cus_PayActivity").navigation();
        } else if (id == R.id.Id_cardTo) {
            Intent intent1 = new Intent(getContext(), personActivity.class);
            intent1.putExtra("select fragment", "IdCard");
            startActivity(intent1);
        } else if (id == R.id.wallet_To) {
            Intent intent1 = new Intent(getContext(), personActivity.class);
            intent1.putExtra("select fragment", "wallet");
            startActivity(intent1);
        } else if (id == R.id.setting_To) {
            Intent intent1 = new Intent(getContext(), personActivity.class);
            intent1.putExtra("select fragment", "setting");
            startActivity(intent1);
        } else if (id == R.id.background) {
            Intent intent2 = new Intent(Intent.ACTION_PICK, null);
            intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            //intent 待启动的Intent 100（requestCode）请求码，返回时用来区分是那次请求
            startActivityForResult(intent2, BACKGROUND_PHOTO);
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
        } else if (requestCode == BACKGROUND_PHOTO) {
            if (data != null) {
                // 得到图片的路径
                Uri uri = data.getData();
                Intent intent = PhotoChoice.crop(uri, "background");
                startActivityForResult(intent, BACKGROUND_CROP);
            }
        } else if (requestCode == BACKGROUND_CROP) {
            // 从剪切图片返回的数据
            if (data != null) {
                backBitmap = PhotoChoice.getPic(data);
                backButton.setImageBitmap(backBitmap);//展示
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (backBitmap != null) {
            //Log.d("123", "onDestroy:"+StringAndBitmap.getFile(backBitmap));
            File file = StringAndBitmap.getFile(backBitmap);
            //Log.d("123", "retrofit"+userid);
            //Log.d("123", file.getName() +","+ file.getAbsolutePath());
            mRetrofit.setHttpPortrait(getActivity(),file.getAbsolutePath(), userid, "1");
        }
    }


}

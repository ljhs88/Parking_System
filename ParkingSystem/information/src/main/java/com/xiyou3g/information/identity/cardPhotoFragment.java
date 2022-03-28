package com.xiyou3g.information.identity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.information.R;
import com.xiyou3g.information.identityActivity;
import com.xiyou3g.information.retrofit.mRetrofit;
import com.xiyou3g.information.Utility.StringAndBitmap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiyou3g.information.Utility.PhotoChoice;
import com.xiyou3g.information.Utility.ToastUtil;

public class cardPhotoFragment extends Fragment implements View.OnClickListener {

    private View view;

    private String userid;
    private String token;
    private String mobile;

    private final int PHOTO = 1;
    private final int CROP = 2;
    private final int CAMERA = 3;
    private final int CROP_CAMERA = 4;
    private final int PERSON_PHOTO = 200;
    private final int FLOWER_PHOTO = 300;
    private int PHOTO_KEY;

    private Dialog bottomDialog;

    private Button back;
    private Button personButton;
    private Button flowerButton;
    private Button photoUploadButton;
    private ImageView personImage;
    private ImageView flowerImage;
    private Bitmap personBitmap = null;
    private Bitmap flowerBitmap = null;
    private Uri imageUri;
    private File tempFile;

    private boolean isPhotoUpload = false;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_identity_cardphoto, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");
        //userid = "946762136657330176";

        getViewId();

        /**
         * 底部选择
         */
        setSelectDialog();

        return view;
    }

    public void setSelectDialog() {
        bottomDialog = new Dialog(getContext(), R.style.BottomDialog);//自定义样式
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_choice, null);
        bottomDialog.setContentView(contentView);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        Button choosePhoto = contentView.findViewById(R.id.picture_choice);
        Button takePhoto = contentView.findViewById(R.id.picture_camera);
        Button cancel = contentView.findViewById(R.id.picture_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void getViewId() {
        back = view.findViewById(R.id.back);
        personImage = view.findViewById(R.id.person_photo);
        flowerImage = view.findViewById(R.id.flower_photo);
        photoUploadButton = view.findViewById(R.id.photoUpload);
        personButton = view.findViewById(R.id.person_photo_change);
        flowerButton = view.findViewById(R.id.flower_change);
        back.setOnClickListener(this);
        personButton.setOnClickListener(this);
        flowerButton.setOnClickListener(this);
        photoUploadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
          getActivity().onBackPressed();
        } else if (id == R.id.person_photo_change) {
            PHOTO_KEY = PERSON_PHOTO;
            bottomDialog.show();
        } else if (id == R.id.flower_change) {
            PHOTO_KEY = FLOWER_PHOTO;
            bottomDialog.show();
        }  else if (id == R.id.picture_camera) {
            Intent intent = PhotoChoice.openCamera(getActivity());
            imageUri = PhotoChoice.imageUri;
            tempFile = PhotoChoice.tempFile;
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, CAMERA);
            bottomDialog.dismiss();
        } else if (id == R.id.picture_choice) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, PHOTO);
            bottomDialog.dismiss();
        } else if (id == R.id.photoUpload) {
            setPhoto();
        }
    }

    private void setPhoto() {
        if (personBitmap != null && flowerBitmap != null) {
            isPhotoUpload = true;
            File file1 = StringAndBitmap.getFile(personBitmap);
            mRetrofit.setCardPhoto(getActivity(), file1.getAbsolutePath(),
                    userid, 1);
            Intent intent = new Intent();
            intent.putExtra("type", "cardPhoto");
            getActivity().setResult(2, intent);
            getActivity().onBackPressed();
        } else if (personBitmap == null) {
            ToastUtil.getToast(getContext(), "请上传身份证正面!");
        } else if (flowerBitmap == null) {
            ToastUtil.getToast(getContext(),"请上传身份证国徽面!");
        } else if (personBitmap == null && flowerBitmap == null) {
            ToastUtil.getToast(getContext(), "请先上传身份证!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isPhotoUpload) {
            File file2 = StringAndBitmap.getFile(flowerBitmap);
            mRetrofit.setCardPhoto(getActivity(), file2.getAbsolutePath(),
                    userid, 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PHOTO) {
            if (data != null) {
                // 得到图片的路径
                Uri uri = data.getData();
                Intent intent = PhotoChoice.crop(uri, "card");
                startActivityForResult(intent, CROP);
            }
        } else if (requestCode == CAMERA) {
            //Intent intent = PhotoChoice.cropCamera(imageUri);
            //startActivityForResult(intent, CROP_CAMERA);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(imageUri, "image/*");
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CROP_CAMERA);
        } else if (requestCode == CROP) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = PhotoChoice.getPic(data);
                if (PHOTO_KEY == PERSON_PHOTO) {
                    personBitmap = bitmap;
                    personImage.setImageBitmap(personBitmap);
                } else if (PHOTO_KEY == FLOWER_PHOTO) {
                    flowerBitmap = bitmap;
                    flowerImage.setImageBitmap(flowerBitmap);
                }
            }
        } else if (requestCode == CROP_CAMERA) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().
                        openInputStream(imageUri));
                if (PHOTO_KEY == PERSON_PHOTO) {
                    personBitmap = bitmap;
                    personImage.setImageBitmap(personBitmap);
                } else if (PHOTO_KEY == FLOWER_PHOTO) {
                    flowerBitmap = bitmap;
                    flowerImage.setImageBitmap(flowerBitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

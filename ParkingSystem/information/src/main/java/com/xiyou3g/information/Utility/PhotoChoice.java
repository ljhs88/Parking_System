package com.xiyou3g.information.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.xiyou3g.information.identityActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoChoice {

    public static Bitmap bitmap;
    public static Uri imageUri;
    public static File tempFile;

    public static Intent openCamera(Activity activity) {
        //获取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(activity,"请开启存储权限",Toast.LENGTH_SHORT).show();
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        return intent;
    }

    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 剪裁
     * @param uri
     * @return
     */
    public static Intent crop(Uri uri, String type) {
        // 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例
        if (type.equals("head")) {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        } else if (type.equals("background")) {
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
        } else if (type.equals("card")) {
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
        }
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        //intent.putExtra("outputFormat", "PNG");// 图片格式
        //intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 返回一个带有返回值的Activity
        return intent;
    }

    /**
     * 拍照剪裁
     * @param uri
     * @return
     */
    public static Intent cropCamera(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * @author xixili
     * created at 2016/2/27 14:32
     * 获取剪切之后的图片
     */
    public static Bitmap getPic(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");//转换为Bitmap类型

            if(bitmap!=null){
                Log.d("123", "getpic");
                return bitmap;
            }
        }
        return null;
    }



}

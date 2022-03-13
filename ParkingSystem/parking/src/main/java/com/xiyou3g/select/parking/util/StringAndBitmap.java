package com.xiyou3g.select.parking.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class StringAndBitmap {
    //图片与String之间的转换，便于将图片存储在数据库中
    private static Bitmap bitmap;
    private static String string;
    public static Bitmap stringToBitmap(String string){
        //数据库中的String类型转换成Bitmap
        if(string!=null){
            byte[] bytes= Base64.decode(string,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            return bitmap;
        }
        else {
            return null;
        }
    }
    public static String bitmapToString(Bitmap bitmap){
        //用户在活动中上传的图片转换成String进行存储
        if(bitmap!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();// 转为byte数组
            string=Base64.encodeToString(bytes,Base64.DEFAULT);
            return string;
        }
        else{
            return "";
        }
    }

}

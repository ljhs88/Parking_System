package com.xiyou3g.information.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringAndBitmap {
    //图片与String之间的转换，便于将图片存储在数据库中
    private static Bitmap bitmap;
    private static String string;
    public static Bitmap stringToBitmap(String string){
        //数据库中的String类型转换成Bitmap
        if(string!=null){
            byte[] bytes= Base64.decode(string,Base64.URL_SAFE);
            bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            return bitmap;
        } else {
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
        } else {
            return "";
        }
    }

    //最终上传的Bitmap保存为File对象
    public static File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


}

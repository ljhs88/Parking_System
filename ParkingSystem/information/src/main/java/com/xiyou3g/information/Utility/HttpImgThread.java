package com.xiyou3g.information.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kirito on 2016.10.24.
 */

public class HttpImgThread extends Thread {
    private Handler handler;
    private ImageView iv;
    private String strurl;
    File file;

    private static final String TAG = "HttpImgThread";

    public HttpImgThread (Handler handler,ImageView iv,String strurl){
        this.handler = handler;
        this.iv = iv;
        this.strurl = strurl;
    }

    @Override
    public void run() {
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL(strurl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(5000);
            con.setDoInput(true);
            File parent = Environment.getExternalStorageDirectory();
            file = new File(parent, String.valueOf(System.currentTimeMillis()));
            FileOutputStream fos = new FileOutputStream(file);
            InputStream in = con.getInputStream();
            Log.e(TAG, "run: path---"+file.getAbsolutePath() );

            byte ch[] = new byte[2 * 1024];
            int len;
            if (fos != null){
                while ((len = in.read(ch)) != -1){
                    fos.write(ch,0,len);
                }
                in.close();
                fos.close();
            }
            /*BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            BufferedWriter bw = null;
            String s;
            while ((s = br.readLine()) != null){
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                //以字符串的形式写入数据，无法读取图片
                bw.write(s);
            }
            br.close();
            bw.close();*/
            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (bitmap != null){
                        iv.setImageBitmap(bitmap);
                    }
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

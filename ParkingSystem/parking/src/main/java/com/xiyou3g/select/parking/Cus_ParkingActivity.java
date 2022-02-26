package com.xiyou3g.select.parking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.xiyou3g.select.parking.UI.SetChargeUI;
import com.xiyou3g.select.parking.UI.SetParkingUI;
import com.xiyou3g.select.parking.UI.SetStallUI;
import com.xiyou3g.select.parking.UI.SetUI;
import com.xiyou3g.select.parking.bean.CreateInformation;
import com.xiyou3g.select.parking.util.CameraUtil;
import com.xiyou3g.select.parking.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

@Route(path = "/parking/Cus_ParkingActivity")
public class Cus_ParkingActivity extends AppCompatActivity implements View.OnClickListener {

    private static int STATUS = 1;
    private String title = "创建充电桩";
    private static final int CHARGING = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;

    public static final int TAKE_PHOTO = 1;

    private ImageView picture;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_parking);
        //get();

        setSupportActionBar(findViewById(R.id.create_parking));
        setSupportActionBar(findViewById(R.id.create_parking));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setUI();
    }

    private void setUI() {

        Toolbar toolbar = findViewById(R.id.create_parking);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitle(title);


        SetUI setChargeUI = new SetChargeUI(this);
        SetUI setParkingUI = new SetParkingUI(this);
        SetUI setStallUI = new SetStallUI(this);
        if ("创建充电桩".equals(title)) {
            STATUS = CHARGING;
            set(setChargeUI);
        } else if ("创建停车场".equals(title)) {
            STATUS = PARKING;
            set(setParkingUI);
        } else if ("创建停车位".equals(title)) {
            STATUS = STALL;
            set(setStallUI);
        }
        Button commit_button = findViewById(R.id.commit_button);
        commit_button.setOnClickListener(this);

        setImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        File mediaFile = new File(mediaStorageDir.getPath()
                + File.separator
                + "Pictures/temp.jpg");
        mediaFile.deleteOnExit();
        Log.d("TAG", "onDestroy: ");
    }

    public void setImage() {
        picture = findViewById(R.id.select_photo);
        picture.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.setContentView(R.layout.layout_bottomsheetdialog_select_photo);
            bottomSheetDialog.show();
            ImageView image_arrow = bottomSheetDialog.getWindow().findViewById(R.id.image_arrows_select_photo);
            TextView take_photo = bottomSheetDialog.getWindow().findViewById(R.id.take_photo);
            TextView select_photo = bottomSheetDialog.getWindow().findViewById(R.id.select_photo);
            TextView cancel = bottomSheetDialog.getWindow().findViewById(R.id.select_photo_cancel);
            image_arrow.setOnClickListener(View -> bottomSheetDialog.cancel());
            take_photo.setOnClickListener(View -> {
                //打开照相机
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = CameraUtil.getOutputMediaFileUri(this);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                //Android7.0添加临时权限标记，此步千万别忘了
                openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(openCameraIntent, TAKE_PHOTO);
                bottomSheetDialog.cancel();
            });
            select_photo.setOnClickListener(View -> {
                if (ContextCompat.checkSelfPermission(Cus_ParkingActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Cus_ParkingActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                bottomSheetDialog.cancel();
            });
            cancel.setOnClickListener(View -> bottomSheetDialog.cancel());
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }

    private void set(SetUI setUI) {
        setUI.setText();
        setUI.setEdit();
    }

    private void get() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.commit_button) {
            CreateInformation createInformation = saveInformation();
            if (checkCreateInformation(createInformation)) {
                EventBus.getDefault().postSticky(createInformation);
            } else {
                ToastUtil.getToast(Cus_ParkingActivity.this, "请填写正确的信息");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                ToastUtil.getToast(this, "没有获取你的权限");
            }
        }
    }

    @SuppressLint({"ObsoleteSdkInt", "CheckResult"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                try {
                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    File mediaFile = new File(mediaStorageDir.getPath()
                            + File.separator
                            + "Pictures/temp.jpg");
                    if (mediaFile.exists()) {
                        picture.setPadding(0, 0, 0, 0);
                        Glide.with(this).load(CameraUtil.getBitmapFormUri(this, CameraUtil.getOutputMediaFileUri(this))).into(picture);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    picture.setPadding(0, 0, 0, 0);
                    assert data != null;
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.media.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse
                        ("content://downloads/public_downloads"), Long.parseLong(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            ToastUtil.getToast(this, "无法获取图像");
        }
    }

    @SuppressLint("Range")
    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri,
                null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private CreateInformation saveInformation() {
        EditText editName = findViewById(R.id.parking_edit1);
        EditText editNumber = findViewById(R.id.parking_edit2);
        EditText editPrice = findViewById(R.id.parking_edit3);
        EditText editBrief = findViewById(R.id.parking_edit4);


        String name = editName.getText().toString();
        int number = -1;
        int price = -1;
        String brief = null;
        picture.setDrawingCacheEnabled(true);
        Bitmap selectPhoto = Bitmap.createBitmap(picture.getDrawingCache());
        picture.setDrawingCacheEnabled(false);
        try {
            if (STATUS == CHARGING || STATUS == PARKING) {
                price = Integer.parseInt(editPrice.getText().toString());
                number = Integer.parseInt(editNumber.getText().toString());
                brief = editBrief.getText().toString();
            } else if (STATUS == STALL) {
                price = Integer.parseInt(editNumber.getText().toString());
                brief = editPrice.getText().toString();
            }
        } catch (NumberFormatException e) {
            ToastUtil.getToast(Cus_ParkingActivity.this, "请填写正确的信息");
        }
        return new CreateInformation(name, number, price, brief, selectPhoto);
    }

    private boolean checkCreateInformation(CreateInformation createInformation) {
        if (STATUS != STALL) {
            return !createInformation.getName().equals("") &&
                    createInformation.getNumber() != -1 &&
                    createInformation.getPrice() != -1 &&
                    !createInformation.getBriefIntroduction().equals("") &&
                    picture.getPaddingStart() != 0;
        } else {
            return !createInformation.getName().equals("") &&
                    createInformation.getPrice() != -1 &&
                    createInformation.getBriefIntroduction().equals("") &&
                    picture.getPaddingStart() != 0;
        }
    }

}
package com.xiyou3g.select.parking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.xiyou3g.select.parking.UI.SetChargeUI;
import com.xiyou3g.select.parking.UI.SetParkingUI;
import com.xiyou3g.select.parking.UI.SetStallUI;
import com.xiyou3g.select.parking.UI.SetUI;
import com.xiyou3g.select.parking.api.CreateChargeService;
import com.xiyou3g.select.parking.api.CreateStallService;
import com.xiyou3g.select.parking.bean.CreateChargeData;
import com.xiyou3g.select.parking.bean.CreateChargeResponse;
import com.xiyou3g.select.parking.bean.CreateInformation;
import com.xiyou3g.select.parking.bean.CreateStallData;
import com.xiyou3g.select.parking.bean.CreateStallResponse;
import com.xiyou3g.select.parking.bean.PhotoResponse;
import com.xiyou3g.select.parking.util.CameraUtil;
import com.xiyou3g.select.parking.util.RetrofitManager;
import com.xiyou3g.select.parking.util.StringAndBitmap;
import com.xiyou3g.select.parking.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = "/parking/Cus_ParkingActivity")
public class Cus_ParkingActivity extends AppCompatActivity implements View.OnClickListener,
        GeocodeSearch.OnGeocodeSearchListener {

    private static int STATUS = 1;
    private String title = "创建充电桩";
    private static final int CHARGING = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;

    public static final int TAKE_PHOTO = 1;

    private ImageView picture;
    public static final int CHOOSE_PHOTO = 2;
    private LatLng thisLatLng;
    private final RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private String province;
    private String city;
    private String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_parking);
        get();

        GeocodeSearch geocoderSearch = null;
        try {
            geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(thisLatLng.latitude, thisLatLng.longitude), 200, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
            Log.d("TAG", "onMyLocationChange: ");
        } catch (AMapException e) {
            e.printStackTrace();
        }
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
        try {
            mediaFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        thisLatLng = new LatLng(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0));

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.commit_button) {
            CreateInformation createInformation = saveInformation();
            if (checkCreateInformation(createInformation)) {
                EventBus.getDefault().postSticky(createInformation);
                /*SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    String userId = sharedPreferences.getString("userId", "");
                    String mobile = sharedPreferences.getString("mobile", "");
                    String userToken = sharedPreferences.getString("userToken", "");*/
                if (STATUS == STALL) {
                    CreateStallData createStallData = new CreateStallData();

                    String userId = "946762136657330176";
                    createStallData.setUserId(userId);
                    createStallData.setAuditState(1);
                    createStallData.setIspublish(0);
                    createStallData.setStatus(0);
                    createStallData.setOwnerName(createInformation.getName());
                    createStallData.setOwnerMobile(createInformation.getOwnerMobile());
                    createStallData.setProvince(province);
                    createStallData.setCity(city);
                    createStallData.setDistrict(district);
                    createStallData.setAddress(createInformation.getAddress());
                    createStallData.setLongitude(String.valueOf(thisLatLng.longitude));
                    createStallData.setLatitude(String.valueOf(thisLatLng.latitude));
                    createStallData.setOwnerNum(createInformation.getOwnerNum());
                    createStallData.setHourPrice(createInformation.getPrice());
                    createStallData.setAdminName(createInformation.getAdminName());
                    createStallData.setAdminMobile(createInformation.getAdminMobile());

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),new Gson().toJson(createStallData));

                    CreateStallService createStallService = retrofitManager.getRetrofit().create(CreateStallService.class);
                    createStallService.createStall(userId, requestBody).enqueue(new Callback<CreateStallResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CreateStallResponse> call, @NonNull Response<CreateStallResponse> response) {

                            Log.d("TAG", "onResponse: " + response.body());
                            CreateStallResponse createStallResponse = response.body();

                            Bitmap bitmap = createInformation.getBitmap();
                            File file = StringAndBitmap.getFile(bitmap);

                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
                            Map<String, RequestBody> map = new HashMap<>();
                            assert createStallResponse != null;
                            map.put("spId", RequestBody.create(MediaType.parse("multipart/form-data"), createStallResponse.getData().getId()));
                            map.put("type", RequestBody.create(MediaType.parse("multipart/form-data"), "0"));
                            createStallService.upLoadPhoto(map, body).enqueue(new Callback<PhotoResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<PhotoResponse> call, @NonNull Response<PhotoResponse> response) {
                                    Log.d("TAG", "onResponse: " + response.body());
                                    finish();
                                }

                                @Override
                                public void onFailure(@NonNull Call<PhotoResponse> call, @NonNull Throwable t) {
                                    Log.e("TAG", "onFailure: photo" + t.toString());
                                }
                            });

                        }

                        @Override
                        public void onFailure(@NonNull Call<CreateStallResponse> call, @NonNull Throwable t) {
                            Log.e("TAG", "onFailure: " + t);
                        }
                    });

                } else if (STATUS == CHARGING) {
                    CreateChargeData createChargeData = new CreateChargeData();

                    String userId = "946762136657330176";
                    createChargeData.setUserId(userId);
                    createChargeData.setAuditState(1);
                    createChargeData.setIspublish(0);
                    createChargeData.setStatus(0);
                    createChargeData.setOwnerName(createInformation.getName());
                    createChargeData.setOwnerMobile(createInformation.getOwnerMobile());
                    createChargeData.setProvince(province);
                    createChargeData.setCity(city);
                    createChargeData.setDistrict(district);
                    createChargeData.setAddress(createInformation.getAddress());
                    createChargeData.setLongitude(String.valueOf(thisLatLng.longitude));
                    createChargeData.setLatitude(String.valueOf(thisLatLng.latitude));
                    createChargeData.setOwnerNum(createInformation.getOwnerNum());
                    createChargeData.setHourPrice(createInformation.getPrice());
                    createChargeData.setAdminName(createInformation.getAdminName());
                    createChargeData.setAdminMobile(createInformation.getAdminMobile());
                    createChargeData.setFinePrice(createInformation.getFinePrice());

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),new Gson().toJson(createChargeData));

                    CreateChargeService createChargeService = retrofitManager.getRetrofit().create(CreateChargeService.class);
                    createChargeService.createCharge(userId, requestBody).enqueue(new Callback<CreateChargeResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<CreateChargeResponse> call, @NonNull Response<CreateChargeResponse> response) {

                            Log.d("TAG", "onResponse: " + response.body());
                            CreateChargeResponse createChargeResponse = response.body();
                            File file = StringAndBitmap.getFile(createInformation.getBitmap());
                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
                            Map<String, RequestBody> map = new HashMap<>();
                            assert createChargeResponse != null;
                            map.put("spId", RequestBody.create(MediaType.parse("multipart/form-data"), createChargeResponse.getData().getId()));
                            map.put("type", RequestBody.create(MediaType.parse("multipart/form-data"), "0"));
                            createChargeService.upLoadPhoto(map, body).enqueue(new Callback<PhotoResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<PhotoResponse> call, @NonNull Response<PhotoResponse> response) {
                                    PhotoResponse photoResponse = response.body();
                                    Log.d("TAG", "onResponse: " + photoResponse);
                                    assert photoResponse != null;
                                    if (photoResponse.isSuccess()) {
                                        finish();
                                    }

                                }

                                @Override
                                public void onFailure(@NonNull Call<PhotoResponse> call, @NonNull Throwable t) {
                                    Log.e("TAG", "onFailure: " + t.toString());
                                }
                            });
                        }

                        @Override
                        public void onFailure(@NonNull Call<CreateChargeResponse> call, @NonNull Throwable t) {
                            Log.e("TAG", "onFailure: " + t);
                        }
                    });
                }
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
        EditText ownerName = findViewById(R.id.parking_edit1);
        EditText ownerMobile = findViewById(R.id.parking_edit2);
        EditText address = findViewById(R.id.parking_edit3);
        EditText ownerNum = findViewById(R.id.parking_edit4);
        EditText hourPrice = findViewById(R.id.parking_edit5);
        EditText adminName = findViewById(R.id.parking_edit6);
        EditText adminMobile = findViewById(R.id.parking_edit7);
        EditText finePrice = findViewById(R.id.parking_edit8);

        String oName = ownerName.getText().toString();
        String oMobile = ownerMobile.getText().toString();
        String ads = address.getText().toString();
        String oNum = ownerNum.getText().toString();
        String hPrice = hourPrice.getText().toString();
        String aName = adminName.getText().toString();
        String aMobile = adminMobile.getText().toString();
        String fPrice = finePrice.getText().toString();

        if (STATUS == STALL) {
            return new CreateInformation(STATUS, oName, oMobile, oNum, Integer.parseInt(hPrice), aName, aMobile, -1, ads, ((BitmapDrawable)picture.getDrawable()).getBitmap());
        } else {
            return new CreateInformation(STATUS, oName, oMobile, oNum, Integer.parseInt(hPrice), aName, aMobile, (int) Double.parseDouble(fPrice), ads, ((BitmapDrawable)picture.getDrawable()).getBitmap());
        }
    }

    private boolean checkCreateInformation(CreateInformation createInformation) {
        if (STATUS != STALL) {
            return !createInformation.getName().equals("") &&
                   !createInformation.getOwnerMobile().equals("") &&
                   !createInformation.getAddress().equals("") &&
                   !createInformation.getOwnerNum().equals("") &&
                   !createInformation.getAdminName().equals("") &&
                   !createInformation.getAdminMobile().equals("") &&
                    createInformation.getFinePrice() != -1 &&
                    createInformation.getPrice() != -1 &&
                    picture.getPaddingStart() == 0;
        } else {
            return !createInformation.getName().equals("") &&
                    !createInformation.getOwnerMobile().equals("") &&
                    !createInformation.getAddress().equals("") &&
                    !createInformation.getOwnerNum().equals("") &&
                    !createInformation.getAdminName().equals("") &&
                    !createInformation.getAdminMobile().equals("") &&
                    createInformation.getPrice() != -1 &&
                    picture.getPaddingStart() == 0;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        province = regeocodeAddress.getProvince();
        city = regeocodeAddress.getCity();
        district = regeocodeAddress.getDistrict();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
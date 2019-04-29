package com.example.dell_pc.androidpractice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell_pc.androidpractice.bean.ListBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = UpFileActivity.class.getName();
    private Button bt1;
    private Button bt2;
    private ImageView iv;
    private Uri uri;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();

    }

    private void initView() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        iv = (ImageView) findViewById(R.id.iv);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                takePhoto();
                break;
            case R.id.bt2:
                takePick();
                break;
        }
    }

    private void takePick() {
        if (ContextCompat.checkSelfPermission(UpFileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(UpFileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);

    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(UpFileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(UpFileActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        }
    }

    private void openCamera() {
        mFile = new File(UpFileActivity.this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            uri = Uri.fromFile(mFile);
        } else {
            uri = FileProvider.getUriForFile(UpFileActivity.this, "com.abc.provider", mFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);

    }

    @SuppressLint("NewApi")
    private void initPhotoError() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                uploadFile(mFile);
            } else if (requestCode == 2) {
                Uri data1 = data.getData();

                File mF = getFileFromUri(data1, UpFileActivity.this);
                uploadFile(mF);
            }
        }
    }

    private File getFileFromUri(Uri data1, Context context) {
        if (data1 == null) {
            return null;
        }
        switch (data1.getScheme()) {
            case "content":
                return getFileFromContentUri(data1, context);
            case "file":

                return new File(data1.getPath());
            default:
                return null;

        }
    }

    private File getFileFromContentUri(Uri data1, Context context) {
        File file = null;
        Cursor query = context.getContentResolver().query(data1, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
        if (query.moveToNext()) {

            String string = query.getString(query.getColumnIndex(MediaStore.MediaColumns.DATA));
            query.close();
            if (!TextUtils.isEmpty(string)) {
                file = new File(string);
            }
        }
        return file;

    }

    private void uploadFile(File F) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), F);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1809A")
                .addFormDataPart("file", F.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                Gson gson = new Gson();
                final ListBean upLoadBean = gson.fromJson(str, ListBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(UpFileActivity.this).load(upLoadBean.getData().getUrl())
                                .into(iv);

                        Toast.makeText(UpFileActivity.this, upLoadBean.getRes(), Toast
                                .LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}

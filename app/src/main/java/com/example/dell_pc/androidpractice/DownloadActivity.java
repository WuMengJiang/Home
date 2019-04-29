package com.example.dell_pc.androidpractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dell_pc.androidpractice.api.MyServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt;
    private ProgressBar pb;
    private File sd;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
        iniData();
    }

    private void iniData() {
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            readSD();
        } else {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readSD();
        }
    }

    private void readSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sd = Environment.getExternalStorageDirectory();
        }
    }

    private void initView() {
        bt = (Button) findViewById(R.id.bt);
        pb = (ProgressBar) findViewById(R.id.pb);

        bt.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                ok(sd + "/" + "a.apk");
                break;
        }
    }

    private void ok(final String path) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(MyServer.apkUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream stream = body.byteStream();
                long max = body.contentLength();
                saveFile(stream, path, max);
            }
        });
    }

    private void saveFile(InputStream stream, String path, long max) {

        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            int con = 0;
            int length = -1;
            byte[] bytes = new byte[1024 * 1024 * 10];
            while ((length = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, length);
                con += length;
                pb.setProgress(con);
                pb.setMax((int) max);

                Double a = (double) con / (double) max;
                final int v = (int) (a * 100);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      tv.setText(v+"%");
                  }
              });
            }
            fos.close();
            stream.close();
        } catch (FileNotFoundException e) {


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

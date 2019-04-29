package com.example.dell_pc.androidpractice.api;

import com.example.dell_pc.androidpractice.bean.User;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyServer {
    String recUrl = "http://api.tianapi.com/";
    String upFile = "http://yun918.cn/study/public/file_upload.php";
    String apkUrl = "http://cdn.banmi.com/banmiapp/apk/banmi_330.apk";

    @GET("wxnew?key=52b7ec3471ac3bec6846577e79f20e4c&num=20&page=10")
    Observable<User> getData();


}

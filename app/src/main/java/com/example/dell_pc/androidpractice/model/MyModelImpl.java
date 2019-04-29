package com.example.dell_pc.androidpractice.model;

import com.example.dell_pc.androidpractice.api.MyServer;
import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.bean.User;
import com.example.dell_pc.androidpractice.callback.CallBack;
import com.example.dell_pc.androidpractice.dbutils.DbUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyModelImpl implements MyModel {
    @Override
    public void getData(final CallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MyServer.recUrl)
                .build();
        MyServer server = retrofit.create(MyServer.class);
        Observable<User> data = server.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        if (user!=null){
                            callBack.onSuess(user);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void insertMod(Students students,CallBack callBack) {
        boolean c = DbUtils.getDbUtils().insert(students);
        if (c){
            callBack.insertCall("添加成功");
        }
    }
}

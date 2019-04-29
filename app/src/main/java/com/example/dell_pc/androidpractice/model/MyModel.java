package com.example.dell_pc.androidpractice.model;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.callback.CallBack;

public interface MyModel {
    void getData(CallBack callBack);

    void insertMod(Students students,CallBack callBack);
}

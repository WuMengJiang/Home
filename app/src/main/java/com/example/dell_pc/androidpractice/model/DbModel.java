package com.example.dell_pc.androidpractice.model;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.callback.DbCallBack;

public interface DbModel {
    void queryMod(DbCallBack dbCallBack);
    void deleteMod(Students students,DbCallBack dbCallBack);
}

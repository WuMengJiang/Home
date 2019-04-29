package com.example.dell_pc.androidpractice.callback;

import com.example.dell_pc.androidpractice.bean.Students;

import java.util.ArrayList;

public interface DbCallBack {
    void qureyCall(ArrayList<Students> students);

    void deleteCall(String s);
}

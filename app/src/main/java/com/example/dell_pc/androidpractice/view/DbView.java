package com.example.dell_pc.androidpractice.view;

import com.example.dell_pc.androidpractice.bean.Students;

import java.util.List;

public interface DbView {
    void qureyCall(List<Students> students);

    void deleteCall(String s);
}

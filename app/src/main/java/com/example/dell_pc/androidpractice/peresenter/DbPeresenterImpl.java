package com.example.dell_pc.androidpractice.peresenter;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.callback.DbCallBack;
import com.example.dell_pc.androidpractice.model.DbModel;
import com.example.dell_pc.androidpractice.view.DbView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbPeresenterImpl implements DbPeresenter, DbCallBack {
    private DbModel dbModel;
    private DbView dbView;

    public DbPeresenterImpl(DbModel dbModel, DbView dbView) {
        this.dbModel = dbModel;
        this.dbView = dbView;
    }

    @Override
    public void queryP() {
        dbModel.queryMod(this);
    }

    @Override
    public void deleteP(Students students) {
        dbModel.deleteMod(students,this);
    }

    @Override
    public void qureyCall(ArrayList<Students> students) {
        dbView.qureyCall(students);
    }

    @Override
    public void deleteCall(String s) {
        dbView.deleteCall(s);
    }
}

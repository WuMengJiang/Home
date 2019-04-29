package com.example.dell_pc.androidpractice.model;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.callback.DbCallBack;
import com.example.dell_pc.androidpractice.dbutils.DbUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbModelImpl implements DbModel {
    @Override
    public void queryMod(final DbCallBack dbCallBack) {
      new Thread(){
          @Override
          public void run() {
              super.run();
              ArrayList<Students> qurey = DbUtils.getDbUtils().qurey();
              dbCallBack.qureyCall(qurey);
          }
      }.start();
    }

    @Override
    public void deleteMod(Students students, DbCallBack dbCallBack) {
        boolean b = DbUtils.getDbUtils().delete(students);
        if (b){
            dbCallBack.deleteCall("删除成功");
        }
    }
}

package com.example.dell_pc.androidpractice.peresenter;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.bean.User;
import com.example.dell_pc.androidpractice.callback.CallBack;
import com.example.dell_pc.androidpractice.model.MyModel;
import com.example.dell_pc.androidpractice.view.MyView;

public class MyPeresenterImpl implements MyPeresenter, CallBack {
    private MyModel myModel;
    private MyView myView;

    public MyPeresenterImpl(MyModel myModel, MyView myView) {
        this.myModel = myModel;
        this.myView = myView;
    }

    @Override
    public void getData() {
        if (myModel!=null){
          myModel.getData(this);
        }
    }

    @Override
    public void insertData(Students students) {
        myModel.insertMod(students,this);
    }

    @Override
    public void onSuess(User user) {
        myView.onSuess(user);
    }

    @Override
    public void insertCall(String  sss) {
        myView.insertCall(sss);
    }
}

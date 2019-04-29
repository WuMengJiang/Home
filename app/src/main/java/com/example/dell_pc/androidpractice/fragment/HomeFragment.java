package com.example.dell_pc.androidpractice.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell_pc.androidpractice.R;
import com.example.dell_pc.androidpractice.adapter.RecAdapter;
import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.bean.User;
import com.example.dell_pc.androidpractice.model.MyModelImpl;
import com.example.dell_pc.androidpractice.peresenter.MyPeresenterImpl;
import com.example.dell_pc.androidpractice.view.MyView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MyView, RecAdapter.OnClick {


    private RecyclerView rlv;
    private ArrayList<User.NewslistBean> beans;
    private RecAdapter adapter;
    private MyPeresenterImpl peresenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        iniData();
        initView(inflate);
        return inflate;
    }

    private void iniData() {
        peresenter = new MyPeresenterImpl(new MyModelImpl(), this);
        peresenter.getData();
    }

    private void initView(View inflate) {
        rlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayout.VERTICAL));

        beans = new ArrayList<>();
        adapter = new RecAdapter(beans, getContext());
        rlv.setAdapter(adapter);
        adapter.setOnClick(this);

    }

    @Override
    public void onSuess(User user) {
        beans.addAll(user.getNewslist());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void insertCall(String sss) {
        Toast.makeText(getContext(),sss,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(int position, User.NewslistBean user) {
        Students stu =new Students();
        stu.setId(Long.valueOf(position));
        stu.setTitle(user.getTitle());
        stu.setPicUrl(user.getPicUrl());
        peresenter.insertData(stu);
    }
}


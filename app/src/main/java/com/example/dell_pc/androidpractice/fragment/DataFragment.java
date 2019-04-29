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
import com.example.dell_pc.androidpractice.adapter.RecAdapterB;
import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.model.DbModelImpl;
import com.example.dell_pc.androidpractice.peresenter.DbPeresenterImpl;
import com.example.dell_pc.androidpractice.view.DbView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment implements DbView, RecAdapterB.OnClick {


    private RecyclerView rlv2;
    private DbPeresenterImpl peresenter;
    private ArrayList<Students> beans = new ArrayList<>();
    private RecAdapterB adapterB;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            iniData();
        }else {
            beans.clear();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_data, container, false);
        initView(inflate);

        return inflate;
    }

    private void iniData() {
        peresenter = new DbPeresenterImpl(new DbModelImpl(), this);
        peresenter.queryP();
    }

    private void initView(View inflate) {
        rlv2 = (RecyclerView) inflate.findViewById(R.id.rlv2);
        rlv2.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv2.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayout.VERTICAL));

        beans = new ArrayList<>();
        adapterB = new RecAdapterB(getContext());
        rlv2.setAdapter(adapterB);
        adapterB.setOnClick(this);
    }

    @Override
    public void qureyCall(final List<Students> students) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               beans.addAll(students);
               adapterB.setBeans(beans);
           }
       });
    }

    @Override
    public void deleteCall(String s) {
        beans.remove(position);
        adapterB.notifyDataSetChanged();
        Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
    }
    private int position;
    private Students user;
    @Override
    public void onItemClick(int position, Students user) {
        this.position=position;
        this.user=user;
        peresenter.deleteP(user);
    }
}

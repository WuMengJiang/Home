package com.example.homework1.framgment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.homework1.R;
import com.example.homework1.adapter.HomeRecAdapter;

import java.util.ArrayList;


public class Home extends Fragment implements View.OnClickListener {
    private RecyclerView rlv;
    private ArrayList<String> list;
    private HomeRecAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button bt;

    public Home() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        rlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        layoutManager = new LinearLayoutManager(getContext());
        rlv.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("郭德纲砸缸");
        }
        adapter = new HomeRecAdapter(list, getContext());
        rlv.setAdapter(adapter);

        bt = (Button) inflate.findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }

    public void setManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        rlv.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                if (abc!=null){
                    abc.Veluas("潘长江喜德刚");
                }


                break;
        }
    }
    private Abc abc ;

    public void setAbc(Abc abc) {
        this.abc = abc;
    }
    //武猛将
    public interface Abc{
        void Veluas(String s);
    }
}

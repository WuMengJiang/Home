package com.example.homework1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.homework1.adapter.MyPageAdapter;
import com.example.homework1.framgment.Home;
import com.example.homework1.framgment.MyFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Home.Abc {
//武猛将
    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> list;
    private MyFragment my;
    private MyPageAdapter adapter;
    private Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);


        my = new MyFragment();
        home = new Home();
        Bundle bundle = new Bundle();
        bundle.putString("abc","abc");
        home.setArguments(bundle);
        home.setAbc(this);

        list = new ArrayList<>();
        list.add(home);
        list.add(my);
        adapter = new MyPageAdapter(getSupportFragmentManager(), list);
        vp.setAdapter(adapter);

        iniTabVp();


    }

    private void iniTabVp() {
        tab.addTab(tab.newTab().setText("首页").setIcon(R.drawable.home));
        tab.addTab(tab.newTab().setText("我的").setIcon(R.drawable.my));
        //tab.setupWithViewPager(vp);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opcation_item,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.op1:
            tab.getTabAt(0).select();
            home.setManager(new LinearLayoutManager(this));
                break;
            case R.id.op2:
            tab.getTabAt(0).select();
            home.setManager(new GridLayoutManager(this,2));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Veluas(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }
}

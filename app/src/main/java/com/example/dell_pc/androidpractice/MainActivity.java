package com.example.dell_pc.androidpractice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.example.dell_pc.androidpractice.adapter.FragmentAdatper;
import com.example.dell_pc.androidpractice.boradcast.MyReceiver;
import com.example.dell_pc.androidpractice.fragment.DataFragment;
import com.example.dell_pc.androidpractice.fragment.HomeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar tool;
    private TabLayout tab;
    private ViewPager vp;
    private NavigationView nv;
    private DrawerLayout dl;
    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);



        initView();
    }

    private void initView() {
        tool = (Toolbar) findViewById(R.id.tool);
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);
        setSupportActionBar(tool);
        nv.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, tool, R.string.app_name, R.string.app_name);
        dl.addDrawerListener(toggle);
        toggle.syncState();


        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new DataFragment());
        FragmentAdatper adatper = new FragmentAdatper(getSupportFragmentManager(), list);
        vp.setAdapter(adatper);
        tab.addTab(tab.newTab().setText("主页").setIcon(R.drawable.tab1));
        tab.addTab(tab.newTab().setText("数据").setIcon(R.drawable.tab2));
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

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nv1:
                        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_item, null);
                        PopupWindow window = new PopupWindow(inflate,200,300);
                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alph_item);
                        animation.setDuration(3000);
                        inflate.setAnimation(animation);
                        window.setBackgroundDrawable(new ColorDrawable());
                        window.setOutsideTouchable(true);
                        window.showAtLocation(inflate, Gravity.RIGHT, 0, 0);
                        break;
                    case R.id.nv2:
                        Intent intent = new Intent();
                        intent.setAction(ConnectivityManager.EXTRA_EXTRA_INFO);
                        sendBroadcast(intent);
                        break;
                    case R.id.nv3:
                       CallPhone();

                        break;
                }
                return true;
            }
        });
    }

    private void CallPhone() {
        Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
        startActivity(in);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyReceiver receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.EXTRA_EXTRA_INFO);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyReceiver receiver = new MyReceiver();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.op_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.op1:
               NotiFiy();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void NotiFiy() {
        Intent intent = new Intent(MainActivity.this,UpFileActivity.class);
        PendingIntent pending = PendingIntent.getActivity(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager service = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("1", "渠道名字", NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.enableLights(true);
            service.createNotificationChannel(mChannel);
        }
        notification = new NotificationCompat.Builder(MainActivity.this, "1")
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("上传文件")
                .setContentTitle("文件")
                .build();

        service.notify(1,notification);
    }
}

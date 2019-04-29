package com.example.dell_pc.androidpractice.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dell_pc.androidpractice.DownloadActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, DownloadActivity.class);
        intent1.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}

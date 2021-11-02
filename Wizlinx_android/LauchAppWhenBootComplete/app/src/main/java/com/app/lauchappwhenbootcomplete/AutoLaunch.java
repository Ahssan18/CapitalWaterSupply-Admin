package com.app.lauchappwhenbootcomplete;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoLaunch extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((intent.getAction() != null) && (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")))
        {
           Intent i=new Intent(context,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("pass","app lauch when boot complete");
            context.startActivity(i);
        }
    }
}

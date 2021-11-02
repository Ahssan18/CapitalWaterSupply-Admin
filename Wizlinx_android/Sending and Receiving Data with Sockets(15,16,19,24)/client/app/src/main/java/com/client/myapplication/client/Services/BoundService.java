package com.client.myapplication.client.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.client.myapplication.client.Activities.BindActivityPrac;
import com.client.myapplication.client.Models.Details;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BoundService extends Service implements BindActivityPrac.Communication {
   final IBinder localBinder=new MyBinder();
   EventBus eventBus=EventBus.getDefault();;
   BoundService boundService;
    public BoundService() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN , sticky =  true)
    public void receiceMessage(Details details)
    {
        Toast.makeText(this, "service"+details.getEmail()+"_"+details.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
        eventBus.register(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        Toast.makeText(this, "Stoped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void communicate(String dada) {
        Toast.makeText(this, "Data is here"+dada, Toast.LENGTH_SHORT).show();
    }

    public class MyBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;

        }
    }
}

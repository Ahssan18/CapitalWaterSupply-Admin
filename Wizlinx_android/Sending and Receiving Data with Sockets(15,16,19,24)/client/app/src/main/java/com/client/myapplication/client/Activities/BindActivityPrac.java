package com.client.myapplication.client.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.client.myapplication.client.Models.Details;
import com.client.myapplication.client.R;
import com.client.myapplication.client.Services.BoundService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BindActivityPrac extends AppCompatActivity {
    BoundService boundService;
    boolean bindStatus = false;
    Intent intent;
    Communication communication;
    private Button btnTransfer,buttonPost;
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_prac);
        init();
        eventBus.register(this);
        bindService();
        clickListener();
    }

    private void init() {
        buttonPost=findViewById(R.id.btn_post);
        btnTransfer=findViewById(R.id.btn_transfer);
        eventBus=EventBus.getDefault();

    }


    private void bindService() {
        intent = new Intent(getApplicationContext(), BoundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Subscribe(threadMode =  ThreadMode.MAIN,sticky = false)
    public void receiceMessage(Details details)
    {
        Toast.makeText(BindActivityPrac.this, ""+details.getName()+"_"+details.getEmail(), Toast.LENGTH_SHORT).show();
    }

    private void clickListener() {
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communication.communicate("Successfully Connected");
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details details=new Details("Ahssan","abc@gmail.com");
                eventBus.postSticky(details);
            }
        });
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            boundService = binder.getService();
            communication=binder.getService();
            bindStatus = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bindStatus = false;

        }
    };
    public interface Communication{
        void communicate(String dada);
    }

    @Override
    protected void onResume() {
        if (!bindStatus) {
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (bindStatus) {
            unbindService(connection);
            bindStatus = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }
}
package com.client.myapplication.client.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.client.myapplication.client.Services.ForegroundService;
import com.client.myapplication.client.R;

public class ForegroundServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ForegroundService.class);
                intent.putExtra("inputExtra","Test notification");
                startService(intent);
                Toast.makeText(ForegroundServiceActivity.this, ""+ForegroundService.servicestatus, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ForegroundService.class);
                stopService(intent);
            }
        });
        findViewById(R.id.btn_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForegroundServiceActivity.this, ""+ForegroundService.servicestatus, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
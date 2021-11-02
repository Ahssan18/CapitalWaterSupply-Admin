package com.app.thread_handler_runnable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "this is a thread", Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "this is a runnable", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 3000);

            }
        };
        handler.postDelayed(runnable, 3000);
    }
}
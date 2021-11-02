package com.app.lauchappwhenbootcomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String text;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv_message);
        try {
            text=getIntent().getExtras().getString("pass");
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText("App lauch munally");
        }
    }

}
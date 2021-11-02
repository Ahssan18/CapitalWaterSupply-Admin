package com.app.svaetextintextfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etMessage;
    private Button btnSave,btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    private void clickListener() {
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=etMessage.getText().toString();
                if(!txt.isEmpty())
                {
                    File file=new File(MainActivity.this.getFilesDir(),".SaveText");
                    if(!file.exists())
                    {
                        file.mkdir();
                    }
                    File file1=new File(file,"sample");
                    try {
                        FileWriter fileWriter=new FileWriter(file1);
                        fileWriter.append(etMessage.getText().toString());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Write some text here...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        etMessage=findViewById(R.id.enterText);
        btnSave=findViewById(R.id.save);
        btnShow=findViewById(R.id.btn_show);
    }
}
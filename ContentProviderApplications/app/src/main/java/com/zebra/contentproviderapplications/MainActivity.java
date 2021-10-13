package com.zebra.contentproviderapplications;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.contentproviderapplications.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clickListener();
    }

    private void clickListener() {
        binding.btnSave.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String name = binding.etName.getText().toString();
                String age = binding.etAge.getText().toString();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("age", age);
                Uri uri = getContentResolver().insert(Provider.CONTENT_URI, values);
                Toast.makeText(this, "" + uri, Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
package com.app.eventbusservicetoactivity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.eventbusservicetoactivity.Fragments.AboutFragment;
import com.app.eventbusservicetoactivity.Fragments.ContactUsFragment;
import com.app.eventbusservicetoactivity.Fragments.HomeFragment;
import com.app.eventbusservicetoactivity.Services.MyService;
import com.app.eventbusservicetoactivity.R;
import com.app.eventbusservicetoactivity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(MainActivity.this, MyService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        fragmentTransaction(new HomeFragment());
        setListeners();
    }

    private void fragmentTransaction(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linear_host_frag, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setListeners() {
        binding.linearHome.setOnClickListener(this::onClick);
        binding.linearAbout.setOnClickListener(this::onClick);
        binding.linearContact.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_home:
                fragmentTransaction(new HomeFragment());
                break;
            case R.id.linear_about:
                fragmentTransaction(new AboutFragment());
                break;
            case R.id.linear_contact:
                fragmentTransaction(new ContactUsFragment());
                break;
        }
    }
}
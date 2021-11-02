package com.zebra.diabledarkmode;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.zebra.MainActivityViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainActivityViewModel mainActivityViewModel;
    @Inject
    Piston piston;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        DaggerMainActivityInjector.create().fieldInjection(this);
//        mainActivityViewModel=DaggerMainActivityInjector.create().getMainViewModel();
        mainActivityViewModel.testDrive();

    }
}
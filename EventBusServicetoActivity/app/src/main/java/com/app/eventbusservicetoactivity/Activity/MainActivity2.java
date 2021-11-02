package com.app.eventbusservicetoactivity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.eventbusservicetoactivity.R;
import com.app.eventbusservicetoactivity.databinding.ActivityMain2Binding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    private List<String> list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list2=new ArrayList<>();
        clickListener();

    }

    private void clickListener() {
        binding.listView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(MainActivity2.this, ""+list2.get(position), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void getData(List<String> list)
    {
        setDataonListView(list);
    }

    private void setDataonListView(List<String> list) {
        list2=list;
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,list);
        binding.listView.setAdapter(arrayAdapter);
    }
}
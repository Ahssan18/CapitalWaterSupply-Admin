package com.app.eventbusservicetoactivity.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.eventbusservicetoactivity.Activity.MainActivity2;
import com.app.eventbusservicetoactivity.Services.BackgroundService;
import com.app.eventbusservicetoactivity.databinding.FragmentAboutBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {


    FragmentAboutBinding binding;
    private List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentAboutBinding.inflate(inflater, container, false);
       list=new ArrayList<>();
       clickListener();
       return binding.getRoot();
    }

    private void clickListener() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(binding.eventName.getText().toString());
                binding.eventName.setText("");
                EventBus.getDefault().postSticky(list);
            }
        });
        binding.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
        binding.btnBackgroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >28){
                    getActivity().startForegroundService(new Intent(getActivity(), BackgroundService.class));
                }else{
                    Intent intent=(new Intent(getActivity(), BackgroundService.class));
                    getActivity().startService(intent);
                }
            }
        });
    }
}
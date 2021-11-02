package com.app.eventbusservicetoactivity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.eventbusservicetoactivity.Model.ContactModel;
import com.app.eventbusservicetoactivity.Model.EventModel;
import com.app.eventbusservicetoactivity.R;
import com.app.eventbusservicetoactivity.databinding.FragmentContactUsBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ContactUsFragment extends Fragment {


    FragmentContactUsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding= FragmentContactUsBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void OnReceive(ContactModel eventModel)
    {
        binding.tvMessage.setText(eventModel.getId());
        binding.tvMessage.append("\n"+eventModel.getName()+"\n"+eventModel.getMessage());
    }
}
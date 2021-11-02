package com.app.eventbusservicetoactivity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.eventbusservicetoactivity.Model.ContactModel;
import com.app.eventbusservicetoactivity.Model.EventModel;
import com.app.eventbusservicetoactivity.R;
import com.app.eventbusservicetoactivity.databinding.FragmentHomeBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false);
        clickListener();
        return binding.getRoot();
    }

    private void clickListener() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel contactModel=new ContactModel();
                contactModel.setId(binding.eventId.getText().toString());
                contactModel.setName(binding.eventName.getText().toString());
                contactModel.setMessage(binding.eventDescription.getText().toString());
                EventBus.getDefault().postSticky(contactModel);
            }
        });
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel event) {
        binding.textview.append(event.getEventid()+"\n"+event.getEventname()+"\n"+event.getEventmessage());

    }
}
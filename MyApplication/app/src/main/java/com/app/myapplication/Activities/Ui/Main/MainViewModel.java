package com.app.myapplication.Activities.Ui.Main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.myapplication.Models.Responce;

public class MainViewModel extends ViewModel {
    private NewsRepository newsRepository;
    private MutableLiveData<Responce> mutableLiveData;

    public void init()
    {
        if(mutableLiveData!=null)
        {
            return;
        }
        mutableLiveData=NewsRepository.getInstance().getNews("google-news","98db86cec3eb4c22afa8d062b1dd2101");
    }
    public MutableLiveData<Responce>getNewDataRepository()
    {
        return mutableLiveData;
    }
}

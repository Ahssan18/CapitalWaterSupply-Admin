package com.app.myapplication.Activities.Ui.Main;

import androidx.lifecycle.MutableLiveData;

import com.app.myapplication.Models.Responce;
import com.app.myapplication.Networking.NewApi;
import com.app.myapplication.Networking.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    public static NewsRepository newsRepository;
    private NewApi newApi;
    public NewsRepository() {
       newApi= RetrofitClient.cteateService(NewApi.class);
    }

    public static NewsRepository getInstance()
    {
        if(newsRepository==null)
        {
            newsRepository=new NewsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<Responce> getNews(String source,String key)
    {
        MutableLiveData<Responce> newsData=new MutableLiveData<>();
        newApi.getNews(source,key).enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if(response.isSuccessful())
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
}

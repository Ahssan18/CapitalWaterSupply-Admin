package com.app.retrofitsampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.app.retrofitsampleproject.Models.Data;
import com.app.retrofitsampleproject.Models.Responce;
import com.app.retrofitsampleproject.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private AdapterRecycle adapterRecycle;
    private List<Data> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        NetworkService.getInstance().getServices().getData().enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if(response!=null)
                {
                   if(response.isSuccessful())
                   {
                       list.addAll(response.body().getData());
                       adapterRecycle.notifyDataSetChanged();
                   }
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Exception", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void init() {
        list=new ArrayList<>();
        adapterRecycle=new AdapterRecycle(this,list);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleview.setAdapter(adapterRecycle);
        adapterRecycle.notifyDataSetChanged();
    }
}
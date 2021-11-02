package com.app.myapplication.Activities.Ui.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.os.Bundle;

import com.app.myapplication.Adapters.NewsAdapter;
import com.app.myapplication.Models.Article;
import com.app.myapplication.Models.Responce;
import com.app.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    ActivityMainBinding binding;
    private List<Article>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list=new ArrayList<>();
       mainViewModel=ViewModelProviders.of(MainActivity.this).get(MainViewModel.class);
       mainViewModel.init();
       mainViewModel.getNewDataRepository().observe(this, new Observer<Responce>() {
           @Override
           public void onChanged(Responce responce) {
               list.addAll(responce.getArticles());
               setRecycle(list);
           }
       });
    }
    private void setRecycle(List<Article> modelUsers) {
        NewsAdapter adapter=new NewsAdapter(MainActivity.this,modelUsers);
        LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        binding.recycleView.setLayoutManager(layoutManager);
        binding.recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
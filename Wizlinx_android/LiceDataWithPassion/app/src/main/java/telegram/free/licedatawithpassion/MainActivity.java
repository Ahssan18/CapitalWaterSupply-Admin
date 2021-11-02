package telegram.free.licedatawithpassion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import telegram.free.licedatawithpassion.ModelClasses.Posts;
import telegram.free.licedatawithpassion.Networking.PostsViewModel;
import telegram.free.licedatawithpassion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    PostsViewModel postsViewModel;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getDataFromApi();

    }

    private void getDataFromApi() {
        progressDialog.setTitle("Loading");
        progressDialog.show();
        postsViewModel.getPosts().observe(this, posts -> setAdapter(posts));
    }

    private void setAdapter(List<Posts> posts) {
       progressDialog.dismiss();
        Adapter adapter=new telegram.free.licedatawithpassion.Adapter(posts,MainActivity.this);
        binding.postRecycle.setLayoutManager(new LinearLayoutManager(this));
        binding.postRecycle.setAdapter(adapter);

    }

    private void init() {
        progressDialog=new ProgressDialog(this);
        postsViewModel= ViewModelProviders.of(this).get(PostsViewModel.class);
    }
}
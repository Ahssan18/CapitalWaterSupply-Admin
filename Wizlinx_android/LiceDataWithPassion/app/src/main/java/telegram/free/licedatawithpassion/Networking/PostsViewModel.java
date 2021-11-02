package telegram.free.licedatawithpassion.Networking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import telegram.free.licedatawithpassion.ModelClasses.Posts;
import telegram.free.licedatawithpassion.Repository.PostsRepositiory;

public class PostsViewModel extends AndroidViewModel {
    PostsRepositiory postsRepositiory;
    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsRepositiory=new PostsRepositiory(application);
    }

   public LiveData<List<Posts>> getPosts()
   {
       return postsRepositiory.getMutableLiveData();
   }
}

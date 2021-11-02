package telegram.free.licedatawithpassion.Repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import telegram.free.licedatawithpassion.ModelClasses.Posts;
import telegram.free.licedatawithpassion.Networking.RetrofitInstance;

public class PostsRepositiory {
    public List<Posts> postsList = new ArrayList<>();
    private MutableLiveData<List<Posts>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public PostsRepositiory(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Posts>> getMutableLiveData() {
        RetrofitInstance.getApiService().getPOstsData().enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (response.isSuccessful()) {

                    postsList.addAll(response.body());
                    mutableLiveData.setValue(postsList);
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                t.printStackTrace();

            }
        });
        return mutableLiveData;
    }
}

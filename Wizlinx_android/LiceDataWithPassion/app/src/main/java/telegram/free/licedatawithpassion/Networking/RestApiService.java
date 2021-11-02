package telegram.free.licedatawithpassion.Networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import telegram.free.licedatawithpassion.ModelClasses.Posts;

public interface RestApiService {
    @GET("posts")
    Call<List<Posts>> getPOstsData();
}

package telegram.free.pixabaypagingproject.Networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import telegram.free.pixabaypagingproject.Profile.Models.Response;

public interface ApiServices {
    @Headers("app-id:600fe77ef961a6946a5700c7")
    @GET("api/user")
    Call<Response> getData(@Query("limit") Integer limit,
                           @Query("page") Integer page
                          );

}

package telegram.free.pixabaypagingproject.Profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import telegram.free.pixabaypagingproject.Networking.RetrofitClient;
import telegram.free.pixabaypagingproject.Profile.Models.Datum;
import telegram.free.pixabaypagingproject.Profile.Models.Response;

public class DataResource extends PageKeyedDataSource<Integer, Datum> {
    public Integer page = 1;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Datum> callback) {
        Log.e("check","loadInitial");
        RetrofitClient.getInstance().getApi().getData(10,page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.e("error","onResponse");

                if(response.isSuccessful())
                {
                    if(response.body()!=null)
                    {
                        Log.e("error","isSuccessful");
                        callback.onResult(response.body().getData(),null, page +1);

                    }else
                    {
                       Log.e("error","null in response");
                    }
                }else
                {
                    Log.e("error","NotSuccessful");

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("error","onFailure");

                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Datum> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Datum> callback) {
        Log.e("check","loadAfter"+params.key);

        RetrofitClient.getInstance().getApi().getData(10,params.key).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                callback.onResult(response.body().getData(),params.key+1);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}

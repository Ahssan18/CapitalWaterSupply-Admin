package telegram.free.forseesolution;

import com.google.android.gms.common.api.ApiException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String BASE_URL = "https://shoppingtrendonline.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

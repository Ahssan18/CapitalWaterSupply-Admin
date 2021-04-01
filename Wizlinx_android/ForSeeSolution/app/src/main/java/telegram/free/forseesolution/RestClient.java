package telegram.free.forseesolution;

import retrofit2.Retrofit;

public class RestClient {
    public static Retrofit retrofit;

    GitHubService service = retrofit.create(GitHubService.class);
    public static Retrofit getInstance()
    {
        if(retrofit!=null)
        {
            return retrofit;
        }else
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://shoppingtrendonline.com/api/")
                    .build();
        }
        return retrofit;

    }

    private RestClient(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}

package telegram.free.forseesolution;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @Multipart
    @POST("api/dummy.php")
    Call<UploadResult> uploadPic(
            @Part MultipartBody.Part file
    );
}

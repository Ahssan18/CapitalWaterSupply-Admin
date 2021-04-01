package telegram.free.forseesolution;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GitHubService {
    @Multipart
    @POST("/api/receipt/upload")
    Call<UploadResult> uploadReceipt(
            @Part MultipartBody.Part file
    );
}

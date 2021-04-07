package telegram.free.forseesolution;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorker extends Worker {
    public static final String TASK_DESC = "file";
    Context ref;
    ProgressDialog progressDialog;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.ref = context;
        progressDialog = new ProgressDialog(context);
    }


    @NonNull
    @Override
    public Result doWork() {
        String path = getInputData().getString(TASK_DESC);
        displayNotification("My Worker", "Hey I finished my work");
        uploadFile(path);
        return Result.success();
    }

    private void uploadFile(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/jpg"),
                        file
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        progressDialog.show();
        progressDialog.setMessage("Wait...");

        RestClient.getApiService().create(ApiServices.class).uploadPic(body).enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Data data = new Data.Builder()
                            .putString("message", response.message())
                            .build();
                    senDataBack(response.body().getMsg());
                    displayNotification("Success", "File uploded Succesfully");

                }else
                {
                    senDataBack("error in res");

                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable t) {
                progressDialog.dismiss();
                senDataBack(t.getMessage());
                displayNotification("Filed", "File uploded Failed");

                t.printStackTrace();
            }
        });
    }

    private void senDataBack(String data) {
//        EventBus.getDefault().postSticky(data);
        Intent i = new Intent();
        i.setAction("com.message.forward");
        i.putExtra("data", data);
        ref.sendBroadcast(i);
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }
}

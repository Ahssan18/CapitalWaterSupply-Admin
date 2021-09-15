package telegram.free.forseesolution.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import telegram.free.forseesolution.MyWorker;
import telegram.free.forseesolution.R;
import telegram.free.forseesolution.ResponceInter;
import telegram.free.forseesolution.UploadResult;

public class PicUploadActivity extends AppCompatActivity implements ResponceInter {
    private static final String TAG ="PicUploadActivity" ;
    OneTimeWorkRequest workRequest;
    private TextView textView;
    private ImageView ivCancel, ivPicker, picView;
    String picturePath;
    ProgressDialog progressDialog;
    private Messagereceviver receiver;
    IntentFilter intentFilter;
    public class Messagereceviver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String data=intent.getStringExtra("data");
            textView.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_activty4);
        init();
        clicKlistener();
        if(workRequest!=null){
            WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(this, workInfo -> {
                        Data data1 = workInfo.getOutputData();
                        String resp = data1.getString("responce");
                        textView.setText(resp);
                    });
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        textView.setText(result);
    }

    private void clicKlistener() {
        ivCancel.setOnClickListener(v -> {
            WorkManager.getInstance().cancelWorkById(workRequest.getId());

        });
        ivPicker.setOnClickListener(v -> {
            Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    selectImage(PicUploadActivity.this);

                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private void init()  {
        String u = "https://app.clickup.com/t/k15ea6";
        URL url = null;
        try {
            url = new URL(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String host = url.getHost();
        String regex = "^((?!-)[A-Za-z0-9-]"
                + "{1,63}(?<!-)\\.)"
                + "+[A-Za-z]{2,6}";
        Pattern p
                = Pattern.compile(regex);
        Matcher m = p.matcher(host);
        Log.d("host_url_",host+"_"+url.toString().replace(host,"google.com/")+"_"+m.matches());

        receiver=new Messagereceviver();
        progressDialog=new ProgressDialog(this);
        picView = findViewById(R.id.imageView);
        ivPicker = findViewById(R.id.imageView2);
        ivCancel = findViewById(R.id.imageView3);
        textView = findViewById(R.id.textView);
    }

    public void upLoad(View view) {
        WorkManager.getInstance().enqueue(workRequest);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /*onActivityResult(*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        picView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                setInput(picturePath);
                                picView.setImageURI(selectedImage);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void setInput(String picturePath) {
        Data data = new Data.Builder()
                .putString(MyWorker.TASK_DESC, picturePath)
                .build();
        workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
    }

    @Override
    public void getResponce(UploadResult result) {
        textView.setText(result.getMsg());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.message.forward");
        registerReceiver(receiver,intentFilter);
    }
}
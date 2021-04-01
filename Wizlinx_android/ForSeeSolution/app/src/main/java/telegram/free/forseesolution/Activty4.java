package telegram.free.forseesolution;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.io.File;

public class Activty4 extends AppCompatActivity {
    OneTimeWorkRequest workRequest;
    private TextView textView;
    private ImageView ivCancel, ivPicker,picView;
    String picturePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File("");

        setContentView(R.layout.activity_activty4);
        init();
        clicKlistener();


        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, workInfo -> {
                    Data data1 = workInfo.getOutputData();
                    String resp = data1.getString("responce");
                    textView.setText(resp);
                });
    }

    private void clicKlistener() {
        ivCancel.setOnClickListener(v -> {
            WorkManager.getInstance().cancelWorkById(workRequest.getId());

        });
        ivPicker.setOnClickListener(v -> {
            selectImage(this);
        });
    }

    private void init() {
        picView=findViewById(R.id.imageView);
        ivPicker = findViewById(R.id.imageView2);
        ivCancel = findViewById(R.id.imageView3);
        textView = findViewById(R.id.textView);
    }

    public void upLoad(View view) {
        WorkManager.getInstance().enqueue(workRequest);
    }
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);

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
                                picView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
    /**/
}
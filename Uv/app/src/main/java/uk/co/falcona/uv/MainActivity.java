package uk.co.falcona.uv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivPicStatus;
    private TextView tvWarning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initilize();
        setData();

    }

    private void setData() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
               ivPicStatus.setImageResource(R.drawable.ic_sun);
               new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       ivPicStatus.setImageResource(R.drawable.ic_ok);
                       tvWarning.setText(R.string.sanized);
                       tvWarning.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
                   }
               },5000);
            }
        },5000);
    }

    private void initilize() {
        ivPicStatus=findViewById(R.id.iv_pic_status);
        tvWarning=findViewById(R.id.tv_varning);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
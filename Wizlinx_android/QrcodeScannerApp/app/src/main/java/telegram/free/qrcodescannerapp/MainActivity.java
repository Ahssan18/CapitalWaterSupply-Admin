package telegram.free.qrcodescannerapp;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import java.io.IOException;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, SurfaceHolder.Callback {
    private static final String TAG = "MainActivity";
    private ZXingScannerView mScannerView;
    private SeekBar seekBar;
    Camera.Parameters params;
    TextView tvZoomLevel;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    private void clickListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("progress_update", progress + "_");

                try {

                    params.setZoom(progress);
//                    currentZoomLevel = progress;
                    params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    camera.setParameters(params);

                    //set zoom level value
                    try {
                        float ratio = ((float) params.getZoomRatios().get(progress)) / 100;
                        Log.d("progress_update", String.format("%.1fX", ratio) + "_");
                        tvZoomLevel.setText(String.format("%.1fX", ratio));
                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void init() {
        tvZoomLevel = findViewById(R.id.tvzom);
        mScannerView = findViewById(R.id.qrcodescanner);
        seekBar = findViewById(R.id.seekbar);
        camera = getCameraInstance();
        params = camera.getParameters();
        seekBar.setMax(params.getMaxZoom() * 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result rawResult) {
                mScannerView.resumeCameraPreview(this::handleResult);
            }
        });
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.e(TAG,"surfaceCreated");
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            camera.release();
            camera = null;
            // TODO: add more exception handling logic here
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG,"surfaceChanged");
        params = camera.getParameters();
        List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();

        Log.e("size_list_preview",previewSizes.size()+"_");
        // You need to choose the most appropriate previewSize for your app
        Camera.Size previewSize = previewSizes.get(0);

        params.setPreviewSize(previewSize.width, previewSize.height);
        camera.setParameters(params);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.e(TAG,"surfaceDestroyed");

    }
}
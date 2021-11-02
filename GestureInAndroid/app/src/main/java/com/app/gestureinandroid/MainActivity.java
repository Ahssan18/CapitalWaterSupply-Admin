package com.app.gestureinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ScaleGestureDetector scaleGestureDetector;
    private float scale=1f;
    private ImageView imageView;
    private Matrix matrix = new Matrix();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        scaleGestureDetector=new ScaleGestureDetector(this, new ScaleGesture());
    }

    private void init() {
        imageView=findViewById(R.id.image_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    class ScaleGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale*=detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            imageView.setImageMatrix(matrix);
            Toast.makeText(MainActivity.this, ""+scale, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
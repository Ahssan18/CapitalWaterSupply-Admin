package com.app.gestureinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.pwittchen.gesture.library.Gesture;
import com.github.pwittchen.gesture.library.GestureListener;

public class GestureThroughLibrary extends AppCompatActivity {
    private Gesture gesture;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_through_library);
        gesture=new Gesture();
        textView=findViewById(R.id.tv_text);
        gesture.addListener(new GestureListener() {
            @Override public void onPress(MotionEvent motionEvent) {
                textView.setText("press");
            }

            @Override public void onTap(MotionEvent motionEvent) {
                textView.setText("tap");
            }

            @Override public void onDrag(MotionEvent motionEvent) {
                textView.setText("drag");
            }

            @Override public void onMove(MotionEvent motionEvent) {
                textView.setText("move");
            }

            @Override public void onRelease(MotionEvent motionEvent) {
                textView.setText("release");
            }

            @Override public void onLongPress(MotionEvent motionEvent) {
                textView.setText("longpress");
            }

            @Override public void onMultiTap(MotionEvent motionEvent, int clicks) {
                textView.setText("multitap [" + clicks + "]");
            }
        });
    }
    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        gesture.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }


}
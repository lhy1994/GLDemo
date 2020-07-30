package com.lhy.learning.gldemo.activity;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.render.PointRenderer;
import com.lhy.learning.gldemo.view.FingerPaintView;
import com.lhy.learning.gldemo.view.ImageGLSurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class FingerPaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_paint);
        FingerPaintView fingerPaintView=findViewById(R.id.surface);
        fingerPaintView.enable();
    }
}
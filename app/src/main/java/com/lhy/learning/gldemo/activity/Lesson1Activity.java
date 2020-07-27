package com.lhy.learning.gldemo.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.render.ColorRenderer;
import com.lhy.learning.gldemo.render.PointRenderer;
import com.lhy.learning.gldemo.render.ShapeRenderer;
import com.lhy.learning.gldemo.view.ImageGLSurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class Lesson1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);
        final ImageGLSurfaceView surfaceView = findViewById(R.id.surface);
//        surfaceView.setRenderer(new PointRenderer());
//        surfaceView.setRenderer(new ShapeRenderer());
        surfaceView.setRenderer(new ColorRenderer());
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
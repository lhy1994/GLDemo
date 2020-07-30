package com.lhy.learning.gldemo.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.render.BaseRenderer;
import com.lhy.learning.gldemo.render.FilterRenderer;
import com.lhy.learning.gldemo.render.PointRenderer;
import com.lhy.learning.gldemo.render.ShapeRenderer;
import com.lhy.learning.gldemo.view.ImageGLSurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class Lesson1Activity extends AppCompatActivity {
    private BaseRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);
        final ImageGLSurfaceView surfaceView = findViewById(R.id.surface);
//        surfaceView.setRenderer(new PointRenderer(this));
//        surfaceView.setRenderer(new ShapeRenderer(this));
//        surfaceView.setRenderer(new ColorRenderer(this));
//        surfaceView.setRenderer(new IndexRenderer(this));
//        surfaceView.setRenderer(new TexRenderer(this));
//        surfaceView.setRenderer(new MultiTexRenderer(this));
//        surfaceView.setRenderer(new MultiTexRenderer2(this));
//        surfaceView.setRenderer(new FBORenderer(this));
        mRenderer = new FilterRenderer(this);
        surfaceView.setRenderer(mRenderer);
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.requestRender();
                if (mRenderer instanceof FilterRenderer) {
                    ((FilterRenderer) mRenderer).next();
                }
            }
        });
    }
}
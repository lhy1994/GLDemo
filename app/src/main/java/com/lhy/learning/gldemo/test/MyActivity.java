package com.lhy.learning.gldemo.test;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/29
 */

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lhy.learning.gldemo.R;


/**
 * Created by cjz on 2018/8/2.
 */

public class MyActivity extends Activity {

    private FrameLayout ll_container;
    private TextView tv_frame_rate;
    private MyGLRenderer myGlRenderer;
    private Button btn_clear;
    Handler handler = new Handler();
    private MyGLSurfaceView myGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Translucent_NoTitleBar);
        //强制横屏：
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_test);
        ll_container = findViewById(R.id.ll_container);
        myGLSurfaceView = new MyGLSurfaceView(this);
        myGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        //设置背景透明:
        myGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        myGLSurfaceView.setZOrderOnTop(true);
        myGlRenderer = new MyGLRenderer(this);
        myGLSurfaceView.setRenderer(myGlRenderer);
//        myGLSurfaceView.setZOrderMediaOverlay(true);
        ll_container.addView(myGLSurfaceView);
        initView();
        loopGetRate();
    }

    /**
     * 利用handler+递归轮询帧率
     **/
    private void loopGetRate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_frame_rate.setText("FPS:" + myGlRenderer.frameCount);
                myGlRenderer.frameCount = 0;
                if (!MyActivity.this.isFinishing()) {
                    loopGetRate();
                }
            }
        }, 1000);
    }

    private void initView() {
        tv_frame_rate = findViewById(R.id.tv_frame_rate); //帧率显示
        //清屏：
        btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGlRenderer.clearAll();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (myGLSurfaceView != null) {
            myGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myGLSurfaceView != null) {
            myGLSurfaceView.onResume();
        }
    }
}



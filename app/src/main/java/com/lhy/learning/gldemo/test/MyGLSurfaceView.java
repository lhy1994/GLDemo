package com.lhy.learning.gldemo.test;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/29
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by cjz on 2018/8/2.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.renderer = (MyGLRenderer) renderer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        renderer.setPointer(event);
        return true;
    }
}



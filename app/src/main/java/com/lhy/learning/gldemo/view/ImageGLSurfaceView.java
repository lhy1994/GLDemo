package com.lhy.learning.gldemo.view;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/24
 */
public class ImageGLSurfaceView extends GLSurfaceView {
    public ImageGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public ImageGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setEGLConfigChooser(false);
    }
}

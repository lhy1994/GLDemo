package com.lhy.learning.gldemo.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lhy.learning.gldemo.render.FingerPaintRenderer;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/29
 */
public class FingerPaintView extends GLSurfaceView {
    private FingerPaintRenderer mRenderer;

    public FingerPaintView(Context context) {
        super(context);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setEGLContextClientVersion(2);
        setEGLConfigChooser(false);
    }

    public void enable() {
        if (mRenderer == null) {
            mRenderer = new FingerPaintRenderer(getContext());
        }
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mRenderer.startLine(event.getX(), event.getY());
                requestRender();
                break;
            case MotionEvent.ACTION_MOVE:
                mRenderer.addPoint(event.getX(), event.getY());
                requestRender();
                break;
            default:
                break;
        }
        return true;
    }
}

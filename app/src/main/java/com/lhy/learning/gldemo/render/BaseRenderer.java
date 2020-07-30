package com.lhy.learning.gldemo.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lhy.learning.gldemo.tool.ShaderHelper;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/24
 */
public abstract class BaseRenderer implements GLSurfaceView.Renderer {

    protected int mProgram;

    private Context mContext;
    protected volatile int outputWidth;
    protected volatile int outputHeight;

    public BaseRenderer(Context context) {
        this.mContext = context;
        onInit();
    }

    protected Context getContext() {
        return mContext;
    }

    public abstract void release();

    public abstract void onInit();

    /**
     * 创建OpenGL程序对象
     *
     * @param vertexShader   顶点着色器代码
     * @param fragmentShader 片段着色器代码
     */
    protected void makeProgram(String vertexShader, String fragmentShader) {
        mProgram = ShaderHelper.makeProgram(vertexShader, fragmentShader);
    }

    protected int getUniform(String name) {
        return GLES20.glGetUniformLocation(mProgram, name);
    }

    protected int getAttrib(String name) {
        return GLES20.glGetAttribLocation(mProgram, name);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        outputWidth = width;
        outputHeight = height;
        GLES20.glViewport(0, 0, width, height);
    }

}

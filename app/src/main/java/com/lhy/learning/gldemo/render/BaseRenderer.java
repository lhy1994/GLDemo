package com.lhy.learning.gldemo.render;

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

    public BaseRenderer(){
        onInit();
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
        // 步骤1：编译顶点着色器
        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
        // 步骤2：编译片段着色器
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShader);
        // 步骤3：将顶点着色器、片段着色器进行链接，组装成一个OpenGL程序
        mProgram = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);

        ShaderHelper.validateProgram(mProgram);

        // 步骤4：通知OpenGL开始使用该程序
        GLES20.glUseProgram(mProgram);
    }

    protected int getUniform(String name) {
        return GLES20.glGetUniformLocation(mProgram, name);
    }

    protected int getAttrib(String name) {
        return GLES20.glGetAttribLocation(mProgram, name);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

}

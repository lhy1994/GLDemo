package com.lhy.learning.gldemo.render;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.lhy.learning.gldemo.tool.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class FingerPaintRenderer extends BaseRenderer {

    /**
     * 顶点着色器
     */
    private static final String VERTEX_SHADER = "" +
            // vec4：4个分量的向量：x、y、z、w
            "attribute vec4 a_Position;\n" +
            "void main()\n" +
            "{\n" +
            // gl_Position：GL中默认定义的输出变量，决定了当前顶点的最终位置
            "    gl_Position = a_Position;\n" +
            // gl_PointSize：GL中默认定义的输出变量，决定了当前顶点的大小
            "    gl_PointSize = 40.0;\n" +
            "}";

    /**
     * 片段着色器
     */
    private static final String FRAGMENT_SHADER = "" +
            // 定义所有浮点数据类型的默认精度；有lowp、mediump、highp 三种，但只有部分硬件支持片段着色器使用highp。(顶点着色器默认highp)
            "precision mediump float;\n" +
            "uniform mediump vec4 u_Color;\n" +
            "void main()\n" +
            "{\n" +
            // gl_FragColor：GL中默认定义的输出变量，决定了当前片段的最终颜色
            "    gl_FragColor = u_Color;\n" +
            "}";

    private FloatBuffer mVertexBuffer;
    private int aPositionHandle;
    private int uColorHandle;

    private int mPointCount;

    public FingerPaintRenderer(Context context) {
        super(context);
    }

    @Override
    public void release() {

    }

    @Override
    public void onInit() {
        mVertexBuffer = ByteBuffer
                // 分配顶点坐标分量个数 * Float占的Byte位数
                .allocateDirect(1024 * 2 * ShaderHelper.BYTES_PER_FLOAT)
                // 按照本地字节序排序
                .order(ByteOrder.nativeOrder())
                // Byte类型转Float类型
                .asFloatBuffer();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        makeProgram(VERTEX_SHADER, FRAGMENT_SHADER);

        uColorHandle = getUniform("u_Color");
        aPositionHandle = getAttrib("a_Position");

        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT,
                false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionHandle);

        GLES20.glEnable(GL10.GL_BLEND);
        GLES20.glEnable(GL10.GL_LINE_SMOOTH);
        GLES20.glLineWidth(5f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        outputWidth = width;
        outputHeight = height;
        Log.i("MyRenderer", "onSurfaceChanged: " + outputWidth + " h= " + height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniform4f(uColorHandle, 0.0f, 0.0f, 1.0f, 1.0f);

        mVertexBuffer.position(0);

        float[] ret = new float[2];
        GLES20.glGetFloatv(GLES20.GL_ALIASED_LINE_WIDTH_RANGE, ret, 0);
        Log.i("MyRenderer", "onDrawFrame: " + Arrays.toString(ret));

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mPointCount);
    }

    private int mVertexBufferIndex = 0;
    private volatile float mCurrX = 0f;
    private volatile float mCurrY = 0f;


    public void addPoint(float x, float y) {
        float newX = (x - outputWidth / 2f) / (outputWidth / 2f);
        float newY = -(y - outputHeight / 2f) / (outputHeight / 2f);
        mCurrX = newX;
        mCurrY = newY;
        mVertexBuffer.put(mVertexBufferIndex++, mCurrX);
        mVertexBuffer.put(mVertexBufferIndex++, mCurrY);
        mPointCount++;
    }
}

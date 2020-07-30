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
            "attribute vec4 a_Position;\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = a_Position;\n" +
            "    gl_PointSize = 10.0;\n" +
            "}";

    /**
     * 片段着色器
     */
    private static final String FRAGMENT_SHADER = "" +
            "precision mediump float;\n" +
            "uniform mediump vec4 u_Color;\n" +
            "void main()\n" +
            "{\n" +
            // 默认的点时方块，为了画出圆点，在片段着色器中裁剪
            "    float dist = length(gl_PointCoord - vec2(0.5));\n" +
            "    float value = -smoothstep(0.48, 0.5, dist) + 1.0;\n" +
            "    if (value == 0.0) {\n" +
            "        discard;\n" +
            "    }\n" +
            "    gl_FragColor = vec4(u_Color.r, u_Color.g, u_Color.b, u_Color.a * value);" +
//            "    gl_FragColor = u_Color;\n" +
            "}";

    private FloatBuffer mVertexBuffer;
    private ByteBuffer mBuffer;
    private int aPositionHandle;
    private int uColorHandle;
    private static final int INIT_MAX_VERTEX_COUNT = 64;

    public FingerPaintRenderer(Context context) {
        super(context);
    }

    @Override
    public void release() {

    }

    @Override
    public void onInit() {
        mMaxVertexCount = INIT_MAX_VERTEX_COUNT;
        mBuffer = ByteBuffer
                // 分配顶点坐标分量个数 * Float占的Byte位数
                .allocateDirect(mMaxVertexCount * 2 * ShaderHelper.BYTES_PER_FLOAT)
                // 按照本地字节序排序
                .order(ByteOrder.nativeOrder());

        mVertexBuffer = mBuffer
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
        GLES20.glEnable(GL10.GL_POINT_SMOOTH);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        outputWidth = width;
        outputHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniform4f(uColorHandle, 0.0f, 0.0f, 1.0f, 1.0f);

        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT,
                false, 0, mVertexBuffer);

        GLES20.glEnable(GL10.GL_POINT_SMOOTH);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mVertexBufferIndex / 2);
    }

    private int mVertexBufferIndex = 0;
    private volatile float mLastX = 0f;
    private volatile float mLastY = 0f;
    private int mPixelPerPoint = 2;
    private int mMaxVertexCount = INIT_MAX_VERTEX_COUNT;


    public void addPoint(float x, float y) {
        int count = (int) Math.max(Math.ceil(Math.sqrt(Math.pow(x - mLastX, 2) + Math.pow(y - mLastY, 2))) / mPixelPerPoint, 1);
        for (int i = 0; i < count; i++) {
            if (mVertexBufferIndex / 2 >= mMaxVertexCount) {
                reAllocBuffer();
            }
            float subX = mLastX + (x - mLastX) * (i * 1f / count);
            float subY = mLastY + (y - mLastY) * (i * 1f / count);

            mVertexBuffer.put(mVertexBufferIndex++, convertX(subX));
            mVertexBuffer.put(mVertexBufferIndex++, convertY(subY));
        }

        mLastX = x;
        mLastY = y;
    }

    public void startLine(float x, float y) {
        if (mVertexBufferIndex / 2 >= mMaxVertexCount) {
            reAllocBuffer();
        }
        mVertexBuffer.put(mVertexBufferIndex++, convertX(x));
        mVertexBuffer.put(mVertexBufferIndex++, convertY(y));
        mLastX = x;
        mLastY = y;
    }

    private void reAllocBuffer() {
        mMaxVertexCount = mMaxVertexCount * 2;
        ByteBuffer buffer = ByteBuffer
                // 分配顶点坐标分量个数 * Float占的Byte位数
                .allocateDirect(mMaxVertexCount * 2 * ShaderHelper.BYTES_PER_FLOAT)
                // 按照本地字节序排序
                .order(ByteOrder.nativeOrder());
        System.arraycopy(mBuffer.array(), 0, buffer.array(), 0, mBuffer.array().length);
        mBuffer = buffer;
        mVertexBuffer = buffer.asFloatBuffer();
    }


    private float convertX(float x) {
        return (x - outputWidth / 2f) / (outputWidth / 2f);
    }

    private float convertY(float y) {
        return -(y - outputHeight / 2f) / (outputHeight / 2f);
    }
}

package com.lhy.learning.gldemo.render;

import android.content.Context;
import android.opengl.GLES20;

import com.lhy.learning.gldemo.tool.ShaderHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/25
 */
public class ColorRenderer extends BaseRenderer {

    private static final String VERTEX_SHADER = "" +
            "attribute vec4 a_Position;\n" +
            // a_Color：从外部传递进来的每个顶点的颜色值
            "attribute vec4 a_Color;\n" +
            // v_Color：将每个顶点的颜色值传递给片段着色器
            "varying vec4 v_Color;\n" +
            "void main()\n" +
            "{\n" +
            "    v_Color = a_Color;\n" +
            "    gl_Position = a_Position;\n" +
            "}";

    private static final String FRAGMENT_SHADER = "" +
            "precision mediump float;\n" +
            // v_Color：从顶点着色器传递过来的颜色值
            "varying vec4 v_Color;\n" +
            "void main()\n" +
            "{\n" +
            "    gl_FragColor = v_Color;\n" +
            "}";

    private static final float[] POINT_DATA = {
            -0.5f, -0.5f,
            0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f,
    };

    private static final float[] COLOR_DATA = {
            // 一个顶点有3个向量数据：r、g、b
            1f, 0.5f, 0.5f,
            1f, 0f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
    };
    private int mPosHandler;
    private int mColorHandler;
    private FloatBuffer mVertexData;
    private FloatBuffer mColorData;

    public ColorRenderer(Context context) {
        super(context);
    }

    @Override
    public void onInit() {
        mVertexData = ShaderHelper.createFloatBuffer(POINT_DATA);
        mColorData = ShaderHelper.createFloatBuffer(COLOR_DATA);
    }

    @Override
    public void release() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置刷新屏幕时候使用的颜色值,顺序是RGBA，值的范围从0~1。GLES20.glClear调用时使用该颜色值。
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        makeProgram(VERTEX_SHADER, FRAGMENT_SHADER);

        mPosHandler = getAttrib("a_Position");
        mColorHandler = getAttrib("a_Color");

        mVertexData.position(0);
        GLES20.glVertexAttribPointer(mPosHandler, 2, GLES20.GL_FLOAT,
                false, 0, mVertexData);
        GLES20.glEnableVertexAttribArray(mPosHandler);

        mColorData.position(0);
        GLES20.glVertexAttribPointer(mColorHandler, 3, GLES20.GL_FLOAT, false, 0, mColorData);
        GLES20.glEnableVertexAttribArray(mColorHandler);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, POINT_DATA.length / 2);
    }
}

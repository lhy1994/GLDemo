package com.lhy.learning.gldemo.render;

import android.content.Context;
import android.opengl.GLES20;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.tool.ShaderHelper;
import com.lhy.learning.gldemo.tool.TextureHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TexRenderer extends BaseRenderer {

    private static final String VERTEX_SHADER = "" +
            "attribute vec4 a_Position;\n" +
            // 纹理坐标：2个分量，S和T坐标
            "attribute vec2 a_TexCoord;\n" +
            "varying vec2 v_TexCoord;\n" +
            "void main()\n" +
            "{\n" +
            "    v_TexCoord = a_TexCoord;\n" +
            "    gl_Position = a_Position;\n" +
            "}";
    private static final String FRAGMENT_SHADER = "" +
            "precision mediump float;\n" +
            "varying vec2 v_TexCoord;\n" +
            "uniform sampler2D u_TextureUnit;\n" +
            "void main()\n" +
            "{\n" +
            "    gl_FragColor = texture2D(u_TextureUnit, v_TexCoord);\n" +
            "}";

    /**
     * 顶点坐标
     */
    private static final float[] POINT_DATA = {
            -0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f,
    };

    /**
     * 纹理坐标
     */
    private static final float[] TEX_VERTEX = {
            0, 1,
            0, 0,
            1, 0,
            1, 1,
    };
    private FloatBuffer mVertexData;
    private FloatBuffer mTexVertexData;

    private int aPositionLocation;
    private int aTexCoordLocation;
    private int uTextureUnitLocation;
    private TextureHelper.TextureBean mTextureBean;

    public TexRenderer(Context context) {
        super(context);
    }

    @Override
    public void release() {

    }

    @Override
    public void onInit() {
        mVertexData = ShaderHelper.createFloatBuffer(POINT_DATA);
        mTexVertexData = ShaderHelper.createFloatBuffer(TEX_VERTEX);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        makeProgram(VERTEX_SHADER, FRAGMENT_SHADER);

        mTextureBean = TextureHelper.loadTexture(getContext(), R.drawable.pikachu);
        uTextureUnitLocation = getUniform("u_TextureUnit");

        aTexCoordLocation = getAttrib("a_TexCoord");
        mTexVertexData.position(0);
        GLES20.glVertexAttribPointer(aTexCoordLocation, 2, GLES20.GL_FLOAT, false, 0, mTexVertexData);
        GLES20.glEnableVertexAttribArray(aTexCoordLocation);

        aPositionLocation = getAttrib("a_Position");
        mVertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT, false, 0, mVertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        // 开启纹理透明混合，这样才能绘制透明图片
        GLES20.glEnable(GL10.GL_BLEND);
        GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // 设置当前活动的纹理单元为纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // 将纹理ID绑定到当前活动的纹理单元上
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureBean.getTextureId());
        // 将纹理单元传递片段着色器的u_TextureUnit
        GLES20.glUniform1i(uTextureUnitLocation, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, POINT_DATA.length / 2);
    }


}

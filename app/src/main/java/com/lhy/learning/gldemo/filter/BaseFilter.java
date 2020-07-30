package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.lhy.learning.gldemo.tool.MatrixHelper;
import com.lhy.learning.gldemo.tool.ShaderHelper;
import com.lhy.learning.gldemo.tool.TextureHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class BaseFilter {
    private static final String DEFAULT_VERTEX_SHADER =
            " uniform mat4 u_Matrix;\n" +
                    " attribute vec4 a_Position;\n" +
                    " attribute vec2 a_TexCoord;\n" +
                    " varying vec2 v_TexCoord;\n" +
                    " void main() {\n" +
                    "      v_TexCoord = a_TexCoord;\n" +
                    "      gl_Position = u_Matrix * a_Position;\n" +
                    " }\n";

    private static final String DEFAULT_FRAGMENT_SHADER =
            " precision mediump float;\n" +
                    "varying vec2 v_TexCoord;\n" +
                    "uniform sampler2D u_TextureUnit;\n" +
                    "void main() {\n" +
                    "     gl_FragColor = texture2D(u_TextureUnit, v_TexCoord);\n" +
                    "}\n";

    private static final float[] POINT_DATA = new float[]{
            -1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f};
    private static final float[] TEX_VERTEX = new float[]{
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f};

    private Context mContext;
    private String mVertexShader;
    private String mFragmentShader;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexVertexBuffer;

    private MatrixHelper mMatrixHelper;
    private int mProgram;
    private int uTextureUnitLocation;
    private TextureHelper.TextureBean mTextureBean;

    public BaseFilter(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mVertexShader = buildVertexShader();
        mFragmentShader = buildFragmentShader();
        mVertexBuffer = ShaderHelper.createFloatBuffer(POINT_DATA);
        mTexVertexBuffer = ShaderHelper.createFloatBuffer(TEX_VERTEX);
    }

    protected String buildVertexShader() {
        return DEFAULT_VERTEX_SHADER;
    }

    protected String buildFragmentShader() {
        return DEFAULT_FRAGMENT_SHADER;
    }

    public void onCreate() {
        mProgram = ShaderHelper.makeProgram(mVertexShader, mFragmentShader);
        int aPositionLocation = getAttrib("a_Position");
        mMatrixHelper = new MatrixHelper(mProgram, "u_Matrix");
        // 纹理坐标索引
        int aTexCoordLocation = getAttrib("a_TexCoord");
        uTextureUnitLocation = getUniform("u_TextureUnit");

        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, 2,
                GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        // 加载纹理坐标
        mTexVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(aTexCoordLocation, 2, GLES20.GL_FLOAT, false, 0, mTexVertexBuffer);
        GLES20.glEnableVertexAttribArray(aTexCoordLocation);

        GLES20.glClearColor(0f, 0f, 0f, 1f);
        // 开启纹理透明混合，这样才能绘制透明图片
        GLES20.glEnable(GL10.GL_BLEND);
        GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void onSizeChanged(int width, int height) {
        mMatrixHelper.enable(width, height);
    }

    public void onDraw() {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 纹理单元：在OpenGL中，纹理不是直接绘制到片段着色器上，而是通过纹理单元去保存纹理

        // 设置当前活动的纹理单元为纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // 将纹理ID绑定到当前活动的纹理单元上
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureBean.getTextureId());

        // 将纹理单元传递片段着色器的u_TextureUnit
        GLES20.glUniform1i(uTextureUnitLocation, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, POINT_DATA.length / 2);
    }

    public void onDestroy() {
        GLES20.glDeleteProgram(mProgram);
        mProgram = 0;
    }

    protected int getUniform(String name) {
        return GLES20.glGetUniformLocation(mProgram, name);
    }

    protected int getAttrib(String name) {
        return GLES20.glGetAttribLocation(mProgram, name);
    }

    public void setTextureBean(TextureHelper.TextureBean textureBean) {
        this.mTextureBean = textureBean;
    }
}

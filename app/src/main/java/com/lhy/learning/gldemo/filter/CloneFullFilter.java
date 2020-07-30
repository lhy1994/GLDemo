package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class CloneFullFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "varying vec2 v_TexCoord;\n" +
                    "uniform sampler2D u_TextureUnit;\n" +
                    "uniform float cloneCount;\n" +
                    "void main() {\n" +
                    "      gl_FragColor = texture2D(u_TextureUnit, v_TexCoord * cloneCount);\n" +
                    "}";

    public CloneFullFilter(Context context) {
        super(context);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int cloneCountLocation = getUniform("cloneCount");
        GLES20.glUniform1f(cloneCountLocation, 3f);
    }
}

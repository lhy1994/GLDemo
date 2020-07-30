package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class ClonePartFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "varying vec2 v_TexCoord;\n" +
                    "uniform sampler2D u_TextureUnit;\n" +
                    "uniform float isVertical;\n" +
                    "uniform float isHorizontal;\n" +
                    "uniform float cloneCount;\n" +
                    "        void main() {\n" +
                    "                vec4 source = texture2D(u_TextureUnit, v_TexCoord);\n" +
                    "                float coordX = v_TexCoord.x;\n" +
                    "                float coordY = v_TexCoord.y;\n" +
                    "                if (isVertical == 1.0) {\n" +
                    "                    float width = 1.0 / cloneCount;\n" +
                    "                    float startX = (1.0 - width) / 2.0;\n" +
                    "                    coordX = mod(v_TexCoord.x, width) + startX;\n" +
                    "                }\n" +
                    "                if (isHorizontal == 1.0) {\n" +
                    "                    float height = 1.0 / cloneCount;\n" +
                    "                    float startY = (1.0 - height) / 2.0;\n" +
                    "                    coordY = mod(v_TexCoord.y, height) + startY;\n" +
                    "                }\n" +
                    "                gl_FragColor = texture2D(u_TextureUnit, vec2(coordX, coordY));\n" +
                    "        }";

    public ClonePartFilter(Context context) {
        super(context);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GLES20.glUniform1f(getUniform("isVertical"), 1.0f);
        GLES20.glUniform1f(getUniform("isHorizontal"), 1.0f);
        GLES20.glUniform1f(getUniform("cloneCount"), 3.0f);
    }
}

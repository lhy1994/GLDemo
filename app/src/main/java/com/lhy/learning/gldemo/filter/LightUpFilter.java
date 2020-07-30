package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class LightUpFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            " precision mediump float;\n" +
                    "                varying vec2 v_TexCoord;\n" +
                    "                uniform sampler2D u_TextureUnit;\n" +
                    "                uniform float intensity;\n" +
                    "                void main() {\n" +
                    "                    vec4 src = texture2D(u_TextureUnit, v_TexCoord);\n" +
                    "                    vec4 addColor = vec4(intensity, intensity, intensity, 1.0);\n" +
                    "                    gl_FragColor = src + addColor;\n" +
                    "                }";
    private int intensityLocation;
    private long startTime;

    public LightUpFilter(Context context) {
        super(context);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
        intensityLocation = getUniform("intensity");
    }

    @Override
    public void onDraw() {
        super.onDraw();
        float intensity = (float) (Math.abs(Math.sin((System.currentTimeMillis() - startTime) / 1000.0)) / 4.0);
        GLES20.glUniform1f(intensityLocation, intensity);
    }
}

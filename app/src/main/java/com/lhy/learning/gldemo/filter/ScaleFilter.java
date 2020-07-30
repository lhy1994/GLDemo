package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class ScaleFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "                varying vec2 v_TexCoord;\n" +
                    "                uniform sampler2D u_TextureUnit;\n" +
                    "                uniform float intensity;\n" +
                    "\n" +
                    "                vec2 scale(vec2 srcCoord, float x, float y) {\n" +
                    "                    return vec2((srcCoord.x - 0.5) / x + 0.5, (srcCoord.y - 0.5) / y + 0.5);\n" +
                    "                }\n" +
                    "\n" +
                    "                void main() {\n" +
                    "                    vec2 offsetTexCoord = scale(v_TexCoord, intensity, intensity);\n" +
                    "                    if (offsetTexCoord.x >= 0.0 && offsetTexCoord.x <= 1.0 &&\n" +
                    "                        offsetTexCoord.y >= 0.0 && offsetTexCoord.y <= 1.0) {\n" +
                    "                        gl_FragColor = texture2D(u_TextureUnit, offsetTexCoord);\n" +
                    "                    }\n" +
                    "                }";

    private int intensityLocation;
    private long startTime;

    public ScaleFilter(Context context) {
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
        float intensity = (float) (Math.abs(Math.sin((System.currentTimeMillis() - startTime) / 1000.0)) + 0.5);
        GLES20.glUniform1f(intensityLocation, intensity);
    }
}

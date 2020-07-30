package com.lhy.learning.gldemo.filter;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class CrossFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            " precision mediump float;\n" +
                    "                varying vec2 v_TexCoord;\n" +
                    "                uniform sampler2D u_TextureUnit;\n" +
                    "                uniform float xV;\n" +
                    "                uniform float yV;\n" +
                    "\n" +
                    "                vec2 translate(vec2 srcCoord, float x, float y) {\n" +
                    "                    if (mod(srcCoord.y, 0.25) > 0.125) {\n" +
                    "                        return vec2(srcCoord.x + x, srcCoord.y + y);\n" +
                    "                    } else {\n" +
                    "                        return vec2(srcCoord.x - x, srcCoord.y + y);\n" +
                    "                    }\n" +
                    "                }\n" +
                    "\n" +
                    "                void main() {\n" +
                    "                    vec2 offsetTexCoord = translate(v_TexCoord, xV, yV);\n" +
                    "\n" +
                    "                    if (offsetTexCoord.x >= 0.0 && offsetTexCoord.x <= 1.0 &&\n" +
                    "                        offsetTexCoord.y >= 0.0 && offsetTexCoord.y <= 1.0) {\n" +
                    "                        gl_FragColor = texture2D(u_TextureUnit, offsetTexCoord);\n" +
                    "                    }\n" +
                    "                }";

    private int xLocation;
    private int yLocation;
    private long startTime;

    public CrossFilter(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
        xLocation = getUniform("xV");
        yLocation = getUniform("yV");
    }

    @Override
    public void onDraw() {
        super.onDraw();
        float intensity = (float) (Math.sin((System.currentTimeMillis() - startTime) / 1000.0) * 0.5);
        GLES20.glUniform1f(xLocation, intensity);
        GLES20.glUniform1f(yLocation, 0.0f);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }
}

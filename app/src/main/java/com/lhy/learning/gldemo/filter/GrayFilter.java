package com.lhy.learning.gldemo.filter;

import android.content.Context;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class GrayFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "varying vec2 v_TexCoord;\n" +
                    "uniform sampler2D u_TextureUnit;\n" +
                    "void main() {\n" +
                    "     vec4 src = texture2D(u_TextureUnit, v_TexCoord);\n" +
                    "     float gray = (src.r + src.g + src.b) / 3.0;\n" +
                    "     gl_FragColor =vec4(gray, gray, gray, 1.0);\n" +
                    "}";

    public GrayFilter(Context context) {
        super(context);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }
}

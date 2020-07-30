package com.lhy.learning.gldemo.filter;

import android.content.Context;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class InverseFilter extends BaseFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n" +
                    "varying vec2 v_TexCoord;\n" +
                    "uniform sampler2D u_TextureUnit;\n" +
                    "        void main() {\n" +
                    "                    vec4 src = texture2D(u_TextureUnit, v_TexCoord);\n" +
                    "                    gl_FragColor = vec4(1.0 - src.r, 1.0 - src.g, 1.0 - src.b, 1.0);\n" +
                    "                }";

    public InverseFilter(Context context) {
        super(context);
    }

    @Override
    protected String buildFragmentShader() {
        return FRAGMENT_SHADER;
    }
}

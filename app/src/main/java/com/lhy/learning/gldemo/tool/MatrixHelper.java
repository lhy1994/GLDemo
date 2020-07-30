package com.lhy.learning.gldemo.tool;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class MatrixHelper {
    private int program;
    private String name;
    private int uMatrixLocation;
    private float[] mProjectionMatrix = new float[]{1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};

    public MatrixHelper(int program, String name) {
        this.program = program;
        this.name = name;
        uMatrixLocation = GLES20.glGetUniformLocation(program, name);
    }

    public void enable(int width, int height) {
        float aspectRatio;
        if (width > height) {
            aspectRatio = width * 1f / height * 1f;
        } else {
            aspectRatio = height * 1f / width * 1f;
        }
        if (width > height) {
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, mProjectionMatrix, 0);
    }
}

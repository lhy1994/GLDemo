package com.lhy.learning.gldemo.render;

import android.opengl.GLES20;

import com.lhy.learning.gldemo.tool.ShaderHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/24
 */
public class PointRenderer extends BaseRenderer {

    /**
     * 顶点着色器
     */
    private static final String VERTEX_SHADER = "" +
            // vec4：4个分量的向量：x、y、z、w
            "attribute vec4 a_Position;\n" +
            "void main()\n" +
            "{\n" +
            // gl_Position：GL中默认定义的输出变量，决定了当前顶点的最终位置
            "    gl_Position = a_Position;\n" +
            // gl_PointSize：GL中默认定义的输出变量，决定了当前顶点的大小
            "    gl_PointSize = 40.0;\n" +
            "}";

    /**
     * 片段着色器
     */
    private static final String FRAGMENT_SHADER = "" +
            // 定义所有浮点数据类型的默认精度；有lowp、mediump、highp 三种，但只有部分硬件支持片段着色器使用highp。(顶点着色器默认highp)
            "precision mediump float;\n" +
            "uniform mediump vec4 u_Color;\n" +
            "void main()\n" +
            "{\n" +
            // gl_FragColor：GL中默认定义的输出变量，决定了当前片段的最终颜色
            "    gl_FragColor = u_Color;\n" +
            "}";

    /**
     * 顶点坐标数据缓冲区
     */
    private FloatBuffer mVertexData;

    /**
     * 顶点数据数组
     */
    private float[] POINT_DATA = new float[]{
            // 点的x,y坐标（x，y各占1个分量）
            0f, 0f};

    /**
     * 颜色uniform在OpenGL程序中的索引
     */
    private int uColorLocation;
    /**
     * 顶点坐标在OpenGL程序中的索引
     */
    private int aPositionLocation;

    @Override
    public void onInit() {
        // 分配一个块Native内存，用于与GL通讯传递。(我们通常用的数据存在于Dalvik的内存中，1.无法访问硬件；2.会被垃圾回收)
        mVertexData = ShaderHelper.createFloatBuffer(POINT_DATA);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置刷新屏幕时候使用的颜色值,顺序是RGBA，值的范围从0~1。GLES20.glClear调用时使用该颜色值。
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        makeProgram(VERTEX_SHADER, FRAGMENT_SHADER);

        // 步骤5：获取颜色Uniform在OpenGL程序中的索引
        uColorLocation = getUniform("u_Color");
        // 步骤6：获取顶点坐标属性在OpenGL程序中的索引
        aPositionLocation = getAttrib("a_Position");

        // 将缓冲区的指针移动到头部，保证数据是从最开始处读取
        mVertexData.position(0);
        // 步骤7：关联顶点坐标属性和缓存数据
        // 1. 位置索引；
        // 2. 每个顶点属性需要关联的分量个数(必须为1、2、3或者4。初始值为4。)；
        // 3. 数据类型；
        // 4. 指定当被访问时，固定点数据值是否应该被归一化(GL_TRUE)或者直接转换为固定点值(GL_FALSE)(只有使用整数数据时)
        // 5. 指定连续顶点属性之间的偏移量。如果为0，那么顶点属性会被理解为：它们是紧密排列在一起的。初始值为0。
        // 6. 数据缓冲区
        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT,
                false, 0, mVertexData);

        // 步骤8：通知GL程序使用指定的顶点属性索引
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 步骤1：使用glClearColor设置的颜色，刷新Surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // 步骤2：更新u_Color的值，即更新画笔颜色
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        // 步骤3：使用数组绘制图形：1.绘制的图形类型；2.从顶点数组读取的起点；3.从顶点数组读取的顶点个数
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }

    @Override
    public void release() {
        GLES20.glDisableVertexAttribArray(aPositionLocation);
        GLES20.glDisableVertexAttribArray(uColorLocation);
        GLES20.glDeleteProgram(mProgram);
    }
}

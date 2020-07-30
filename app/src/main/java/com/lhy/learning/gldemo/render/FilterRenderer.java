package com.lhy.learning.gldemo.render;

import android.content.Context;
import android.util.Log;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.filter.BaseFilter;
import com.lhy.learning.gldemo.filter.GrayFilter;
import com.lhy.learning.gldemo.filter.InverseFilter;
import com.lhy.learning.gldemo.filter.LightUpFilter;
import com.lhy.learning.gldemo.filter.ScaleFilter;
import com.lhy.learning.gldemo.tool.TextureHelper;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: liuhaoyuan
 * @CreateDate: 2020/7/28
 */
public class FilterRenderer extends BaseRenderer {
    private ArrayList<BaseFilter> mFilters;
    private BaseFilter mCurrFilter;
    private int mDrawIndex;
    private boolean mIsChanged;
    private TextureHelper.TextureBean mTextureBean;

    public FilterRenderer(Context context) {
        super(context);
    }

    @Override
    public void release() {
    }

    @Override
    public void onInit() {
        mFilters = new ArrayList<>();
//        mFilters.add(new CrossFilter(getContext()));
//        mFilters.add(new ClonePartFilter(getContext()));
//        mFilters.add(new BaseFilter(getContext()));
//        mFilters.add(new CloneFullFilter(getContext()));
//        mFilters.add(new TranslateFilter(getContext()));
        mFilters.add(new ScaleFilter(getContext()));
        mFilters.add(new InverseFilter(getContext()));
        mFilters.add(new GrayFilter(getContext()));
        mFilters.add(new LightUpFilter(getContext()));
        mCurrFilter = mFilters.get(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (mCurrFilter != null) {
            mCurrFilter.onCreate();
        }
        mTextureBean = TextureHelper.loadTexture(getContext(), R.drawable.pikachu);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        Log.i("MyRenderer", "onSurfaceChanged: "+width+" h= "+height);
        if (mCurrFilter != null) {
            mCurrFilter.onSizeChanged(width, height);
            mCurrFilter.setTextureBean(mTextureBean);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mIsChanged) {
            mCurrFilter = mFilters.get(mDrawIndex);

            for (BaseFilter filter : mFilters) {
                if (filter != mCurrFilter) {
                    filter.onDestroy();
                }
            }
            mCurrFilter.onCreate();
            mCurrFilter.onSizeChanged(outputWidth, outputHeight);
            mCurrFilter.setTextureBean(mTextureBean);
            mIsChanged = false;
        }
        if (mCurrFilter != null) {
            mCurrFilter.onDraw();
        }
    }

    public void next() {
        mDrawIndex++;
        if (mDrawIndex >= mFilters.size()) {
            mDrawIndex = 0;
        }
        mIsChanged = true;
    }
}

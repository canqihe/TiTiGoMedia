package com.trueu.titigomedia;

import android.app.Activity;
import android.view.View;

/**
 * Created by Colin
 * on 2020/8/6
 * E-mail: hecanqi168@gmail.com
 */
public abstract class BaseDetailPager {
    public Activity mActivity;
    public View mRootView;// 根布局对象

    public BaseDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }
}

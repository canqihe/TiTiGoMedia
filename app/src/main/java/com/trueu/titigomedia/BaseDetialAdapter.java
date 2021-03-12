package com.trueu.titigomedia;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Colin
 * on 2020/8/6
 * E-mail: hecanqi168@gmail.com
 */
public class BaseDetialAdapter extends PagerAdapter {

    private BaseDetialAdapter pager;

    private List<BaseDetailPager> mPageList;

    String titles[];

    public BaseDetialAdapter(List<BaseDetailPager> pageList, String[] arr) {

        this.titles = arr;
        this.mPageList = pageList;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        BaseDetailPager pager = mPageList.get(position);
        container.addView(pager.mRootView);
        pager.initData();
        return pager.mRootView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

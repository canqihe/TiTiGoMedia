package com.trueu.titigomedia.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trueu.titigomedia.BaseDetailPager;
import com.trueu.titigomedia.BaseDetialAdapter;
import com.trueu.titigomedia.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    Unbinder unbinder;

    String[] titles = {"AES对称加解密", "摩尔斯加解密","Base64加解密", "MD5加密", "SHA加密"};

    private List<BaseDetailPager> mPagerList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initData() {

        mPagerList.add(new HomePager(getActivity()));
        mPagerList.add(new HomePager(getActivity()));
        mPagerList.add(new HomePager(getActivity()));
        mPagerList.add(new HomePager(getActivity()));
        mPagerList.add(new HomePager(getActivity()));

        viewPager.setAdapter(new BaseDetialAdapter(mPagerList, titles));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }
}
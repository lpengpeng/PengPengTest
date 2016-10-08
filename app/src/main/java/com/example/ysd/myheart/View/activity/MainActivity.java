package com.example.ysd.myheart.View.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ysd.myheart.BaseActivity;
import com.example.ysd.myheart.R;
import com.example.ysd.myheart.View.fragment.BlankFragment;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity {

    private FragmentTransaction mBeginTransaction;
    private TextView mTvHome;
    private TextView mTvClassify;
    private TextView mTvCart;
    private TextView mTvMine;
    private FrameLayout mFrameLayout;
    private BlankFragment mHomeFragment;
    private BlankFragment mClassifyFragment;
    private BlankFragment mCartFragment;
    private BlankFragment mMineFragment;
    private Fragment currentFragment;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_home:
                switchFragmnt(1);
                break;
            case R.id.tv_tab_classify:
                switchFragmnt(2);
                break;
            case R.id.tv_tab_cart:
                switchFragmnt(3);
                break;
            case R.id.tv_tab_mine:
                switchFragmnt(4);
                break;
        }
    }

    public void switchFragmnt(int which) {


        switch (which) {
            case 1:
                if (mHomeFragment == null) {
                    mHomeFragment = BlankFragment.newInstance("home", "1");
                }

                selectFragment(mHomeFragment);
                break;
            case 2:
                if (mClassifyFragment == null) {
                    mClassifyFragment = BlankFragment.newInstance("Classify", "2");
                }
                selectFragment(mClassifyFragment);
                break;
            case 3:
                if (mCartFragment == null) {
                    mCartFragment = BlankFragment.newInstance("mcart", "3");
                }
                selectFragment(mCartFragment);
                break;
            case 4:
                if (mMineFragment != null) {
                    getFramentTransaction();
                    mBeginTransaction.remove(mMineFragment).commit();
                    mBeginTransaction = null;

                }
                mMineFragment = BlankFragment.newInstance("mMIne", "4");
                selectFragment(mMineFragment);
                break;
        }

    }

    public void selectFragment(Fragment to) {
        getFramentTransaction();
//        mBeginTransaction.addToBackStack(null);
        // TODO: 2016/9/28 这个是针对单纯的返回不包含数据刷新 
        if (currentFragment == null) {

            mBeginTransaction.add(R.id.fl_content, to).commitAllowingStateLoss();
            currentFragment = to;
            mBeginTransaction = null;
            return;
        }

        if (!to.isAdded()) {
            mBeginTransaction.hide(currentFragment).add(R.id.fl_content, to).commitAllowingStateLoss();
        } else {
            mBeginTransaction.hide(currentFragment).show(to).commitAllowingStateLoss();
        }

        currentFragment = to;
        mBeginTransaction = null;

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mFrameLayout = $(R.id.fl_content);
        mTvHome = $(R.id.tv_tab_home);
        mTvClassify = $(R.id.tv_tab_classify);
        mTvCart = $(R.id.tv_tab_cart);
        mTvMine = $(R.id.tv_tab_mine);
    }

    @Override
    public void setListener() {
        mTvHome.setOnClickListener(this);
        mTvClassify.setOnClickListener(this);
        mTvCart.setOnClickListener(this);
        mTvMine.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        mTvHome.performClick();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post("hehheh");
            finish();
        }
        return true;

    }

    /**
     * 获取fragment的事物管理
     */
    public void getFramentTransaction() {
        if (mBeginTransaction == null)
            mBeginTransaction = getSupportFragmentManager().beginTransaction();
    }

}

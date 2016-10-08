package com.example.ysd.myheart;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysd.myheart.View.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by YSD on 2016/9/27.
 */

public class SplashActivity extends BaseActivity {
    private final static int WRITE_EXTERNAL_STORAGE = 0x001;

    private boolean mRequestSd = true;
    private TextView mTextView;

    @Override
    public void widgetClick(View v) {

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
        return R.layout.activity_splash;
    }

    @Override
    public void initView(View view) {
        mTextView = $(R.id.tv_msg_splash);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        EventBus.getDefault().register(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
            }
        }, 1000);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Event message) {

        mTextView.setText(message.getMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();

        //适配6.0获取Android存储空间权限 2016/9/20
        //优化6.0获取权限方式2016/9/26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (mRequestSd) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE);
                } else {
                    Toast.makeText(SplashActivity.this, "权限我有了1", Toast.LENGTH_SHORT).show();

                }
            } else {
                mRequestSd = true;
            }
        } else {
            Toast.makeText(SplashActivity.this, "权限我有了2", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SplashActivity.this, "挂在好了", Toast.LENGTH_LONG).show();
                } else {
                    mRequestSd = false;
                    Toast.makeText(SplashActivity.this, "我要去挂载", Toast.LENGTH_LONG).show();
                    showMissingPermissionDialog();

                }
                break;

        }
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限\n请点击“设置”-“权限”-打开所需权限");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SplashActivity.this, "打开去设置", Toast.LENGTH_LONG).show();
                startAppSettings();
            }
        });
        builder.show();

    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

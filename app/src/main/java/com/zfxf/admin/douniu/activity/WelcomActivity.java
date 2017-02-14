package com.zfxf.admin.douniu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.tools.Constants;
import com.zfxf.admin.douniu.tools.MyTools;
import com.zfxf.admin.douniu.tools.SpTools;
import com.zfxf.admin.douniu.tools.ToolsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView mImageView;
    private AlphaAnimation mAa;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = MyTools.getContext();
        initview();//初始化界面
        startAnimation();//播放动画
        initEvent();//初始化事件

    }

    private void initEvent() {
        mAa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkInernet();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void checkInernet() {
        if (!ToolsUtils.isNetworkAvailable(getApplicationContext())) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(WelcomActivity.this);
            dialog.setTitle("当前无可用网络，请联网后再试");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    System.exit(0);
                }
            });
            AlertDialog alertDialog1 = dialog.create();
            alertDialog1.setCancelable(false);
            alertDialog1.show();
        } else {
            gotoLoading();
        }
    }

    private void gotoLoading() {
        if (SpTools.getBoolean(mContext, Constants.ISSETUP, false)) {
            Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, GuideActivity.class);
            startActivity(intent);
        }
        WelcomActivity.this.finish();
    }

    private void startAnimation() {
        //渐变动画
        mAa = new AlphaAnimation(1, 1);
        mAa.setDuration(2000);
        mAa.setFillAfter(true);
        mImageView.startAnimation(mAa);//开始播放动画
    }

    private void initview() {
        setContentView(R.layout.activity_welcom);
        ButterKnife.bind(this);

        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {//沉浸式状态栏
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = MyTools.getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }

}

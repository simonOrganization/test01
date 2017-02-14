package com.zfxf.admin.douniu.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.activity.MainActivity;
import com.zfxf.admin.douniu.activity.TestActivity;
import com.zfxf.admin.douniu.base.BasePage;

/**
 * @author Admin
 * @time 2017/2/10 14:37
 * @des ${TODO}
 */

public class ZixunBasePage extends BasePage {

    public ZixunBasePage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {

        TextView tv = new TextView(mainActivity);
        tv.setText("咨询的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        Button button = new Button(mainActivity);
        button.setText("咨询的按钮");
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                ,FrameLayout.LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, TestActivity.class);
                mainActivity.startActivity(intent);
                mainActivity.overridePendingTransition(R.anim.out_to_buttom,R.anim.out_to_top);
            }
        });
        // 替换掉白纸
        fl_base.addView(button,params);// 添加自己的内容到白纸上
        super.initData();
    }
}

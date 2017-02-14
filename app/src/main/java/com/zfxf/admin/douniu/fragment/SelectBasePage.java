package com.zfxf.admin.douniu.fragment;

import android.view.Gravity;
import android.widget.TextView;

import com.zfxf.admin.douniu.activity.MainActivity;
import com.zfxf.admin.douniu.base.BasePage;

/**
 * @author Admin
 * @time 2017/2/10 14:37
 * @des ${TODO}
 */

public class SelectBasePage extends BasePage {

    public SelectBasePage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {

        TextView tv = new TextView(mainActivity);
        tv.setText("自选的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        // 替换掉白纸
        fl_base.addView(tv);// 添加自己的内容到白纸上
        super.initData();
    }
}

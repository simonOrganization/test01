package com.zfxf.admin.douniu.base;

import android.view.View;
import android.widget.FrameLayout;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.activity.MainActivity;

/**
 * @author Admin
 * @time 2017/2/10 14:25
 * @des ${TODO}
 */

public class BasePage {
    protected MainActivity mainActivity;
    protected View	root;
    protected FrameLayout fl_base;

    public BasePage(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initView();
    }

    private void initView() {
        root = View.inflate(mainActivity, R.layout.fragment_base, null);
        fl_base = (FrameLayout) root.findViewById(R.id.fl_base_tag);
    }

    public View getRoot(){
        return root;
    }

    public void initData(){

    }
}

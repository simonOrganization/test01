package com.zfxf.admin.douniu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.base.BasePage;
import com.zfxf.admin.douniu.fragment.HomeBasePage;
import com.zfxf.admin.douniu.fragment.MyBasePage;
import com.zfxf.admin.douniu.fragment.SelectBasePage;
import com.zfxf.admin.douniu.fragment.ZixunBasePage;
import com.zfxf.admin.douniu.tools.MyTools;
import com.zfxf.admin.douniu.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.vp_mainactivity)
    MyViewPager mViewPager;
    @BindView(R.id.rg_main)
    RadioGroup mRadioGroup;

    private List<BasePage> mPages = new ArrayList<BasePage>();
    private MainActivity mActivity;
    private int selectIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = this;
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_mainactivity_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = MyTools.getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }

    public void initData() {
        mPages.add(new HomeBasePage(mActivity));
        mPages.add(new SelectBasePage(mActivity));
        mPages.add(new ZixunBasePage(mActivity));
        mPages.add(new MyBasePage(mActivity));

        MyAdapter myAdapter = new MyAdapter();
        mViewPager.setAdapter(myAdapter);

        //设置默认选择首页
        switchPage();
        //设置第一个按钮被选中(首页)
        mRadioGroup.check(R.id.rb_main_main);
    }

    public void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_main_main://主页
                        selectIndex = 0;
                        break;
                    case R.id.rb_main_select://自选
                        selectIndex = 1;
                        break;
                    case R.id.rb_main_zixun://咨询
                        selectIndex = 2;
                        break;
                    case R.id.rb_main_my://我的
                        selectIndex = 3;
                        break;
                }
                switchPage();
            }
        });
//        mViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//            @Override
//            public void onPageSelected(int position) {
//                selectIndex = position;
//                switchPage();
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private void switchPage() {
        switch (selectIndex) {
            case 0:
                mRadioGroup.check(R.id.rb_main_main);
                break;
            case 1:
                mRadioGroup.check(R.id.rb_main_select);
                break;
            case 2:
                mRadioGroup.check(R.id.rb_main_zixun);
                break;
            case 3:
                mRadioGroup.check(R.id.rb_main_my);
                break;
        }
        mViewPager.setCurrentItem(selectIndex,false);//去掉滑动的平滑效果
    }

    private class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mPages.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePage basePage = mPages.get(position);
            View root = basePage.getRoot();
            container.addView(root);
            basePage.initData();//加载数据
            return root;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

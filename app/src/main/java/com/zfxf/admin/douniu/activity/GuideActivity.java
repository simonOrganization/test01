package com.zfxf.admin.douniu.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.tools.Constants;
import com.zfxf.admin.douniu.tools.DensityUtil;
import com.zfxf.admin.douniu.tools.MyTools;
import com.zfxf.admin.douniu.tools.SpTools;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private ViewPager vp_guide;
    private LinearLayout ll_point;
    private View v_redpoint;
    private Button bt_start;
    private ArrayList<ImageView> datas;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
        initEvent();
    }

    private int distance;
    private void initEvent() {
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpTools.setBoolean(getApplicationContext(), Constants.ISSETUP, true);
                Intent main = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        //计算灰色点直接的距离
        v_redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v_redpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                distance = ll_point.getChildAt(1).getLeft() - ll_point.getChildAt(0).getLeft();
            }
        });

        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
            public void onPageSelected(int position) {
                if(position == datas.size()-1){
                    bt_start.setVisibility(View.VISIBLE);
                }else{
                    bt_start.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
                float leftmargin = distance *(position + positionOffset);
                RelativeLayout.LayoutParams  params = (android.widget.RelativeLayout.LayoutParams) v_redpoint.getLayoutParams();
                params.leftMargin = Math.round(leftmargin);
                v_redpoint.setLayoutParams(params);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        int [] pics = new int[] {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
        datas = new ArrayList<ImageView>();
        for (int i = 0; i < pics.length; i++) {
            ImageView iv_temp = new ImageView(getApplicationContext());
            iv_temp.setBackgroundResource(pics[i]);
            datas.add(iv_temp);
            //设置灰点
            View v_point = new View(getApplicationContext());
            v_point.setBackgroundResource(R.drawable.gray_point);
            float dip = 10;
            LayoutParams params = new LayoutParams(DensityUtil.dip2px(getApplicationContext(), dip),
                    DensityUtil.dip2px(getApplicationContext(), dip));
            if(i != 0){
                params.leftMargin = 20;
            }
            v_point.setLayoutParams(params);
            ll_point.addView(v_point);
        }
        myAdapter = new MyAdapter();
        vp_guide.setAdapter(myAdapter);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View child = datas.get(position);
            container.addView(child);
            return child;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    private void initView() {
        setContentView(R.layout.activity_guide);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll_guide_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = MyTools.getStatusBarHeight();
            //动态的设置隐藏布局的高度
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }

        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_point = (LinearLayout) findViewById(R.id.ll_guide_points);//动态加载点
        v_redpoint = findViewById(R.id.v_guide_redpoint);//红点
        bt_start = (Button) findViewById(R.id.bt_guide_start);
    }
}

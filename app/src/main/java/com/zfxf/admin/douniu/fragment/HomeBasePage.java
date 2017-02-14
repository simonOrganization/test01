package com.zfxf.admin.douniu.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.activity.MainActivity;
import com.zfxf.admin.douniu.base.BasePage;
import com.zfxf.admin.douniu.tools.DensityUtil;
import com.zfxf.admin.douniu.tools.MyTools;
import com.zfxf.admin.douniu.view.InnerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 * @time 2017/2/10 14:37
 * @des ${TODO}
 */

public class HomeBasePage extends BasePage {

    InnerView mViewPage;
    LinearLayout mContainer;
    private List<String> mDatas = new ArrayList<String>();
    private AutoScrollTask mAutoScrollTask;

    public HomeBasePage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {
        fl_base.removeAllViews();
        if(mDatas.size()>0){
            mDatas.clear();
        }
        if(mAutoScrollTask != null){
            mAutoScrollTask.stop();
        }

        View view = View.inflate(mainActivity, R.layout.fragment_home, null);
        mViewPage = (InnerView) view.findViewById(R.id.item_home_pic_vp);
        mContainer = (LinearLayout) view.findViewById(R.id.item_home_pic_ll);

        mDatas.add("http://image.tianjimedia.com/uploadImages/2014/067/5116EPAUD762_1000x500.jpg");
        mDatas.add("http://img1.3lian.com/2015/a2/230/d/34.jpg");
        mDatas.add("http://img.anfone.com/outside/anfone/201412/2014120121431576220.jpg");
        mDatas.add("http://img.dfhon.cn/pictures/200912041016/dd8451_huang.jpg");
        mDatas.add("http://f3.topit.me/3/ea/e2/1128274861976e2ea3o.jpg");
        mViewPage.setAdapter(new myPicAdapter());

        refreshHolder();

//        ImageView imageView = new ImageView(mainActivity);
//        String imageurl = "http://img.my.csdn.net/uploads/201210/19/1350641152_5486.png";
//        MyTools.displayImage(imageView, imageurl);

        // 替换掉白纸
        fl_base.addView(view);// 添加自己的内容到白纸上
        super.initData();
    }

    private void refreshHolder() {
        // 添加小点
        for (int i = 0; i < mDatas.size(); i++) {
            View indicatorView = new View(MyTools.getContext());
            indicatorView.setBackgroundResource(R.drawable.indicator_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(MyTools.getContext(),10)
                    ,DensityUtil.dip2px(MyTools.getContext(),10));// dp-->px
            // 左边距
            params.leftMargin = DensityUtil.dip2px(MyTools.getContext(),5);
            // 下边距
            params.bottomMargin = DensityUtil.dip2px(MyTools.getContext(),5);
            mContainer.addView(indicatorView, params);
            // 默认选中效果
            if (i == 0) {
                indicatorView.setBackgroundResource(R.drawable.indicator_selected);
            }
        }

        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                position = position % mDatas.size();
                for (int i = 0; i < mDatas.size(); i++) {
                    View indicatorView = mContainer.getChildAt(i);
                    // 还原背景
                    indicatorView.setBackgroundResource(R.drawable.indicator_normal);
                    if (i == position) {
                        indicatorView.setBackgroundResource(R.drawable.indicator_selected);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 设置curItem为count/2
        int diff = Integer.MAX_VALUE / 2 % mDatas.size();
        int index = Integer.MAX_VALUE / 2;
        mViewPage.setCurrentItem(index - diff);

        // 自动轮播
        mAutoScrollTask = new AutoScrollTask();
        mAutoScrollTask.start();
        // 用户触摸的时候移除自动轮播的任务
        mViewPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoScrollTask.start();
                        break;
                }
                return false;
            }
        });


    }
    class AutoScrollTask implements Runnable {
        public void start() {/**开始轮播*/
            MyTools.postTaskDelay(this, 4000);
        }
        public void stop() { /**结束轮播*/
            MyTools.removeTask(this);
        }
        @Override
        public void run() {
            int item = mViewPage.getCurrentItem();
            item++;
            mViewPage.setCurrentItem(item);
            // 结束-->再次开始
            start();
        }
    }

    class myPicAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            if(mDatas !=null){
                return  Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            int pos = position % mDatas.size();
            ImageView iv = new ImageView(mainActivity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            MyTools.displayImage(iv,mDatas.get(pos));
            container.addView(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick(position % mDatas.size());
                }
            });
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void itemClick(int pos){
        Toast.makeText(mainActivity,"您点击的是第 "+ (++pos) +" 个Item",Toast.LENGTH_SHORT).show();
    }

}

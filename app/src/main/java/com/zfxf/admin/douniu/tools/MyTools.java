package com.zfxf.admin.douniu.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zfxf.admin.douniu.R;
import com.zfxf.admin.douniu.base.BaseApplication;

import java.lang.reflect.Field;

/**
 * @author Admin
 * @time 2017/2/10 12:45
 * @des ${TODO}
 */

public class MyTools {
    /**得到上下文*/
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**得到Resouce对象*/
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**得到应用程序的包名*/
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**得到主线程id*/
    public static long getMainThreadid() {
        return BaseApplication.getMainTreadId();
    }

    /**得到主线程Handler*/
    public static Handler getMainThreadHandler() {
        return BaseApplication.getHandler();
    }

    /**安全的执行一个任务*/
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();

        if (curThreadId == getMainThreadid()) {// 如果当前线程是主线程
            task.run();
        } else {// 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**延迟执行任务*/
    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**移除任务*/
    public static void removeTask(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }

    /**
     * 通过反射的方式获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void displayImage(ImageView container,String uri) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)//loading时候的图片
                .showImageOnFail(R.mipmap.ic_launcher)//失败时候的图片
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(uri,container,options);
    }
    static {
        //创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(BaseApplication.getContext());
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);
    }

}

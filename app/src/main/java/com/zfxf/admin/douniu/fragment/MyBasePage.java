package com.zfxf.admin.douniu.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zfxf.admin.douniu.activity.MainActivity;
import com.zfxf.admin.douniu.base.BasePage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author Admin
 * @time 2017/2/10 14:37
 * @des ${TODO}
 */

public class MyBasePage extends BasePage {

    public MyBasePage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initData() {

        TextView tv = new TextView(mainActivity);
        tv.setText("我的内容");
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);

        Button button = new Button(mainActivity);
        button.setText("我是按钮");
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                ,FrameLayout.LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mainActivity,"点击了按钮",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mainActivity, TestActivity.class);
//                mainActivity.startActivity(intent);
                String url = "http://domain/index.php/valid/send/";
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("mybasepage","Exception:"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("mybasepage","response:"+response);
                    }
                });
            }
        });
        // 替换掉白纸
        fl_base.addView(button,params);// 添加自己的内容到白纸上
        super.initData();
    }
}

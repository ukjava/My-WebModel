package com.ukjava.mywebmodel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.ukjava.mywebmodel.adapter.MyAdapter;
import com.ukjava.mywebmodel.bean.MyBean;
import com.ukjava.mywebmodel.utils.HttpUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    class MyHandler extends Handler{
        // 虚引用的引用
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity){
            mActivity = new WeakReference<Activity>(activity);
        }

        //handler消息机制的方法
        @Override
        public void handleMessage(Message msg) {
            MyBean myBean = (MyBean) msg.obj;
            Activity activity = mActivity.get();
            if (activity != null){
                MyAdapter adapter = new MyAdapter(MainActivity.this,
                        (ArrayList<MyBean.ResultsBean>) myBean.getResults());

                recyclerView.setAdapter(adapter);
            }

        }
    }

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyHandler myHandler = new MyHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使标题栏延伸到状态栏
        View decorView = getWindow().getDecorView();
       int optain =  View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
       decorView.setSystemUiVisibility(optain);
       getWindow().setStatusBarColor(Color.TRANSPARENT);

       initView();
       initDAta();


    }

    //初始化控件
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycle_view);
        //支持toobar的操作  （注：清单文件中吧actionbar去掉）
        setSupportActionBar(toolbar);

    }

    //初始化数据
    private void initDAta() {
        String Url = "https://gank.io/api/data/福利/10/1";
        //callback 回调运行在子线程中的 不能更新UI的
        HttpUtils.OkHttpAsyncGetRequest(Url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
               MyBean myBean = gson.fromJson(response.body().string(),MyBean.class);

                //获得对象的方式,handler消息机制
                Message message = Message.obtain();
                message.what = 0x11;
                message.obj = myBean;
                myHandler.sendMessage(message);

            }
        });

        // 设置布局管理  默认动画
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}

package com.ukjava.mywebmodel.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtils {
    // get 异步请求   注： callback回调在子线程中执行
    public static void OkHttpAsyncGetRequest(String Url, Callback callback){
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Url).get().build();
        httpClient.newCall(request).enqueue(callback);
    }
}

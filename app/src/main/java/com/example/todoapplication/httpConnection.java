package com.example.todoapplication;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class httpConnection {
    private OkHttpClient client;
    private static httpConnection instance = new httpConnection();
    public static httpConnection getInstance(){
        return instance;
    }

    private httpConnection(){
        this.client = new OkHttpClient();
    }

    /* 웹 서버 요청 */
    public void requestWebServer(String parameter, String parameter2, Callback callback){
        RequestBody body = new FormBody.Builder().add("parameter",parameter).add("parameter2",parameter2).build();
        Request request = new Request.Builder().url("API url").post(body).build();
        client.newCall(request).enqueue(callback);
    }
}

package com.example.todoapplication;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OKHttpAPICall {

    //GET request
    public void get(String requestURL){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(requestURL).build();        //GET request

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    //POST request
    public void post(String requestURL, String inputdata) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonInput = new JSONObject();

            try{
                jsonInput.put("todo",inputdata);
            }catch (JSONException e){
                e.printStackTrace();
                return;
            }

            RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonInput.toString());

            Request request = new Request.Builder().url(requestURL).post(body).build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

    //PUT request
    public void put(String requestURL, String inputdata) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonInput = new JSONObject();

            try{
                jsonInput.put("todo",inputdata);
            }catch (JSONException e){
                e.printStackTrace();
                return;
            }

            RequestBody body = RequestBody.create(MediaType.parse("application/json"),jsonInput.toString());

            Request request = new Request.Builder().url(requestURL).put(body).build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }
}



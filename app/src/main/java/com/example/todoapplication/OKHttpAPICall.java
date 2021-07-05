package com.example.todoapplication;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
            RequestBody body = new FormBody.Builder().add("data",inputdata).build();

            Request request = new Request.Builder().url(requestURL).post(body).build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

    //PUT request
}



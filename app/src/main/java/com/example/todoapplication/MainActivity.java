package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_todo;
    private todoAdapter mAdapter;
    private ArrayList<Todo> todoItems;
    private Button btn_save;
    private EditText input_todo;
    private RecyclerView.LayoutManager mLayoutManager;
    private OKHttpAPICall apiCall = new OKHttpAPICall();
    String base_url = "http://192.168.1.81:8000/todolist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoItems = new ArrayList<Todo>();

        btn_save = findViewById(R.id.btn_save);
        rv_todo = findViewById(R.id.todorecycle);
        input_todo = findViewById(R.id.input_todo);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new todoAdapter(todoItems,this);

        rv_todo.setLayoutManager(mLayoutManager);
        rv_todo.setAdapter(mAdapter);

        get_DB();
        System.out.println(todoItems);

        //저장버튼 클릭시 이벤트
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_todo = input_todo.getText().toString();
                input_todo.setText("");
                //UI 변경
                Todo todo = new Todo();
                todo.setTodo_contents(add_todo);
                todoItems.add(todo);
                mAdapter = new todoAdapter(todoItems,MainActivity.this);
                rv_todo.setAdapter(mAdapter);
                //서버 POST 요청 (새로운 할 일 POST)
                new Thread(){
                    public void run(){
                        apiCall.post(base_url,add_todo);
                    }
                }.start();;

            }
        });

    }

    private void get_DB(){
        //DB 에서 내용 받아오는 함수

        new Thread() {
            public void run(){
                String output;
                output = apiCall.get(base_url);         //DB 값 잘 넘어옴

                System.out.println(output);

                System.out.println("test");
                jsonParsing(output);
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);

            }
        }.start();;

    }

    private void jsonParsing(String str)
    {
        try{
            JSONArray todoArray = new JSONArray(str);
            for(int i = 0; i < todoArray.length(); i++){
                JSONObject todoObject = todoArray.getJSONObject(i);

                Todo item = new Todo();

                item.setTodo_contents(todoObject.getString("todo_contents"));

                todoItems.add(item);
            }
        }catch(JSONException e){
            e.printStackTrace();
            todoItems = null;
        }
    }

    final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if(todoItems !=null) {
                System.out.println("여기");
                mAdapter = new todoAdapter(todoItems, MainActivity.this);
                rv_todo.setHasFixedSize(true);
                rv_todo.setAdapter(mAdapter);
            }
        }
    };

}
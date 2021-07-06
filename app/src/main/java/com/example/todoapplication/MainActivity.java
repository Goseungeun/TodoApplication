package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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

        load_recent_DB();

        //저장버튼 클릭시 이벤트
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UI 변경
                Todo todo = new Todo();
                todo.setTodo_contents(input_todo.getText().toString());
                mAdapter.addItem(todo);
                //서버 POST 요청 (새로운 할 일 POST)
                new Thread(){
                    public void run(){
                        apiCall.post(base_url,input_todo.getText().toString());
                    }
                }.start();;

            }
        });

    }

    private void load_recent_DB(){
        new Thread() {
            public void run(){
                apiCall.get(base_url,callback);
            }
        }.start();;
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.err.println(e.toString());
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String DB_Data = response.body().string();
            System.out.println(DB_Data);
            try{
                JSONArray todoArray = new JSONArray(DB_Data);

                for(int i = 0; i < todoArray.length() ; i++){
                    JSONObject jObject = todoArray.getJSONObject(i);

                    Todo item = new Todo();

                    item.setTodo_contents(jObject.getString("todo_contents")); //item에 DB 내용 잘 들어감
                    todoItems.add(item);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            if(mAdapter == null){
                mAdapter =  new todoAdapter(todoItems,getApplicationContext());
                rv_todo.setHasFixedSize(true);
                rv_todo.setAdapter(mAdapter);
            }

        }
    };

}
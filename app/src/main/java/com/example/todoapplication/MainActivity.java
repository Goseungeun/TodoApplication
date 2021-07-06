package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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



        //저장버튼 클릭시 이벤트
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

}
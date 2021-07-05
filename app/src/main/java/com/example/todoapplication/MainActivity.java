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
    private EditText addtodo;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save = findViewById(R.id.btn_save);
        rv_todo = findViewById(R.id.todorecycle);
        addtodo = findViewById(R.id.input_todo);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new todoAdapter(todoItems,this);

        rv_todo.setLayoutManager(mLayoutManager);
        rv_todo.setAdapter(mAdapter);
        todoItems = new ArrayList<Todo>();

        //저장버튼 클릭시 이벤트
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo();
                todo.setTodo_contents(addtodo.getText().toString());
                todoItems.add(todo);
                //서버 POST 요청 (새로운 할 일 POST)
                //서버에 출력된 데이터 가져오기 (리스트 하나하나 아이템으로 출력)
                addtodo.setText("");
            }
        });

    }

}
package com.example.todoapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder>{
    public Object setItem;
    private ArrayList<Todo> items;
    private Context mContext;

    public todoAdapter(ArrayList<Todo> items, Context mContext){
        this.items = items;
        this.mContext = mContext;
    }

    public todoAdapter(ArrayList<Todo> todoItems) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.todo_item,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Todo item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Todo item){
        items.add(item);
        notifyItemChanged(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox todo_contents;

        public ViewHolder(View itemView){
            super(itemView);

            todo_contents = itemView.findViewById(R.id.todo_item);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition();
                    Todo todoItem = items.get(curPos);

                    String[] strChoiceItem = {"수정하기","삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택하세요");
                    builder.setItems(strChoiceItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0){
                                //수정작업
                                final EditText new_contents = new EditText(mContext);

                                AlertDialog.Builder modify_dia = new AlertDialog.Builder(mContext);
                                modify_dia.setTitle("일정 수정");
                                modify_dia.setView(new_contents);
                                modify_dia.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String contents = new_contents.getText().toString();
                                        todoItem.setTodo_contents(contents);

                                        //서버 UPDATE 요청('/todolist/<string:contents>/modify')
                                        //요청 후 서버에 출력된 데이터 가져오기 ( 리스트 하나하나 만들기)
                                    }
                                });

                                modify_dia.show();
                            }

                            else if(position == 1) {
                                items.remove(curPos);
                                notifyItemRemoved(curPos);
                                //서버 DELETE 요청 ('/todolist/<string:contents>/delete')

                            }
                        }
                    });
                    builder.show();
                }
            });
        }
        public void setItem(Todo item){
            todo_contents.setText(item.getTodo_contents());
        }
    }
}


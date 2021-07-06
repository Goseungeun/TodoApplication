package com.example.todoapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder>{
    public Object setItem;
    private ArrayList<Todo> items;
    private Context mContext;
    private OKHttpAPICall apiCall = new OKHttpAPICall();
    String base_url = "http://192.168.1.81:8000/todolist";
    String modify_url;

    todoAdapter(ArrayList<Todo> items, Context mContext){
        this.items = items;
        this.mContext = mContext;
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
        private ImageView btn_delete;
        private ImageView btn_modify;

        public ViewHolder(View itemView){
            super(itemView);

            todo_contents = itemView.findViewById(R.id.todo_item);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_modify = itemView.findViewById(R.id.btn_modify);

            btn_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();
                    Todo todoItem = items.get(curPos);

                    final EditText new_contents = new EditText(mContext);

                    AlertDialog.Builder modify_dia = new AlertDialog.Builder(mContext);
                    modify_dia.setTitle("일정 수정");
                    modify_dia.setView(new_contents);
                    modify_dia.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //UI 변경
                            String contents = new_contents.getText().toString();
                            String old_contents = todoItem.getTodo_contents();      //url에 넣기 위한 contents를 받아옴
                            todoItem.setTodo_contents(contents);
                            notifyItemChanged(curPos,todoItem);
                            //서버 UPDATE 요청('/todolist/<string:contents>/modify')
                            modify_url = base_url+"/"+old_contents+"/modify";
                            new Thread(){
                                public void run(){
                                    apiCall.put(modify_url,contents);
                                }
                            }.start();;
                        }
                    });

                    modify_dia.show();
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();

                    items.remove(curPos);
                    notifyItemRemoved(curPos);
                }
            });
        }
        public void setItem(Todo item){
            todo_contents.setText(item.getTodo_contents());
        }
    }
}


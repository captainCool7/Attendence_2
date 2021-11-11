package com.example.attendence_2.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendence_2.R;
import com.example.attendence_2.model.StudentModel;

import java.util.ArrayList;

public class StudentItemAdapter extends RecyclerView.Adapter<StudentItemAdapter.StudentItemHolder>{
    Context context;
    ArrayList<StudentModel> studentList;
    ArrayList<String> present_list;


    public StudentItemAdapter(Context context,ArrayList<StudentModel> studentList, ArrayList<String> present_list) {
        this.studentList = studentList;
        this.present_list = present_list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_list_item,parent,false);
        return new StudentItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentItemHolder holder, int position) {
        holder.student_name.setText(studentList.get(position).getName());
        String name = holder.student_name.getText().toString();
        if(present_list.contains(name)){
            holder.card.setBackground(context.getDrawable(R.drawable.student_item_selected));
        }else{
            holder.card.setBackground(context.getDrawable(R.drawable.student_item_simple));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("myApp",holder.card);
                String name = holder.student_name.getText().toString();
                if(present_list.contains(name)){
                    present_list.remove(name);
                    holder.card.setBackground(context.getDrawable(R.drawable.student_item_simple));
                }else{
                    present_list.add(name);
                    holder.card.setBackground(context.getDrawable(R.drawable.student_item_selected));
                }
                Log.d("myApp",present_list.toString());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentItemHolder extends RecyclerView.ViewHolder{
        TextView student_name;
        CardView card;
        public StudentItemHolder(@NonNull View itemView) {
            super(itemView);
            student_name = itemView.findViewById(R.id.tv_student_name);
            card = itemView.findViewById(R.id.student_card_item);
        }
    }
}

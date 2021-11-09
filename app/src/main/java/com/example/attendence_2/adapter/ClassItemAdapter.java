package com.example.attendence_2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendence_2.R;
import com.example.attendence_2.activity.ClassViewActivity;

import java.util.ArrayList;

public class ClassItemAdapter extends RecyclerView.Adapter<ClassItemAdapter.ClassItemHolder> {
    ArrayList<Integer> class_no;
    Context context;

    public ClassItemAdapter(ArrayList<Integer> class_no,Context context) {
        this.class_no = class_no;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.class_item,parent,false);
        return new ClassItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassItemHolder holder,int position) {
        holder.class_number.setText(class_no.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,holder.class_number.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ClassViewActivity.class);
                i.putExtra("classNumber",holder.class_number.getText().toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return class_no.size();
    }

    class ClassItemHolder extends RecyclerView.ViewHolder{
        TextView class_number;

        public ClassItemHolder(@NonNull View itemView) {
            super(itemView);
            class_number = itemView.findViewById(R.id.class_number);
        }
    }
}

package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.attendence_2.R;
import com.example.attendence_2.utils.AttendenceRecord;
import com.example.attendence_2.utils.Record;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DateViewActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    Record model;
    String classNumber;
    ArrayList<String> present_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_view);
        Intent i = getIntent();
        String d = i.getStringExtra("date");
        firestore = FirebaseFirestore.getInstance();
        getData(d);
    }

    private void getData(String d) {
        DocumentReference reference = firestore.collection("attendence").document(d);

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot.exists()){
                        model = snapshot.toObject(Record.class);
                        ArrayList<AttendenceRecord> records = model.getRecords();
                        Log.d("myApp","todays reord "+records.toString());
                        for(AttendenceRecord obj1:records){
                            Log.d("myApp","standard is "+classNumber);
                            if(obj1.getStandard().equals(classNumber)){
                                Log.d("myApp","inside "+ obj1.getStandard());
                                ArrayList<String> arr = obj1.getPresent_list();
                                present_list = arr;
                            }
//                            getStudentList();
                        }
                    }else{
                        Log.d("myApp","No Todays Record");
                    }
                }else{
                    Log.d("myApp", "get failed with ", task.getException());
                }
            }
        });
    }
}
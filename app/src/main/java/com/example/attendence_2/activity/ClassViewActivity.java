package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.attendence_2.R;
import com.example.attendence_2.adapter.ClassItemAdapter;
import com.example.attendence_2.adapter.StudentItemAdapter;
import com.example.attendence_2.databinding.ActivityClassViewBinding;
import com.example.attendence_2.model.ClassModel;
import com.example.attendence_2.model.TeacherModel;
import com.example.attendence_2.utils.AttendenceRecord;
import com.example.attendence_2.utils.Record;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClassViewActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityClassViewBinding binding;
    String classNumber;
    ClassModel classModel;
    ArrayList<String> present_list;
    FirebaseFirestore firestore;
    Record model;
    String todaysDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_class_view);
        binding = ActivityClassViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        classNumber = intent.getStringExtra("classNumber");
        binding.tvClass.setText("Class "+classNumber);
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        Date d = new Date();
        d.setHours(0);
        todaysDate = formatter.format(d);
        present_list = new ArrayList<>();
        getTodaysData();
//        getStudentList();
        binding.fabStudentList.setOnClickListener(this);
    }

    private void getStudentList() {
        Task<QuerySnapshot> collectionReference = firestore.collection("students").whereEqualTo("standard",classNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
                            Log.d("myApp",snapshot.getDocuments().get(0).toString());
                            classModel = snapshot.getDocuments().get(0).toObject(ClassModel.class);
                            setupRecyclerView();
                        }else{
                            Log.d("myApp","unsuccessful");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("myApp",e.getMessage());
                    }
                });
    }

    private void getTodaysData() {
        Log.d("myApp","getting todays data");
        DocumentReference reference = firestore.collection("attendence").document(todaysDate);
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
                            getStudentList();
                        }
                    }else{
                        Log.d("myApp","No Todays Record");
                        getStudentList();
                    }
                }else{
                    Log.d("myApp", "get failed with ", task.getException());
                }
            }
        });
    }

    private void setupRecyclerView() {
        Log.d("myApp","todays list "+present_list.toString());
        StudentItemAdapter adapter=new StudentItemAdapter(ClassViewActivity.this, classModel.getStudent_list(), present_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvStudentList.setLayoutManager(manager);
        binding.rvStudentList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        DocumentReference reference = firestore.collection("attendence").document(todaysDate);

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot.exists()){
                        Log.d("myApp",snapshot.toString());
                        model = snapshot.toObject(Record.class);
                        ArrayList<AttendenceRecord> records = model.getRecords();
                        int flag=0;
                        Intent i = new Intent(ClassViewActivity.this,MainActivity.class);
                        for(AttendenceRecord obj: records){
                            if(obj.getStandard().equals(classNumber)){
                                obj.setPresent_list(present_list);
                                flag=1;
                            }
                        }
                        if(flag==0){
                            AttendenceRecord obj = new AttendenceRecord(present_list,classNumber);
                            records.add(obj);
                        }
                        model.setRecords(records);
                        saveAttendenceRecord(model);
                        startActivity(i);
                    }else{
                        Log.d("myApp","No document of this date");
                        model = new Record();
                        ArrayList<AttendenceRecord> records = new ArrayList<>();
                        Intent i = new Intent(ClassViewActivity.this,MainActivity.class);
                        startActivity(i);
                        AttendenceRecord obj = new AttendenceRecord(present_list,classNumber);
                        records.add(obj);
                        model.setRecords(records);
                        saveAttendenceRecord(model);
                    }
                }else{
                    Log.d("myApp", "get failed with ", task.getException());
                }
            }
        });
    }

    private void saveAttendenceRecord(Record record){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("attendence").document(todaysDate).set(record)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                     Toast.makeText(this,"Registeration Successful",Toast.LENGTH_SHORT).show();
                        Log.d("myApp","Successfully stored data");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("myApp","Storing Failed");
            }
        });
    }
}
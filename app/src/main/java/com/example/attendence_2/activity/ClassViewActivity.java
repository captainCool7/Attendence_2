package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.attendence_2.R;
import com.example.attendence_2.databinding.ActivityClassViewBinding;
import com.example.attendence_2.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ClassViewActivity extends AppCompatActivity {
    ActivityClassViewBinding binding;
    String classNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_class_view);
        binding = ActivityClassViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        classNumber = intent.getStringExtra("classNumber");
        getStudentList();
//        setupRecyclerView();
    }

    private void getStudentList() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> collectionReference = firestore.collection("students").whereEqualTo("standard",classNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot snapshot = task.getResult();
                            DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
                            Log.d("myApp",snapshot.getDocuments().get(0).toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("myApp",e.getMessage());
                    }
                });
    }

    private void setupRecyclerView() {

    }
}
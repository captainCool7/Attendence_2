package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.attendence_2.adapter.ClassItemAdapter;
import com.example.attendence_2.databinding.ActivityMainBinding;
import com.example.attendence_2.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    TeacherModel model;
    ArrayList<Integer> defaultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.button.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        setUpUserDetail();
//        FirebaseData task = new FirebaseData();
//        task.execute();
//        setUpUserDetail();
    }

    private void setRecyclerView() {
        binding.tvUname.setText("Welcome Back! "+model.getName());
        ClassItemAdapter adapter=new ClassItemAdapter(model.getClass_no(),MainActivity.this);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        binding.rvClass.setLayoutManager(manager);
        binding.rvClass.setAdapter(adapter);
    }

    private void setUpUserDetail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(this,LandingActivity.class));
        }else{
//            binding.tvUname.setText(user.getEmail());
            Log.d("myApp","onStart Method "+user.getEmail());
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            Task<QuerySnapshot> collectionReference = firestore.collection("teachers").whereEqualTo("email",user.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                QuerySnapshot snapshot = task.getResult();
                                DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
//                                Log.d("myApp",snapshot.getDocuments().get(0).toString());
                                model = documentSnapshot.toObject(TeacherModel.class);
                                Log.d("myApp","Value is: "+model.toString());
                                setRecyclerView();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("myApp",e.getMessage());
                        }
                    });
        }
    }

//    private class FirebaseData extends AsyncTask<Void,Void,Void>{
//        TeacherModel teacherModel;
//        @Override
//        protected Void doInBackground(Void... voids) {
//            FirebaseUser user = mAuth.getCurrentUser();
//            if(user==null){
//                startActivity(new Intent(MainActivity.this,LandingActivity.class));
//            }else{
////            binding.tvUname.setText(user.getEmail());
//                Log.d("myApp","onStart Method "+user.getEmail());
//                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//                Task<QuerySnapshot> collectionReference = firestore.collection("teachers").whereEqualTo("email",user.getEmail())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if(task.isSuccessful()){
//                                    QuerySnapshot snapshot = task.getResult();
//                                    DocumentSnapshot documentSnapshot = snapshot.getDocuments().get(0);
//                                    Log.d("myApp",snapshot.getDocuments().get(0).toString());
//                                    teacherModel = documentSnapshot.toObject(TeacherModel.class);
//                                    Log.d("myApp",teacherModel.toString());
////                                    Log.d("myApp","Value is: "+model.toString());/
////                                    setRecyclerView();
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("myApp",e.getMessage());
//                            }
//                        });
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void noargs) {
//            ClassItemAdapter adapter=new ClassItemAdapter(teacherModel.getClass_no(),MainActivity.this);
//            GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
//            binding.rvClass.setLayoutManager(manager);
//            binding.rvClass.setAdapter(adapter);
//        }
//    }

//    @Override
//    public void onClick(View view) {
//        TeacherModel model = getData();
//        db.collection("teachers").document(model.getUserName()).set(model)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();
//                        Log.d("MyApp","Successful");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("MyApp",e.getMessage());
//                    }
//                });
//    }

//    private TeacherModel getData() {
//        String[] classList = binding.etClass.getText().toString().split(" ");
//        ArrayList<Integer> class_no = new ArrayList<>();
//        for(String num:classList){
//            class_no.add(Integer.parseInt(num));
//        }
//        return new TeacherModel(binding.etName.getText().toString(),binding.etUsername.getText().toString(),class_no);
//    }
}
package com.example.attendence_2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.attendence_2.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.button.setOnClickListener(this);
        binding.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,LandingActivity.class));
    }

//    @Override
//    public void onClick(View view) {
//        TeacherModel model = getData();
//        db.collection("teachers").document(model.getUserName()).set(model)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
////                        Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();
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
package com.example.attendence_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendence_2.database.FirebaseStore;
import com.example.attendence_2.databinding.ActivityRegisterBinding;
import com.example.attendence_2.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NAME_REGEX = "/^[a-z ,.'-]+$/i";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*/d)[A-Za-z/d]{6,}$";
    ActivityRegisterBinding binding;
    String name,email,password,u_id;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(this);
        Log.d("myApp","register activity");
    }

    private boolean getData() {
        name = binding.etName.getText().toString();
        email = binding.etEmail.getText().toString().toLowerCase().trim();
        password = binding.etPassword.getText().toString();
        u_id = binding.etUsername.getText().toString();
        Log.d("myApp","In get data");
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.setError("Email address is invalid");
            return false;
        }

        if(name.isEmpty() || name.matches(NAME_REGEX)){
            binding.etName.setError("Please enter proper name");
            return false;
        }

        if(password.isEmpty() || password.matches(PASSWORD_REGEX)){
            binding.etPassword.setError("Must contain a letter and number");
            return false;
        }
        Log.d("myApp",email);
        return true;
    }

    @Override
    public void onClick(View view) {
        Log.d("myApp","Button CLicked");
        if(getData())
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        int result = FirebaseStore.storeTeacherData(new TeacherModel(name,u_id,email));
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(this,"Unable to create user",Toast.LENGTH_SHORT).show();
                        Log.d("myApp",e.getMessage());
                    }
                });
    }
}
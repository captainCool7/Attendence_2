package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.attendence_2.database.FirebaseStore;
import com.example.attendence_2.databinding.ActivityRegisterBinding;
import com.example.attendence_2.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NAME_REGEX = "/^[a-z ,.'-]+$/i";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*/d)[A-Za-z/d]{4,}$";
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
    }

    private boolean getData() {
        name = binding.etName.getText().toString();
        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();
        u_id = binding.etPassword.getText().toString();

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

        return true;
    }

    @Override
    public void onClick(View view) {
        if(getData())
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("myApp","request send");
                            FirebaseStore.storeTeacherData(new TeacherModel(name,u_id,email));
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                    }
                });

    }
}
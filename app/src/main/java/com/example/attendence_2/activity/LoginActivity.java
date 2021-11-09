package com.example.attendence_2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.example.attendence_2.R;
import com.example.attendence_2.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*/d)[A-Za-z/d]{6,}$";
    ActivityLoginBinding binding;
    String email,password;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(this);
    }

    private boolean getData(){
        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.setError("Email address is invalid");
            return false;
        }

        if(password.isEmpty() || password.matches(PASSWORD_REGEX)){
            binding.etPassword.setError("Entered Password is invalid");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(getData()){
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("myApp","Unable to login User");
                        }
                    });
        }
    }
}
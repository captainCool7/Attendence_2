package com.example.attendence_2.database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.attendence_2.model.TeacherModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseStore {
    private static int SUCCESS_CODE = 200;
    private static int FAILED_CODE = 201;
    static int result;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static int storeTeacherData(TeacherModel teacher){
        db.collection("teachers").document(teacher.getU_id()).set(teacher)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
//                     Toast.makeText(this,"Registeration Successful",Toast.LENGTH_SHORT).show();
                    Log.d("myApp","Registration Successful");
                    result = SUCCESS_CODE;
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("myApp","Registration Failed");
                result = FAILED_CODE;
            }
        });
        return result;
    }
}

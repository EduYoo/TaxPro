package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Student student;

    private EditText studentCode_EditText;
    private EditText password_EditText;

    private Button signUp_Btn;
    private Button login_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        student=Student.getInstance();
        student.initializeInfo();

        studentCode_EditText=findViewById(R.id);
        password_EditText=findViewById(R.id);
        signUp_Btn=findViewById(R.id);
        login_Btn=findViewById(R.id);

    }
}
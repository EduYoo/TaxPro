package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.taxpro.firebasefirestore.FireStoreService;
import com.example.taxpro.info.Student;

public class LoginActivity extends AppCompatActivity
{
    private Context context;

    private Student student;

    private EditText studentCode_EditText;
    private EditText password_EditText;

    private Button login_Btn;
    private Button signUp_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getIntent();

        context=this;

        student=Student.getInstance();
        student.initializeInfo();

        studentCode_EditText=findViewById(R.id.loginActivity_edit_StudentCode);
        password_EditText=findViewById(R.id.loginActivity_edit_Password);

        login_Btn=findViewById(R.id.loginActivity_btn_Login);
        signUp_Btn=findViewById(R.id.loginActivity_btn_Signup);

        login_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FireStoreService.Auth.checkStudentCode(context,studentCode_EditText.getText().toString(), password_EditText.getText().toString());
            }
        });

        signUp_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), StudentCodeActivity.class));
            }
        });
    }


}
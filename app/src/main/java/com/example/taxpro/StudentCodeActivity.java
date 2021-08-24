package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentCodeActivity extends AppCompatActivity
{
    private Context context;

    EditText studentCode_EditText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_code);

        getIntent();

        context = getApplicationContext();

        studentCode_EditText=findViewById(R.id.StudentCodeActivity_edit_StudentCode);
        btn=findViewById(R.id.StudentCodeActivity_btn);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FireStoreAPI.Auth.checkStudentCode(context,studentCode_EditText.getText().toString(),null);
            }
        });
    }
}
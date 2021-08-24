package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity
{
    private Student student;

    Button scan_Btn;
    Button work_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getIntent();

        student=Student.getInstance();

        Log.d("???",student.getClassCode());
        Log.d("???",student.getStudentCode());
        Log.d("???",student.getEmail());
        Log.d("???",student.getName());
        Log.d("???",student.getRegion());
        Log.d("???",student.getSchool());
        Log.d("???",student.getGrade());
    }
}
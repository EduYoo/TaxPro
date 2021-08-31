package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener
{
    Context context;

    private Student student;

    Button scan_Btn;
    Button work_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getIntent();

        FireStoreAPI.Class.getClassInfo();

        context=this;

        student=Student.getInstance();



        Log.d("???",student.getClassCode());
        Log.d("???",student.getStudentCode());
        Log.d("???",student.getEmail());
        Log.d("???",student.getName());
        Log.d("???",student.getRegion());
        Log.d("???",student.getSchool());
        Log.d("???",student.getGrade());



        work_Btn=findViewById(R.id.MainScreenActivity_btn_Work);
        work_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(context, WorkActivity_BankTeller.class));
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (student.getJob())
        {
            case "은행원":

                break;
            case "투자회사직원":

                break;
            default:

        }

    }
}
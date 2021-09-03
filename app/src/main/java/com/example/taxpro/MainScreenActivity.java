package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taxpro.firebasefirestore.FireStoreService;
import com.example.taxpro.info.ClassInfo;
import com.example.taxpro.info.Student;
import com.example.taxpro.work.WorkActivity_BankTeller;
import com.example.taxpro.work.WorkActivity_CreditRatingStaff;
import com.example.taxpro.work.WorkActivity_InvestmentCompanyStaff;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context context;

    private ClassInfo classInfo;
    private Student student;

    private TextView job_Text;
    private Button scan_Btn;
    private Button work_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getIntent();

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        FireStoreService.Class.getClassInfo();

        Log.d("???",student.getClassCode());
        Log.d("???",student.getStudentCode());
        Log.d("???",student.getEmail());
        Log.d("???",student.getName());
        Log.d("???",student.getRegion());
        Log.d("???",student.getSchool());
        Log.d("???",student.getGrade());
        Log.d("???",student.getJob());






        job_Text=findViewById(R.id.MainScreenActivity_text_job);
        job_Text.setText(student.getJob());
        work_Btn=findViewById(R.id.MainScreenActivity_btn_Work);
        work_Btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.MainScreenActivity_btn_Work:

                switch (student.getJob())
                {
                    case "은행원":
                        startActivity(new Intent(context, WorkActivity_BankTeller.class));
                        break;
                    case "투자회사직원":
                        startActivity(new Intent(context, WorkActivity_InvestmentCompanyStaff.class));
                        break;
                    case "신용평가위원":
                        ArrayList<String> list=new ArrayList<>(classInfo.getStudentMap().values());
                        startActivity(new Intent(context, WorkActivity_CreditRatingStaff.class).putExtra("studentList",list));
                        break;
                    default:

                }

                break;
            case R.id.MainScreenActivity_btn_Scan:

                break;
            default:

        }

    }
}
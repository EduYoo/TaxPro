package com.example.taxpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class WorkActivity extends AppCompatActivity
{
    private Context context;

    private ClassInfo classInfo;
    private Student student;
    private Saving saving;

    private Button SavingRegistration_Btn;
    private Button savingList_Btn;
    private Button savingClosing_Btn;

    EditText savingAmount_Dialog_Btn;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        getIntent();

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        if (student.getJob()=="은행원")
        {

        }

    }

    void selectSavingDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        int size = classInfo.getListOfSavingProduct().size();
        String[] arrayForSaving= classInfo.getListOfSavingProduct().toArray(new String[size]);

        builder
                .setTitle("예금 상품 선택")
                .setSingleChoiceItems(arrayForSaving, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        saving.setType(arrayForSaving[i]);
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        FireStoreAPI.Bank.getSavingProduct(new FireStoreGetCallback<Double>()
                        {
                            @Override
                            public void callback(Double object)
                            {
                                saving.setRate(object);
                            }
                        }, saving.getType());


                        enterAmountDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "예금 상품 선택 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();
    }

    void enterAmountDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=this.getLayoutInflater();

        View view = inflater.inflate(R.layout,null);

        savingAmount_Dialog_Btn=findViewById(R.id.);

        builder
                .setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int amount=Integer.valueOf(savingAmount_Dialog_Btn.getText().toString());
                        saving.setAmount(amount);

                        enterStudentInfoDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "예금 금액 입력 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();



    }

    void enterStudentInfoDialog()
    {

    }
}
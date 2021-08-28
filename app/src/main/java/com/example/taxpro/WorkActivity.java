package com.example.taxpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener
{
    private Context context;

    private ClassInfo classInfo;
    private Student student;
    private Saving saving;

    private Button savingRegistration_Btn;
    private Button savingList_Btn;
    private Button savingClosing_Btn;

    EditText savingAmount_Dialog_Edit;

    private Spinner numberSpinner;

    private ArrayAdapter<Integer> numberAdapter;


    Integer[] numberArray;
    HashMap<Integer, String> map;

    ArrayList<Saving> savingStateList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        getIntent();

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        savingRegistration_Btn=findViewById(R.id.WorkActivity_btn_SavingRegistration);
        savingList_Btn=findViewById(R.id.WorkActivity_btn_SavingList);
        savingClosing_Btn=findViewById(R.id.WorkActivity_btn_SavingClosing);

        savingRegistration_Btn.setOnClickListener(this);
        savingList_Btn.setOnClickListener(this);
        savingClosing_Btn.setOnClickListener(this);

        FireStoreAPI.Bank.getListOfSavingProduct();


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        classInfo.getListOfSavingProduct().clear();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.WorkActivity_btn_SavingRegistration:
                saving=new Saving();


                selectSavingDialog();
                break;
            case R.id.WorkActivity_btn_SavingList:
                savingStateList= new ArrayList<>();

                FireStoreAPI.Bank.seeSavingState(new FireStoreGetCallback<Saving>()
                {
                    @Override
                    public void callback(Saving object)
                    {
                        Log.d("???!!!",object.toString());
                        savingStateList.add(object);
                    }
                });

                startActivity(new Intent(context, SavingStateActivity.class).putExtra("SavingStateList", savingStateList));

                break;
            case R.id.WorkActivity_btn_SavingClosing:

                break;
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

        View view = inflater.inflate(R.layout.activity_work_bank_dialog_enter_amount,null);

        savingAmount_Dialog_Edit=view.findViewById(R.id.WorkActivity_Dialog_EnterAmount_edit_Amount);

        builder
                .setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int amount=Integer.valueOf(savingAmount_Dialog_Edit.getText().toString());
                        saving.setAmount(amount);

                        enterStudentNumberDialog();
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

    void enterStudentNumberDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_bank_dialog_enter_number,null);

        numberArray=new Integer[classInfo.getTheNumberOfStudent()];


        for (int i=1; i<=classInfo.getTheNumberOfStudent();i++)
        {
            numberArray[i-1]=i;
        }

        numberSpinner=(Spinner) view.findViewById(R.id.WorkActivity_Dialog_EnterInfo_spinner_Number);
        numberAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,numberArray);
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                saving.setNumber(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Toast.makeText(context, "출석 번호를 선택하세요!", Toast.LENGTH_SHORT).show();
            }
        });




        builder.setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Log.d("???",String.valueOf(saving.getNumber()));

                        saving.setRegistrationDate(new Date());
                        saving.setPeriod(30);
                        saving.setTotalTerm(90);
                        saving.setName(classInfo.getStudentMap().get(saving.getNumber()));



                        checkDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "학생 정보 입력 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();

    }

    void checkDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder
                .setTitle("등록하시겠습니까?")
                .setTitle(saving.getName()+" 학생으로 예금 가입을 진행하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        FireStoreAPI.Bank.enrollSaving(context, saving);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "등록 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();

    }

}
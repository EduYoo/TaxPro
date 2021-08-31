package com.example.taxpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WorkActivity_BankTeller extends AppCompatActivity implements View.OnClickListener
{
    private Context context;

    private ClassInfo classInfo;
    private Student student;
    private Saving saving;

    private Button savingRegistration_Btn;
    private Button savingList_Btn;
    private Button savingClosing_Btn;

    private EditText savingAmount_Dialog_Edit;

    private Spinner numberSpinner;

    private ArrayAdapter<Integer> numberAdapter;


    private Integer[] numberArray;
    private HashMap<Integer, String> map;

    private ArrayList<Saving> savingList;
    private ArrayList<Saving> savingClosingList;
    private Calendar calendar;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_bank_teller);


        getIntent();

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();
        calendar=Calendar.getInstance();

        savingRegistration_Btn=findViewById(R.id.WorkActivity_BankTeller_btn_SavingRegistration);
        savingList_Btn=findViewById(R.id.WorkActivity_BankTeller_btn_SavingList);
        savingClosing_Btn=findViewById(R.id.WorkActivity_BankTeller_btn_SavingClosing);

        savingRegistration_Btn.setOnClickListener(this);
        savingList_Btn.setOnClickListener(this);
        savingClosing_Btn.setOnClickListener(this);

        savingList=new ArrayList<>();
        savingClosingList=new ArrayList<>();

        FireStoreAPI.Bank.getListOfSavingProduct();


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        savingList.clear();
        savingClosingList.clear();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        seeSavingState();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        savingList.clear();
        savingClosingList.clear();
        classInfo.getListOfSavingProduct().clear();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.WorkActivity_BankTeller_btn_SavingRegistration:
                saving=new Saving();
                selectSavingDialog();
                break;
            case R.id.WorkActivity_BankTeller_btn_SavingList:
                startActivity(new Intent(context, SavingStateActivity.class).putExtra("savingList",savingList));
                break;
            case R.id.WorkActivity_BankTeller_btn_SavingClosing:
                startActivity(new Intent(context, SavingClosingActivity.class).putExtra("savingList",savingClosingList));
                break;
        }

    }

    private void seeSavingState()
    {
        FireStoreAPI.Bank.seeSavingState(new FireStoreGetCallback<Saving>()
        {
            @Override
            public void callback(Saving object) throws ParseException
            {
                Calendar today=Calendar.getInstance();
                today.setTime(new Date());

                Calendar dueDate=Calendar.getInstance();
                Date date_Due= new SimpleDateFormat("yyyy-MM-dd").parse(object.getDueDate());
                dueDate.setTime(date_Due);

                long dSec=(dueDate.getTimeInMillis()-today.getTimeInMillis())/1000;
                long dDay= dSec/(24*60*60);
                object.setdDay(String.valueOf(dDay));


                if (dDay>0)
                    savingList.add(object);
                if (dDay<=0)
                    savingClosingList.add(object);

            }
        });
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
                        if (saving.getType()==null)
                        {
                            Toast.makeText(context, "예금 상품을 선택해주세요!", Toast.LENGTH_SHORT).show();
                        }
                        else
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

        View view = inflater.inflate(R.layout.activity_work_bank_teller_dialog_enter_amount,null);

        savingAmount_Dialog_Edit=view.findViewById(R.id.WorkActivity_BankTeller_Dialog_EnterAmount_edit_Amount);

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

        View view = inflater.inflate(R.layout.activity_work_bank_teller_dialog_enter_number,null);

        numberArray=new Integer[classInfo.getTheNumberOfStudent()];


        for (int i=1; i<=classInfo.getTheNumberOfStudent();i++)
        {
            numberArray[i-1]=i;
        }

        Log.d("???", classInfo.getStudentMap().toString());

        numberSpinner=(Spinner) view.findViewById(R.id.WorkActivity_BankTeller_Dialog_EnterInfo_spinner_Number);
        numberAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,numberArray);
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                saving.setNumber(adapterView.getItemAtPosition(i).toString());
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


                        calendar.setTime(new Date());
                        calendar.add(Calendar.DATE,90);

                        saving.setCloseOrNot(false);
                        saving.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        saving.setDueDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                        saving.setdDay("90");
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
                        savingList.clear();
                        savingClosingList.clear();
                        FireStoreAPI.Bank.enrollSaving(context, saving);
                        seeSavingState();
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
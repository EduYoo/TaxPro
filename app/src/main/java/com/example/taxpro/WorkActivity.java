package com.example.taxpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

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
    private Spinner nameSpinner;
    private ArrayAdapter<Integer> numberAdapter;
    private ArrayAdapter<String> nameAdapter;

    Integer[] numberArray;
    List<String> nameArray;

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
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.WorkActivity_btn_SavingRegistration:
                FireStoreAPI.Bank.getListOfSavingProduct();
                saving=new Saving();

                selectSavingDialog();
                break;
            case R.id.WorkActivity_btn_SavingList:

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

        View view = inflater.inflate(R.layout.activity_work_dialog_enter_amount,null);

        savingAmount_Dialog_Edit=findViewById(R.id.WorkActivity_Dialog_EnterAmount_edit_Amount);

        builder
                .setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int amount=Integer.valueOf(savingAmount_Dialog_Edit.getText().toString());
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
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_dialog_enter_info,null);

        numberArray=new Integer[classInfo.getTheNumberOfStudent()];
        nameArray=classInfo.getStudentList();

        for (int i=1; i<=classInfo.getTheNumberOfStudent();i++)
        {
            numberArray[i]=i;
        }

        numberSpinner=(Spinner) findViewById(R.id.WorkActivity_Dialog_EnterInfo_spinner_Number);
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

        nameSpinner=(Spinner) findViewById(R.id.WorkActivity_Dialog_EnterInfo_spinner_Name);
        nameAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,nameArray);
        nameSpinner.setAdapter(nameAdapter);
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                saving.setName(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                Toast.makeText(context, "이름을 선택하세요!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
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
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        FireStoreAPI.Bank.enrollSaving(saving);
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
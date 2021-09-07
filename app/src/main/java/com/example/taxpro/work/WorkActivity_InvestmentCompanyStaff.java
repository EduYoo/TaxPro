package com.example.taxpro.work;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taxpro.R;
import com.example.taxpro.firebasefirestore.FireStoreGetCallback;
import com.example.taxpro.firebasefirestore.FireStoreService;
import com.example.taxpro.goods.Goods;
import com.example.taxpro.goods.GoodsLog;
import com.example.taxpro.goods.InvestmentGoods;
import com.example.taxpro.info.ClassInfo;
import com.example.taxpro.info.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorkActivity_InvestmentCompanyStaff extends AppCompatActivity implements View.OnClickListener
{
    private Context context;

    private ClassInfo classInfo;
    private Student student;

    private Button openingMarket_Btn;
    private Button buyingStocks_Btn;
    private Button sellingStocks_Btn;
    private Button closingingMarket_Btn;

    private List<String> time;
    private GoodsLog log;

    private Spinner numberSpinner;

    private ArrayAdapter<Integer> numberAdapter;

    private Integer[] numberArray;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_investment_company_staff);

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        classInfo.getListOfInvestmentGoods().clear();

        FireStoreService.Investment.getOpeningAndClosingTime(new FireStoreGetCallback<List<String>>()
        {
            @Override
            public void callback(List<String> object) throws ParseException
            {
                time=object;
            }
        });

        FireStoreService.Investment.getInvestmentGoods(new FireStoreGetCallback<InvestmentGoods>()
        {
            @Override
            public void callback(InvestmentGoods object) throws ParseException
            {
                classInfo.getListOfInvestmentGoods().add(object);
            }
        });

        openingMarket_Btn=findViewById(R.id.WorkActivity_InvestmentCompanyStaff_btn_OpeningMarket);
        buyingStocks_Btn=findViewById(R.id.WorkActivity_InvestmentCompanyStaff_btn_BuyingStocks);
        sellingStocks_Btn=findViewById(R.id.WorkActivity_InvestmentCompanyStaff_btn_SellingStocks);
        closingingMarket_Btn=findViewById(R.id.WorkActivity_InvestmentCompanyStaff_btn_ClosingingMarket);

        openingMarket_Btn.setOnClickListener(this);
        buyingStocks_Btn.setOnClickListener(this);
        sellingStocks_Btn.setOnClickListener(this);
        closingingMarket_Btn.setOnClickListener(this);
        openingMarket_Btn.setAlpha(0f);
        openingMarket_Btn.setTranslationY(50);
        openingMarket_Btn.animate().alpha(1f).translationYBy(-50).setDuration(1500);
        buyingStocks_Btn.setVisibility(View.GONE);
        sellingStocks_Btn.setVisibility(View.GONE);
        closingingMarket_Btn.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        time.clear();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.WorkActivity_InvestmentCompanyStaff_btn_OpeningMarket:

                Log.d("???",LocalTime.now().toString());
                Log.d("???",time.get(0));
                Log.d("???",time.get(1));
                if (LocalTime.now().isAfter(LocalTime.parse(time.get(0))) && LocalTime.now().isBefore(LocalTime.parse(time.get(1))))
                {
                    openingMarket_Btn.setVisibility(View.GONE);

                    buyingStocks_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(buyingStocks_Btn);
                    sellingStocks_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(sellingStocks_Btn);
                    closingingMarket_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(closingingMarket_Btn);


                }
                else
                {
                    Toast.makeText(context, "주식시장 개장시간이 아닙니다!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.WorkActivity_InvestmentCompanyStaff_btn_BuyingStocks:
                log=new GoodsLog(new InvestmentGoods());
                selectInvestmentGoodsDialog();
                break;
            case R.id.WorkActivity_InvestmentCompanyStaff_btn_SellingStocks:
                break;
            case R.id.WorkActivity_InvestmentCompanyStaff_btn_ClosingingMarket:
                if (LocalTime.now().isAfter(LocalTime.parse(time.get(1))) && LocalTime.now().isBefore(LocalTime.parse(time.get(0))))
                {
                    buyingStocks_Btn.setVisibility(View.GONE);
                    sellingStocks_Btn.setVisibility(View.GONE);
                    closingingMarket_Btn.setVisibility(View.GONE);
                    openingMarket_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(openingMarket_Btn);

                }
                else
                {
                    Toast.makeText(context, "주식시장 마감시간이 아닙니다!", Toast.LENGTH_SHORT).show();
                }


                break;
        }

    }

    void setAnimationOnButton(Button button)
    {
        button.setAlpha(0f);
        button.setTranslationY(50);
        button.animate().alpha(1f).translationYBy(-50).setDuration(1500);
    }

    void selectInvestmentGoodsDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        int size = classInfo.getListOfInvestmentGoods().size();
        String[] arrayForInvestmentGoods= new String[size];

        for (int i=0; i<size;i++)
        {
            arrayForInvestmentGoods[i]=classInfo.getListOfInvestmentGoods().get(i).getName();
        }

        builder
                .setTitle("주식 상품 선택")
                .setSingleChoiceItems(arrayForInvestmentGoods, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        log.getGoods().setName(arrayForInvestmentGoods[i]);
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (log.getGoods().getName()==null)
                        {
                            Toast.makeText(context, "주식 상품을 선택해주세요!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            enterQuantityDialog();
                        }

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "주식 상품 선택 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();
    }

    void enterQuantityDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_investment_company_staff_dialog_enter_quantity,null);

        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.WorkActivity_InvestmentCompanyStaff_Dialog_picker_Quantity);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);
        numberPicker.setWrapSelectorWheel(false);

        builder
                .setView(view)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        ((InvestmentGoods)log.getGoods()).setBuyingQuantity(numberPicker.getValue());
                        enterStudentNumberDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "주식 매수량 입력 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();

    }

    void enterStudentNumberDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_investment_company_staff_dialog_enter_number,null);

        numberArray=new Integer[classInfo.getTheNumberOfStudent()];


        for (int i=1; i<=classInfo.getTheNumberOfStudent();i++)
        {
            numberArray[i-1]=i;
        }

        Log.d("???", classInfo.getStudentMap().toString());

        numberSpinner=(Spinner) view.findViewById(R.id.WorkActivity_InvestmentCompanyStaff_Dialog_EnterInfo_spinner_Number);
        numberAdapter=new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,numberArray);
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                log.setBuyer_number(adapterView.getItemAtPosition(i).toString());
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
                        log.setBuyer_name(classInfo.getStudentMap().get(log.getBuyer_number()));
                        log.setSelling(false);
                        log.setSeller_number(student.getNumber());
                        log.setSeller_name(student.getName());
                        checkDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "입력 취소", Toast.LENGTH_SHORT).show();
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
                .setTitle(log.getBuyer_name()+" 학생이름으로 주식을 매수하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        FireStoreService.Investment.buyInvestmentGoods(log.getGoods().getName(),log);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "매수 취소", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();

    }
}
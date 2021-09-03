package com.example.taxpro.work;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.taxpro.R;
import com.example.taxpro.firebasefirestore.FireStoreGetCallback;
import com.example.taxpro.firebasefirestore.FireStoreService;
import com.example.taxpro.goods.InvestmentGoods;
import com.example.taxpro.info.ClassInfo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WorkActivity_InvestmentCompanyStaff extends AppCompatActivity implements View.OnClickListener
{
    private Context context;

    private ClassInfo classInfo;

    private Button openingMarket_Btn;
    private Button buyingStocks_Btn;
    private Button sellingStocks_Btn;
    private Button closingingMarket_Btn;

    private List<String> time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_investment_company_staff);

        context=this;

        classInfo=ClassInfo.getInstance();
        investmentGoods=new ArrayList<>();

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

                if (LocalTime.now().isAfter(LocalTime.parse(time.get(0))) && LocalTime.now().isBefore(LocalTime.parse(time.get(1))))
                {
                    openingMarket_Btn.setVisibility(View.GONE);

                    buyingStocks_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(buyingStocks_Btn);
                    sellingStocks_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(sellingStocks_Btn);
                    closingingMarket_Btn.setVisibility(View.VISIBLE);
                    setAnimationOnButton(closingingMarket_Btn);

                    Log.d("???", investmentGoods.toString());
                }
                else
                {
                    Toast.makeText(context, "주식시장 개장시간이 아닙니다!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.WorkActivity_InvestmentCompanyStaff_btn_BuyingStocks:
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
        String[] arrayForInvestmentGoods= classInfo.getListOfInvestmentGoods().toArray(new String[size]);

        builder
                .setTitle("주식 상품 선택")
                .setSingleChoiceItems(arrayForInvestmentGoods, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(context, "주식 상품을 선택해주세요!", Toast.LENGTH_SHORT).show();
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
}
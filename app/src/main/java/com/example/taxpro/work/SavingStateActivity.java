package com.example.taxpro.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.taxpro.R;
import com.example.taxpro.account.Saving;

import java.util.ArrayList;

public class SavingStateActivity extends AppCompatActivity
{
    private Context context;
    private ArrayList<Saving> savingList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_state);

        context=this;

        savingList=(ArrayList<Saving>) getIntent().getSerializableExtra("savingList");

        SavingAdapter adapter = new SavingAdapter(context,savingList);

        RecyclerView recyclerView = findViewById(R.id.SavingStateActivity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);



    }


}
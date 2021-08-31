package com.example.taxpro;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class SavingClosingActivity extends AppCompatActivity
{
    private Context context;
    private ArrayList<Saving> savingList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_closing);

        context=this;
        createRecyclerView();
    }

    public void createRecyclerView()
    {
        savingList=(ArrayList<Saving>) getIntent().getSerializableExtra("savingList");

        CustomAdapter adapter = new CustomAdapter(context,savingList);

        RecyclerView recyclerView = findViewById(R.id.SavingClosingActivity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), new  LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(adapter);

    }
}
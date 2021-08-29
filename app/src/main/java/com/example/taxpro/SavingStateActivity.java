package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class SavingStateActivity extends AppCompatActivity
{
    Context context;
    protected ArrayList<String> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_state);

        getIntent();



        context=this;

        SavingAdapter savingAdapter= new SavingAdapter();



        RecyclerView recyclerView = findViewById(R.id.SavingStateActivity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), new  LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(decoration);



        recyclerView.setAdapter(savingAdapter);



    }


}
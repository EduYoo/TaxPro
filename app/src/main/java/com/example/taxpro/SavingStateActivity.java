package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SavingStateActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_state);

        ArrayList<Saving> savingStateList=(ArrayList<Saving>) getIntent().getSerializableExtra("SavingStateList");
    }
}
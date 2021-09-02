package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity
{
    private Context context;

    private EditText email_EditText;
    private EditText password_EditText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntent();

        context=this;

        email_EditText=findViewById(R.id.SignUpActivity_edit_Email);
        password_EditText=findViewById(R.id.SignUpActivity_edit_Password);
        btn=findViewById(R.id.SignUpActivity_btn);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FireStoreService.Auth.signUp(context,email_EditText.getText().toString(),password_EditText.getText().toString());
            }
        });
    }
}
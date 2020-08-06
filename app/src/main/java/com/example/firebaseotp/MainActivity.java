package com.example.firebaseotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = findViewById(R.id.editTextTextPersonName);

    }

    public void phonepass(View view) {
        Intent i = new Intent(MainActivity.this,VerifyPhoneActivity.class) ;
        i.putExtra("phone",edt.getText().toString());
        startActivity(i);

    }
}
package com.example.a108ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

public class ViewLabTests extends AppCompatActivity {
    TextInputLayout lt_name,lt_age,lt_number,lt_date;
    String lt_nam,lt_ag,lt_numbe,lt_dat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lab_tests);
        lt_name = findViewById(R.id.lt_name);
        lt_age = findViewById(R.id.lt_age);
        lt_number = findViewById(R.id.lt_number);
        lt_date = findViewById(R.id.lt_date);

        lt_nam = getIntent().getStringExtra("PatientName");
        lt_ag = getIntent().getStringExtra("PatientAge");
        lt_numbe = getIntent().getStringExtra("PatientNumber");
        lt_dat = getIntent().getStringExtra("PatentDate");

        lt_name.getEditText().setText(lt_nam);
        lt_age.getEditText().setText(lt_ag);
        lt_number.getEditText().setText(lt_numbe);
        lt_date.getEditText().setText(lt_dat);

    }
}
package com.example.a108ambulance;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextInputLayout names,ages,numbers,dates;
    private Button btns,loads;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Test test;
    private ListView listViews;
    final  ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = findViewById(R.id.names);
        ages = findViewById(R.id.ages);
        dates=findViewById(R.id.dates);
        btns = findViewById(R.id.btns);
        numbers=findViewById(R.id.numbers);
        //listViews = findViewById(R.id.listviews);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Lab Test");
        test = new Test();
        loads = findViewById(R.id.loads);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names_holder = names.getEditText().getText().toString();
                String ages_holder = ages.getEditText().getText().toString();
                String numbers_holder = numbers.getEditText().getText().toString();
                String dates_holder = dates.getEditText().getText().toString();
                if(TextUtils.isEmpty(names_holder) && TextUtils.isEmpty(ages_holder)){
                    Toast.makeText(MainActivity.this,"Please add some data",Toast.LENGTH_SHORT).show();
                }else {
                    addDatatoFirebase(names_holder,ages_holder,numbers_holder,dates_holder);

                }
            }
        });
        loads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names_holder = names.getEditText().getText().toString();
                String ages_holder = ages.getEditText().getText().toString();
                String numbers_holder = numbers.getEditText().getText().toString();
                String dates_holder = dates.getEditText().getText().toString();
                getdata(numbers_holder,ages_holder,names_holder,dates_holder);

            }
        });






    }
    private void addDatatoFirebase(String names,String ages,String numbers,String dates)
    {
        test.setAges(ages);
        test.setNames(names);
        test.setNumbers(numbers);
        test.setDates(dates);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                databaseReference.child(numbers).setValue(test);
                Toast.makeText(MainActivity.this,"Test booked",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Test is not booked"+error,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private  void getdata(String numbers,String ages,String names,String dates)
    {
        if(numbers.isEmpty() || ages.isEmpty() || names.isEmpty() || dates.isEmpty()){
            Toast.makeText(MainActivity.this,"Enter the details",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, ViewLabTests.class);
            intent.putExtra("PatientName", names);
            intent.putExtra("PatientAge", ages);
            intent.putExtra("PatientNumber", numbers);
            intent.putExtra("PatentDate", dates);
            startActivity(intent);
        }


        /*
        test.setAges(ages);
        test.setNames(names);
        test.setNumbers(numbers);
        test.setDates(dates);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);


        listViews.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patient").child(numbers);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Test test = dataSnapshot.getValue(Test.class);
                    String txt = test.getNames()+ ":" + test.getAges()+":"+test.getNumbers()+ ":" + test.getDates();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

}

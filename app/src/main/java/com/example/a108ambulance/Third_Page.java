package com.example.a108ambulance;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Third_Page extends AppCompatActivity {
    private TextInputLayout name,age,number,pt_name,pt_age,pt_number;
    private Button btn,load;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Patient patient;
    //private ListView listView;
    //final  ArrayList<String> list = new ArrayList<>();
   // ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);
        name = findViewById(R.id.textField);
        age = findViewById(R.id.text_age);
        btn = findViewById(R.id.btn);
        number=findViewById(R.id.text_number);
       // listView = findViewById(R.id.listview);
        pt_name = findViewById(R.id.pt_name);
        pt_age = findViewById(R.id.pt_age);
        pt_number = findViewById(R.id.pt_number);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Patient");
        patient = new Patient();
        load = findViewById(R.id.load);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_holder = name.getEditText().getText().toString();
                String age_holder = age.getEditText().getText().toString();
                String number_holder = number.getEditText().getText().toString();
                if(TextUtils.isEmpty(name_holder) && TextUtils.isEmpty(age_holder)){
                    Toast.makeText(Third_Page.this,"Please add some data",Toast.LENGTH_SHORT).show();
                }else {
                    addDatatoFirebase(name_holder,age_holder,number_holder);

                }
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_holder = name.getEditText().getText().toString();
                String age_holder = age.getEditText().getText().toString();
                String number_holder = number.getEditText().getText().toString();
                getdata(number_holder,age_holder,name_holder);

            }
        });






    }
    private void addDatatoFirebase(String name,String age,String number)
    {
        patient.setAge(age);
        patient.setName(name);
        patient.setNumber(number);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                databaseReference.child(number).setValue(patient);
                Toast.makeText(Third_Page.this,"Booking Successful",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Third_Page.this,"Can't book"+error,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private  void getdata(String number,String age,String name)
    {
        pt_name.getEditText().setText(name);
        pt_age.getEditText().setText(age);
        pt_number.getEditText().setText(number);

        /*patient.setAge(age);
        patient.setName(name);
        patient.setNumber(number);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);


        listView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patient").child(number);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Patient patient = dataSnapshot.getValue(Patient.class);
                    String txt = patient.getName()+ ":" + patient.getAge()+":"+patient.getNumber();
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



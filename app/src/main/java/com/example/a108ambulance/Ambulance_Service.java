package com.example.a108ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ambulance_Service extends AppCompatActivity {
    ImageView imageView;
    TextInputLayout num,add;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_service);
        imageView = findViewById(R.id.emergency);
        num = findViewById(R.id.number);
        add = findViewById(R.id.address);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ambulance Service Patient details");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num_holder = "+91"+num.getEditText().getText().toString();
                String add_holder = add.getEditText().getText().toString();

                if (TextUtils.isEmpty(num_holder) && TextUtils.isEmpty(add_holder)) {
                    Toast.makeText(Ambulance_Service.this, "Please Enter Number and Address", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(num_holder, add_holder);
                }

            }
        });

    }
    private void addDatatoFirebase(String number, String address) {
        PatientDetails patientDetails = new PatientDetails(number,address);
        String phone_number ="8309295163";
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(number).setValue(patientDetails);
                Toast.makeText(Ambulance_Service.this, "Calling....", Toast.LENGTH_SHORT).show();
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + phone_number));
                startActivity(phone_intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ambulance_Service.this, "Failed to reach you" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
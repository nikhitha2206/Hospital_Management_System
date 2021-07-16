package com.example.a108ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Forgot_password extends AppCompatActivity {
    EditText phone;
    Button next;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        phone = findViewById(R.id.txt_phone);
        next = findViewById(R.id.next_f);
        //progressBar = findViewById(R.id.progressbar);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumber();

            }
        });
    }

    private void verifyPhoneNumber() {
        if(!validateFields()){
            return;
        }
        String phonenumber = phone.getText().toString();

        String phone_number = "+91"+phone.getText().toString();
        Query checkuser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(phone_number);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //progressBar.setVisibility(View.VISIBLE);
                    phone.setError(null);
                    Intent intent = new Intent(Forgot_password.this,RecoverPassword.class);
                    intent.putExtra("phone_number",phonenumber);
                    //intent.putExtra("whatToDo","UpdateData");
                    startActivity(intent);
                    finish();


                }else {
                    //progressBar.setVisibility(View.GONE);
                    phone.setError("User doesnot exists");
                    phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Forgot_password.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private boolean validateFields() {
        String val = phone.getText().toString().trim();

        if(val.isEmpty())
        {
            phone.setError("Field Cannot be Empty");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }
}
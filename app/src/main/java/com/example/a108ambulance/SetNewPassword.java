package com.example.a108ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {
    Button update;
    EditText pass,conf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        update = findViewById(R.id.update_pass);
        pass = findViewById(R.id.newpass);
        conf = findViewById(R.id.confpass);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_new_password();
            }
        });
    }
    public void set_new_password()
    {
        if(!validatePassword() | !validateconfpassword()){
            return;
        }
        String password = pass.getText().toString();
        String get_phone_number = getIntent().getStringExtra("phone_umber");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(get_phone_number).child("password").setValue(password);
        Intent intent = new Intent(SetNewPassword.this,ForgetPasswordSuccessMessage.class);
        startActivity(intent);
        finish();


    }
    private boolean validatePassword()
        {
            String val = pass.getText().toString().trim();
            if(val.isEmpty())
            {
                pass.setError("Field Cannot be Empty");
                return false;
            } else {
                pass.setError(null);
                //reg_password.setErrorEnabled(true);
                return true;
            }
        }
        private boolean validateconfpassword()
        {
            String val = conf.getText().toString().trim();
            String val2 = conf.getText().toString().trim();

            if(val2.isEmpty())
            {
                conf.setError("Field Cannot be Empty");
                return false;
            } else {
                if(val2.equals(val)){
                    conf.setError(null);
                    return true;
                }
                else {
                    conf.setError("Confirm password is not same as password");
                    return false;
                }

            }
        }
}
package com.example.a108ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button login;
    TextView forgot_password,create_account;
    EditText phoneNumber,password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginButton);
        forgot_password = findViewById(R.id.forgot_password);
        phoneNumber = findViewById(R.id.username);
        password = findViewById(R.id.password);
        create_account = findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginclicked();
            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Forgot_password.class);
                startActivity(intent);

            }
        });


    }
    public void loginclicked()
    {
        if(!ValidateFields()){
            return;
        }
        //get data
        String PhoneNumber = phoneNumber.getText().toString().trim();
        String Password = password.getText().toString().trim();

        Query checkuser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNumber").equalTo(PhoneNumber);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phoneNumber.setError(null);
                    String systemPassword = snapshot.child(PhoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(Password)){
                        Intent intent = new Intent(Login.this,Ambulance_Service.class);
                        intent.putExtra("phoneNumber",PhoneNumber);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this,"Password does not match!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this,"No such user exists!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean ValidateFields(){
        String Username = phoneNumber.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(Username.isEmpty()){
            Toast.makeText(Login.this,"Username cant be empty",Toast.LENGTH_SHORT).show();
        }else if(Password.isEmpty()){
            Toast.makeText(Login.this,"Password cant be empty",Toast.LENGTH_SHORT).show();
        }

        return true;
    }

}
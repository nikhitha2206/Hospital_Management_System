package com.example.a108ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    Button signup;
    EditText reg_name,reg_username,reg_password,reg_phoneNo,reg_email,conf;
    RadioGroup radioGroup;
    RadioButton gender;
    TextView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.btn_signup);
        reg_name = findViewById(R.id.name);
        reg_username = findViewById(R.id.username);
        reg_password = findViewById(R.id.Password);
        reg_phoneNo = findViewById(R.id.phone);
        reg_email = findViewById(R.id.email);
        conf = findViewById(R.id.Pass2);
        radioGroup= findViewById(R.id.radio_group);
        account = findViewById(R.id.login);
        //gender = findViewById(radioGroup.getCheckedRadioButtonId());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupUser();

            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);


            }
        });
    }
    private boolean validateFullName()
    {
        String val = reg_name.getText().toString().trim();
        if(val.isEmpty())
        {
            reg_name.setError("Field Cannot be Empty");
            return false;
        }else
        {
            reg_name.setError(null);
           // regname.setErrorEnabled(true);
            return true;
        }
    }
    private boolean validateUserName()
    {
        String val = reg_username.getText().toString().trim();
        String whitespaces = "\\A\\w{4,20}\\z";
        if(val.isEmpty())
        {
            reg_username.setError("Field Cannot be Empty");
            return false;
        }else if(val.length()>=20){
            reg_username.setError("User Name is too large");
            return false;
        }else if(!val.matches(whitespaces)){
            reg_username.setError("White spaces are not allowed");
            return false;
        }
        else
        {
            reg_username.setError(null);
            //regusername.setErrorEnabled(true);
            return true;
        }
    }
    private boolean validateEmail()
    {
        String val = reg_email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty())
        {
            reg_email.setError("Field Cannot be Empty");
            return false;
        }else if(!val.matches(checkEmail)){
            reg_email.setError("Invalid Email");
            return false;
        } else {
            reg_email.setError(null);
            //regemail.setErrorEnabled(true);
            return true;
        }
    }
    private boolean validatePassword()
    {
        String val = reg_password.getText().toString().trim();
        if(val.isEmpty())
        {
            reg_password.setError("Field Cannot be Empty");
            return false;
        } else {
            reg_password.setError(null);
            //reg_password.setErrorEnabled(true);
            return true;
        }
    }
    private boolean validateconfpassword()
    {
        String val = reg_password.getText().toString().trim();
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
    private boolean validateNumber()
    {
        String val = reg_phoneNo.getText().toString().trim();

        if(val.isEmpty())
        {
            reg_phoneNo.setError("Field Cannot be Empty");
            return false;
        } else {
            conf.setError(null);
            return true;
        }
    }
    private boolean validateGender(){
        if(radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(Signup.this,"Please select gender!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public void SignupUser()
    {
        if(!validateFullName() | !validateEmail() | !validatePassword() | !validateUserName() | !validateNumber() | !validateconfpassword() | !validateGender()){
            return;
        }else {
            Validated();
        }

    }
    public void Validated()
    {
        String name = reg_name.getText().toString();
        String email = reg_email.getText().toString();
        String password = reg_password.getText().toString();
        String username = reg_username.getText().toString();
       // String phoneNumber = reg_phoneNo.getText().toString();
        String phoneNumber = reg_phoneNo.getText().toString();
        gender = findViewById(radioGroup.getCheckedRadioButtonId());
        String selected_gender = gender.getText().toString();
        Intent intent = new Intent(Signup.this,OtpVerification.class);
        intent.putExtra("phoneNumber",phoneNumber);
        intent.putExtra("name",name);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        intent.putExtra("gender",selected_gender);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
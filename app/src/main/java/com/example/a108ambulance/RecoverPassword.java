package com.example.a108ambulance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RecoverPassword extends AppCompatActivity {
    private Button verify_f;
    private EditText otp_f;
    private ProgressBar progressBar_f;
    private String Verification_ID;
    private FirebaseAuth auth;
    String phone_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        verify_f = findViewById(R.id.verify_btn_f);
        otp_f = findViewById(R.id.otp_f);
        progressBar_f = findViewById(R.id.progress_bar_f);
        progressBar_f.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();


        phone_f = "+91"+getIntent().getStringExtra("phone_number");




        sendVerificationCode(phone_f);
        verify_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp_f.getText().toString();
                if (code.isEmpty() || code.length()<6) {
                    Toast.makeText(RecoverPassword.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                progressBar_f.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }
    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,
                TimeUnit.SECONDS,RecoverPassword.this,
                mCallback);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Verification_ID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otp_f.setText(code);
                verifyCode(code);
                progressBar_f.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(RecoverPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Verification_ID, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential){
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(RecoverPassword.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RecoverPassword.this, SetNewPassword.class);
                    intent.putExtra("phone_umber", phone_f);
                    startActivity(intent);
                    finish();
                } else {
                    if(task.getException()instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(RecoverPassword.this, "Verification Not Completed,Try again!!", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

}
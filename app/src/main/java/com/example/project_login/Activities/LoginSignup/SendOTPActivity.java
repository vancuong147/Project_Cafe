package com.example.project_login.Activities.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project_login.DTO.User;
import com.example.project_login.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SendOTPActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        final EditText inputMobile=findViewById(R.id.inputMobile);
        final Button buttonGetOTP=findViewById(R.id.getOTP_BTN);
        final ProgressBar progressBar=findViewById(R.id.progressBar);

        inputMobile.setText(getIntent().getStringExtra("mobile_phone"));
        String action=getIntent().getStringExtra("action").trim();
        if(action.equals("signup")){
            user=(User) getIntent().getParcelableExtra("USER_INFO");
            inputMobile.setText(user.getPhone().substring(1));

        }
        mAuth=FirebaseAuth.getInstance();
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendOTPActivity.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                buttonGetOTP.setVisibility(View.INVISIBLE);

                String phoneNum=inputMobile.getText().toString().trim();
                PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNum)
                        .setTimeout(120L,TimeUnit.SECONDS)
                        .setActivity(SendOTPActivity.this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                progressBar.setVisibility(View.GONE);
                                buttonGetOTP.setVisibility(View.VISIBLE);
                                Intent intent=new Intent(getApplicationContext(),VerifyOTPActivity.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("verificationId",s);
                                intent.putExtra("action",action);
                                if(action.equals("signup")){
                                    intent.putExtra("USER_INFO",user);
                                }
                                startActivity(intent);
                            }

                        })
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);

                //Test
//                Intent intent=new Intent(getApplicationContext(),VerifyOTPActivity.class);
//                intent.putExtra("mobile",inputMobile.getText().toString());
//                startActivity(intent);
            }
        });
    }


}
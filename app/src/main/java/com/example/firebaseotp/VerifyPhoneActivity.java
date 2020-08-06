package com.example.firebaseotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {
    String num;
    String mverificationid;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        num = getIntent().getStringExtra("phone");
        firebaseAuth= FirebaseAuth.getInstance();
        Toast.makeText(this, ""+num, Toast.LENGTH_SHORT).show();
        sendVerificationCode(num);
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.e("checkphone","on verification"+code);

            if(code!=null){
                verifyVerficationCode(code);
                Toast.makeText(VerifyPhoneActivity.this, code, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("errorphone","verification failed"+e.getMessage());
            Toast.makeText(VerifyPhoneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            //Toast.makeText(getApplicationContext(), "verification failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mverificationid=s;
        }
    };


    private void verifyVerficationCode(String code) {

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mverificationid,code);
        signinwithcredential(credential);
    }

    private void signinwithcredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.i("code", "task success");

                }else{
                    Toast.makeText(getApplicationContext(), "Task Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
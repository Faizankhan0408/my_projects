package com.faizanfirstproject.letschat.verify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.faizanfirstproject.letschat.R;
import com.faizanfirstproject.letschat.databinding.ActivityOtpverifyBinding;
import com.faizanfirstproject.letschat.userData.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {
  ActivityOtpverifyBinding binding;
  FirebaseAuth auth;

  ProgressDialog dialog;

  String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_otpverify);
        binding=ActivityOtpverifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // to show progress
        dialog=new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        // hiding  default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
//
        auth=FirebaseAuth.getInstance();

        String phoneNumber= getIntent().getStringExtra("phoneNumber");
        binding.OTPHeading.setText("Verify "+phoneNumber);

        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPVerify.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String verifyId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verifyId, forceResendingToken);
                      // after receiving code we dismiss dialog
                        dialog.dismiss();
                        verificationId=verifyId;
                    }
                }).build();
           PhoneAuthProvider.verifyPhoneNumber(options);

//            by this when user set otp here automatically it verify it
           binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
               @Override
               public void onOtpCompleted(String otp) {
                   PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,otp);

                   // here we check that we are able to sign in or not

                   auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                              //starting userprofile activity
                               Intent intent=new Intent(OTPVerify.this, UserProfile.class);
                               startActivity(intent);

                               // it finishes all activities before UserProfile activity
                               finishAffinity();
                           }
                           else{
                               Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           });
    }
}
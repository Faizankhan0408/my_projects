package com.faizanfirstproject.letschat.verify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.faizanfirstproject.letschat.R;
import com.faizanfirstproject.letschat.databinding.ActivityTalkingNumberBinding;

import java.util.Objects;

public class talkingNumber extends AppCompatActivity {
    ActivityTalkingNumberBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_talking_number);
        binding=ActivityTalkingNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//      here we are hiding our default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // enabling keypad in this
        binding.verifyPhoneNumber.requestFocus();

//
        binding.verifyContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(talkingNumber.this,OTPVerify.class);
                intent.putExtra("phoneNumber",binding.verifyPhoneNumber.getText().toString());
                startActivity(intent);
            }
        });
    }
}
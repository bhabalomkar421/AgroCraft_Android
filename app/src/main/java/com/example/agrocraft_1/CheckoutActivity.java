package com.example.agrocraft_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Bundle bundle = getIntent().getExtras();

        String userId = bundle.getString("userId") != null ? bundle.getString("userId") : "none" ;
        TextView text = findViewById(R.id.status);
        text.setText("user id : " +userId);
    }
}
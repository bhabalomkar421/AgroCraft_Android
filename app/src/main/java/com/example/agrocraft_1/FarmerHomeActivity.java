package com.example.agrocraft_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerHomeActivity extends AppCompatActivity {

    private TextView helloUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
private Button addProduct,myProfile,myproduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);
        helloUser=findViewById(R.id.textView2);
        addProduct=findViewById(R.id.add_product);
        myProfile=findViewById(R.id.my_profile);
        myproduct=findViewById(R.id.my_product);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String farmerid=currentFirebaseUser.getUid();

        DatabaseReference myRef = database.getReference().child("Farmer").child("Users").child(farmerid);



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value
            helloUser.setText("Welcome "+dataSnapshot.child("fullName").getValue());

            Log.d("getkey","s"+dataSnapshot.child("fullName").getValue());



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("R", "Failed to read value.", error.toException());
            }



        });


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FarmerHomeActivity.this,FarmerAddProduct.class);

                startActivity(intent);

            }
    });
        myproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FarmerHomeActivity.this,FarmerMyProduct.class);

                startActivity(intent);

            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerHomeActivity.this, my_profile2.class);

                startActivity(intent);
                finish();

            }
        });
}
}
package com.example.agrocraft_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class my_profile extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//TextView farmer_name,farmer_phonenumber,farmer_address,farmer_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
//farmer_phonenumber=findViewById(R.id.phone);
//        farmer_name=findViewById(R.id.name);
//        farmer_address=findViewById(R.id.email);
//        farmer_email=findViewById(R.id.address);
//
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        String farmerid=currentFirebaseUser.getUid();
//        DatabaseReference myRef = database.getReference().child("Farmer").child("Users").child(farmerid);

//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value
////                farmer_name.setText("Welcome "+dataSnapshot.child("fullName").getValue());
////                farmer_address.setText("Welcome "+dataSnapshot.child("address").getValue());
////                farmer_email.setText("Welcome "+dataSnapshot.child("email").getValue());
////                farmer_phonenumber.setText("Welcome "+dataSnapshot.child("phone").getValue());
//
//                Log.d("getkey","s"+dataSnapshot.child("fullName").getValue());
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("R", "Failed to read value.", error.toException());
//            }
//
//
//
//        });





    }
}
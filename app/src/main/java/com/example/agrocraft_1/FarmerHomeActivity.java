package com.example.agrocraft_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);
        helloUser=findViewById(R.id.textView2);

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


//        Toast.makeText(this,""+list,Toast.LENGTH_SHORT).show();
//Toast.makeText(this,""+value,Toast.LENGTH_SHORT).show();
    }
}
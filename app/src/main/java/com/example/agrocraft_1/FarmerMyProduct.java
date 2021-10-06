package com.example.agrocraft_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerMyProduct extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
RecyclerView mRecyclerView;
    private com.example.agrocraft_1.AdapterClass adapterclass;
ArrayList<FarmerProducts> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("s","s");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_my_product);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String farmerid=currentFirebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child("Farmer").child("Users").child(farmerid).child("products");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value
//                farmer_name.setText("Welcome "+dataSnapshot.child("fullName").getValue());
//                farmer_address.setText("Welcome "+dataSnapshot.child("address").getValue());
//                farmer_email.setText("Welcome "+dataSnapshot.child("email").getValue());
//                farmer_phonenumber.setText("Welcome "+dataSnapshot.child("phone").getValue());
//
//                Log.d("getkey","s"+dataSnapshot.child("fullName").getValue());

//Log.d("childs","a"+dataSnapshot.getValue());
//Log.d("datachildren",""+dataSnapshot.getChildren());
//                Log.d("snap","a"+dataSnapshot);
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String batchname=datas.child("Product_id").getValue().toString();
                    Log.d("products",batchname);


                    DatabaseReference myRef2 = database.getReference().child("Products").child(batchname);
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("snapshots",""+snapshot.child("name").getValue()+" "+snapshot.child("price").getValue());
//                            Log.d("hi","hi");

                            data.add(new FarmerProducts(""+snapshot.child("name").getValue(),""+snapshot.child("price").getValue(),""+snapshot.child("quantity").getValue(),""+snapshot.child("Url").getValue()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("error","error");


                        }
                    });





                    AdapterClass adapter= new AdapterClass(data);

                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager( FarmerMyProduct.this, RecyclerView.HORIZONTAL,  false));

//                    adapterclass = new AdapterClass(FarmerMyProduct.this, data);
//                    mRecyclerView.setAdapter(adapterclass);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error","error");

            }
        });
}}
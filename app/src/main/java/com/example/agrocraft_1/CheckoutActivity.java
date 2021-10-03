package com.example.agrocraft_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckoutActivity extends AppCompatActivity {
    private DatabaseReference myCartDatabase, myUsersDatabase, orderDatabase;
    private FirebaseAuth mAuth;
    String userId, result = "\n\n\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        TextView text = findViewById(R.id.status);
        TextView product = findViewById(R.id.product_list);
        Button payment = (Button) findViewById(R.id.payment);


        myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Buyer").child("Users");
        myCartDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userId);

        myUsersDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("fullName").getValue().toString();
                    text.setText("Hello, " + name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myCartDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    result = result + snapshot.child("name").getValue().toString() + "                        " + "Rs. " + snapshot.child("price").getValue().toString() + "/kg." + "\n\n";
                }
                result += "\n\n\n";
                product.setText(result);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add order
                myCartDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            orderDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId).child(snapshot.child("post_id").getValue().toString());
                            orderDatabase.child("quantity").setValue("11");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                // delete from cart
                myCartDatabase.removeValue();

//                Toast.makeText(this, "Your Order has been placed sucessfully" ,Toast.LENGTH_SHORT).show();
                toast("Your Order has been placed sucessfully");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redirect();
                    }
                }, 4000);
            }
        });

    }

    private void toast(String s) {
        Toast.makeText(this, "Message: "+s, Toast.LENGTH_LONG).show();
    }

    private void redirect(){
        startActivity(new Intent(this, HomeActivity.class));
    }
}
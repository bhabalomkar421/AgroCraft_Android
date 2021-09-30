package com.example.agrocraft_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FarmerAddProduct extends AppCompatActivity {
EditText product_name,product_category,product_price,product_quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_add_product);

        product_name=findViewById(R.id.product_name);
        product_category=findViewById(R.id.product_category);
        product_quantity=findViewById(R.id.product_quantity);
        product_price=findViewById(R.id.product_price);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String farmerid=currentFirebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child("Farmer").child("Users").child(farmerid);

    }
}






//    private void sendProductToCart(final View view, final String name, String qty, String price, String poster, String postId) {
//        String newCartID=myCartDatabase.child(userId).push().getKey();
//        DatabaseReference newCart=myCartDatabase.child(userId).child(newCartID);
//        DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss ");
//        Date date=new Date();
//        String tim=dateFormat.format(date);
//        HashMap<String,Object> myMap=new HashMap<>();
//        myMap.put("name",name);
//        myMap.put("qty",qty);
//        myMap.put("price",price);
//        myMap.put("poster_id",poster);
//        myMap.put("post_id",postId);
//        myMap.put("time",tim);
//        myMap.put("post_image",image);
//
//        newCart.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                {
//                    Snackbar.make(view, "You added "+name+" to your cart. ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            }
//        });


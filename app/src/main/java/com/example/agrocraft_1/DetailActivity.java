package com.example.agrocraft_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private String name,qty,price,image,poster,time,postId,category;
    private TextView tvDetailTitle,tvDetailQty,tvDetailCategory,tvDetailPoster,tvDetailPrice,tvDetailTime,tvPosterPhone;
    private ImageView ivImage;
    private DatabaseReference myUsersDatabase;
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference myProductsDatabase,myCartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      tvDetailTitle=findViewById(R.id.tvDetailTitle);
        tvDetailQty=findViewById(R.id.tvDetailQty);
        tvDetailCategory=findViewById(R.id.tvDetailCategory);
        tvDetailPoster=findViewById(R.id.tvDetailPoster);
        tvDetailPrice=findViewById(R.id.tvDetailPrice);
        tvDetailTime=findViewById(R.id.tvDetailTime);
        tvPosterPhone=findViewById(R.id.tvDetailPosterPhone);

        mAuth=FirebaseAuth.getInstance();
        myCartDatabase= FirebaseDatabase.getInstance().getReference().child("Cart");

        myUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Buyer").child("Users");
        myProductsDatabase= FirebaseDatabase.getInstance().getReference().child("Products");



        userId=mAuth.getCurrentUser().getUid();
        ivImage=findViewById(R.id.ivDetailImage);

        name=getIntent().getStringExtra("name");
        qty=getIntent().getStringExtra("qty");
        price=getIntent().getStringExtra("price");
        image=getIntent().getStringExtra("image");
        poster=getIntent().getStringExtra("poster");
        time=getIntent().getStringExtra("time");
        postId=getIntent().getStringExtra("postId");
        category=getIntent().getStringExtra("category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        tvDetailTitle.setText(""+name);
        tvDetailQty.setText(qty+" Kgs ");
        tvDetailPrice.setText("Ksh: "+price);
        tvDetailTime.setText("Uploaded at "+time);
        tvDetailCategory.setText(category);
        Picasso.get().load(image).placeholder(R.drawable.ic_photo_library_black_24dp).into(ivImage);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendProductToCart(view,name,qty,price,poster,postId);
            }
        });
        setupUploader();

    }

    private void sendProductToCart(final View view, final String name, String qty, String price, String poster, String postId) {
        String newCartID=myCartDatabase.child(userId).push().getKey();
        DatabaseReference newCart=myCartDatabase.child(userId).child(newCartID);
        DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss ");
        Date date=new Date();
        String tim=dateFormat.format(date);
        HashMap<String,Object> myMap=new HashMap<>();
        myMap.put("name",name);
        myMap.put("qty",qty);
        myMap.put("price",price);
        myMap.put("poster_id",poster);
        myMap.put("post_id",postId);
        myMap.put("time",tim);
        myMap.put("post_image",image);

        newCart.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Snackbar.make(view, "You added "+name+" to your cart. ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


    }

    private void setupUploader() {
        myUsersDatabase.child(poster).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    String name=dataSnapshot.child("fullName").getValue().toString();
                    String phone=dataSnapshot.child("phone").getValue().toString();

                    tvDetailPoster.setText("Product Sold  By: "+name);
                    tvPosterPhone.setText(phone);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}

package com.example.agrocraft_1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FarmerAddProduct extends AppCompatActivity {

    EditText product_name,product_category,product_price,product_quantity;
    Button upload,submit;
    ImageView imageView;
    Uri filePath;
    Uri downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_add_product);

        product_name=findViewById(R.id.product_name);
        product_category=findViewById(R.id.product_category);
        product_quantity=findViewById(R.id.product_quantity);
        product_price=findViewById(R.id.product_price);
        upload=findViewById(R.id.upload);
        submit=findViewById(R.id.submit);
        imageView=findViewById(R.id.imageView4);





//Function to select image  when users clicks upload button
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

//Function to add data when users clicks on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String product=product_name.getText().toString();
                String category=product_category.getText().toString();
                String price=product_price.getText().toString();
                String quantity =product_quantity.getText().toString();
//String price="1";
//String quantity ="1";
                addDetails( product , category, price, quantity);
                product_name.setText("");
                product_category.setText("");
                product_price.setText("");
                product_quantity.setText("");

            }
        });

    }

    //function which makes the ImageView change when users has select an image
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        filePath = data.getData();

                        try {

                            // Setting image on image view using Bitmap
                            Bitmap bitmap = MediaStore
                                    .Images
                                    .Media
                                    .getBitmap(
                                            getContentResolver(),
                                            filePath);
                            imageView.setImageBitmap(bitmap);
                            uploadImage();

                        }

                        catch (IOException e) {

                            Log.d("check","error");
                            e.printStackTrace();
                        }
                    }
                }
            });

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    private void addDetails(String product, String category, String price, String quantity){
        if(downloadUrl!=null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            String farmerid=currentFirebaseUser.getUid();
            String productid=database.getReference().child("Products").push().getKey();
            Log.d("product",""+productid);
            DatabaseReference myRef = database.getReference().child("Products").child(productid);



if(product!=null && category!=null && price!=null && quantity!=null){
            HashMap<String,Object> myMap=new HashMap<>();
            myMap.put("name",product);
            myMap.put("category",category);
            myMap.put("price",price);
            myMap.put("quantity",quantity);
            myMap.put("FarmerId",farmerid);
            myMap.put("Url",downloadUrl.toString());

            myRef.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
//                        Log.d("add","Success");
                        Toast.makeText(FarmerAddProduct.this,"PRODUCT ADDED",Toast.LENGTH_SHORT).show();
                    }
                }
            });
//Object obj_productid=productid;
            //add product id to farmers collection
    String myRef2 = database.getReference().child("Farmer").child("Users").child(farmerid).child("products").push().getKey();
    DatabaseReference myReference = database.getReference().child("Farmer").child("Users").child(farmerid).child("products").child(myRef2);
    HashMap<String,Object> myMap2=new HashMap<>();
    myMap2.put("Product_id",productid);
//    List<String>products_lists=new ArrayList<>();
//    products_lists.add(productid);
    myReference.updateChildren(myMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
            {
                Log.d("add","Success");
//                Toast.makeText(FarmerAddProduct.this,"PRODUCT ADDED",Toast.LENGTH_SHORT).show();
            }
        }
    });





        }else{
    Toast.makeText(FarmerAddProduct.this,"PLEASE ADD ALL DETAILS",Toast.LENGTH_SHORT).show();

}
        }else{
            Toast.makeText(FarmerAddProduct.this,"PLEASE ADD ALL DETAILS",Toast.LENGTH_SHORT).show();

        }

    }

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Defining the child of storageReference
            StorageReference ref =storageRef.child("images/" + UUID.randomUUID().toString());
//StorageReference storageRef = storage.getReference();

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                   ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           downloadUrl = uri;
                                                                                           //Do what you want with the url
//                                           Toast.makeText(FarmerAddProduct.this,""+downloadUrl,Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(FarmerAddProduct.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(FarmerAddProduct.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });

        }
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
//        String newCartID=myCartDatabase.child(userId).push().getKey();
//        DatabaseReference newCart=myCartDatabase.child(userId).child(newCartID);

//        private DatabaseReference journalCloudEndPoint;
//        private DatabaseReference tagCloudEndPoint;    Task<Uri> downloadUrl;


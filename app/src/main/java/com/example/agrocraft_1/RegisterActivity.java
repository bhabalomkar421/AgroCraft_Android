package com.example.agrocraft_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName,etAdmNo,etPass,etPassOne,phone, address;
    private Button btnRegister;
    private DatabaseReference myUsersDatabase;
    private FirebaseAuth mAuth;
    private String userId;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etAdmNo=findViewById(R.id.etAdm);
        etPass=findViewById(R.id.etPassword);
        etPassOne=findViewById(R.id.etPassword);
        fullName=findViewById(R.id.fullname);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);

        pd=new ProgressDialog(this);
        pd.setTitle("Creating Account");
        pd.setMessage("Just a moment...");
        mAuth=FirebaseAuth.getInstance();
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                String adm=etAdmNo.getText().toString().trim();
                String pass=etPass.getText().toString().trim();
                String pass1=etPassOne.getText().toString().trim();
                String full_Name = fullName.getText().toString().trim();
                String phone_no = phone.getText().toString().trim();
                String buyer_address = address.getText().toString().trim();
                if (!TextUtils.isEmpty(adm) && !TextUtils.isEmpty(pass) &&!TextUtils.isEmpty(pass1))
                {
                    if (pass.equals(pass1)) {
                        //toast("Data ready for upload....");
                        mAuth.createUserWithEmailAndPassword(adm,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    toast("Account created Successfully, Login to verify");
                                    userId=mAuth.getCurrentUser().getUid();
                                    myUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Buyer").child("Users").child(userId);
                                    myUsersDatabase.child("fullName").setValue(full_Name);
                                    myUsersDatabase.child("address").setValue(buyer_address);
                                    myUsersDatabase.child("phone").setValue(phone_no);
                                    myUsersDatabase.child("email").setValue(adm);

                                    pd.dismiss();
                                    mAuth.signOut();
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    finish();
                                }else {
                                    pd.dismiss();
                                    toast(task.getException().getMessage());
                                }
                            }
                        });



                    }else {
                        pd.dismiss();

                        toast("Passwords dont match....");
                    }
                }else {
                    pd.dismiss();

                    toast("You left a blank !");
                }
            }
        });
    }

    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
    private void toast(String s) {
        Toast.makeText(this, "Message: "+s, Toast.LENGTH_SHORT).show();
    }

}

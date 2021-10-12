package com.example.agrocraft_1;

import android.util.Log;

public class FarmerProducts {

    String productname, productprice, productquantity, productimage;


    public FarmerProducts(String productname, String productprice, String productquantity, String productimage) {
        this.productname = productname;
        this.productprice = productprice;
        this.productquantity = productquantity;
        this.productimage = productimage;

        Log.d("construct",""+productimage);
    }
}
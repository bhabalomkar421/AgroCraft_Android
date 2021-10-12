package com.example.agrocraft_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {
    private final ArrayList<FarmerProducts> data;
    private Context context;

    public AdapterClass(ArrayList<FarmerProducts> data) {
        this.data = data;
//        this.context=context;Context context,
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_products, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap mIcon_val = null;

        holder.productname.setText(data.get(position).productname);
        holder.productprice.setText("Rs "+data.get(position).productprice);
        holder.productquantity.setText("Quant " +data.get(position).productquantity);
//        Picasso.get().load(data.get(position).getAndroid_image_url()).resize(120, 60).into(holder.ivPicture);
        Picasso.get().load(data.get(position).productimage).resize(100, 100).into(holder.ivPicture);

//        holder.ivPicture.setImageBitmap(getBitmapFromURL());
//        URL newurl = null;
//        try {
//            newurl = new URL(data.get(position).productimage);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        holder.ivPicture.setImageBitmap(mIcon_val);
//        Picasso.get().load("https://<image-url>").into(imageView);

    }

    @Override
    public int getItemCount() {
         return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView productname, productprice,productquantity;
    ImageView ivPicture;
    public ViewHolder( View itemView) {
        super(itemView);
//        name = itemView.findViewById(R.id.item_name);
//        age = itemView.findViewById(R.id.item_age);
        productname=itemView.findViewById(R.id.productname);
        productprice=itemView.findViewById(R.id.productprice);
        productquantity=itemView.findViewById(R.id.productquantity);
        ivPicture=itemView.findViewById(R.id.ivPicture);
    }
}
}


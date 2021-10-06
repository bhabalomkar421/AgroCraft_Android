package com.example.agrocraft_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.productname.setText(data.get(position).productname);
        holder.productprice.setText(data.get(position).productprice);
        holder.productquantity.setText(data.get(position).productquantity);

    }

    @Override
    public int getItemCount() {
         return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView productname, productprice,productquantity;
    public ViewHolder( View itemView) {
        super(itemView);
//        name = itemView.findViewById(R.id.item_name);
//        age = itemView.findViewById(R.id.item_age);
        productname=itemView.findViewById(R.id.productname);
        productprice=itemView.findViewById(R.id.productprice);
        productquantity=itemView.findViewById(R.id.productquantity);
    }
}
}


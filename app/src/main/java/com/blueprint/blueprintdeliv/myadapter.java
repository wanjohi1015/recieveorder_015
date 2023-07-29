package com.blueprint.blueprintdeliv;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    List<model> datalist;

    public myadapter( List<model> datalist) {

        this.datalist = datalist;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem_row,parent,false);

        return new myviewholder(view);

    }

    static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title,quantity,price;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            title=itemView.findViewById(R.id.title);



        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {


        model modell = datalist.get(position);


            holder.price.setText(modell.getPrice() + "");
            holder.quantity.setText(modell.getQuantity() + "");
            holder.title.setText(modell.getTitle());





    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }



}

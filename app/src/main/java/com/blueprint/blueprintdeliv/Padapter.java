package com.blueprint.blueprintdeliv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Padapter extends RecyclerView.Adapter<Padapter.myviewholder>

{

        Context PmCtx;
        List<Pmodel> Pdatalist;

    public Padapter(Context PmCtx, List<Pmodel> Pdatalist) {
        this.PmCtx = PmCtx;
        this.Pdatalist = Pdatalist;
    }

    @NonNull
    @Override
    public Padapter.myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new Padapter.myviewholder(LayoutInflater.from(PmCtx).inflate(R.layout.activity_psinglerow, parent, false));

    }
    static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title,price,description;
        CircleImageView circleImageView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.t2);
            title=itemView.findViewById(R.id.t1);
            description=itemView.findViewById(R.id.t4);
            circleImageView=itemView.findViewById(R.id.t5);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull Padapter.myviewholder holder, int position) {


        holder.title.setText(Pdatalist.get(position).getTitle());

        holder.price.setText(Pdatalist.get(position).getPrice()+"");
        holder.description.setText(Pdatalist.get(position).getDescription());

        Glide.with(holder.itemView.getContext()).load(Pdatalist.get(position).getImageUrl()).centerCrop().into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {

            return Pdatalist.size();

    }
}

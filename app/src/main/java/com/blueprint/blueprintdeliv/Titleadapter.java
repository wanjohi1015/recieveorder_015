package com.blueprint.blueprintdeliv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Titleadapter extends RecyclerView.Adapter<Titleadapter.titleviewholder>{

    Context context;
     List<Titlemodel> titleArrayList;
    List<model> datalist = new ArrayList<>();

    myadapter myadapter;

    public Titleadapter( Context context,  List<Titlemodel> titleArrayList){

    this.titleArrayList = titleArrayList;

    this.context = context;


    }


    @NonNull
    @Override
    public titleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item,parent,false);


        return new titleviewholder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull Titleadapter.titleviewholder holder, int position) {

        Titlemodel titlemodell = titleArrayList.get(position);

        holder.number.setText(titlemodell.getPhoneNumber());
        holder.date.setText(titlemodell.getTimestamp()+"");
        holder.totalprice.setText(titlemodell.getTotalprice()+"");
        holder.items.setText(titlemodell.getItems()+"");
        datalist = new ArrayList<>();
        List<model> datalist = titlemodell.getTitleItem();

        myadapter = new myadapter(datalist);
        holder.nested_rv.setLayoutManager(new LinearLayoutManager(context));
        holder.nested_rv.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();









    }

    public class titleviewholder extends RecyclerView.ViewHolder {


        TextView number, date,totalprice,items;
        RecyclerView nested_rv;

        public titleviewholder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            date = itemView.findViewById(R.id.date);
            items = itemView.findViewById(R.id.items);

            totalprice = itemView.findViewById(R.id.totalprice);
            nested_rv = itemView.findViewById(R.id.nested_rv);

        }
    }



    @Override
    public int getItemCount() {
        return titleArrayList.size();
    }





}

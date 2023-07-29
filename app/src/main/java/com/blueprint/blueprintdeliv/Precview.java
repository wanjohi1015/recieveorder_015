package com.blueprint.blueprintdeliv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Precview extends Fragment {
    RecyclerView Precview;
    ArrayList<Pmodel> Pdatalist;
    FirebaseFirestore db;
    Padapter Padapter;
    View v;




    public Precview() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.precview,parent,false);
        Precview = v.findViewById(R.id.precview);
        Precview.setLayoutManager(new LinearLayoutManager(getContext()));
        Padapter = new Padapter(getActivity(), Pdatalist);
        Precview.setAdapter(Padapter);

        return v;




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        db = FirebaseFirestore.getInstance();
        Pdatalist = new ArrayList<>();
        CollectionReference orders = db.collection("Products");
        Query orderss = orders.orderBy("title", Query.Direction.DESCENDING);


        orderss.get().addOnSuccessListener(queryDocumentSnapshots -> {


            if (!queryDocumentSnapshots.isEmpty()) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                for (DocumentSnapshot d : list) {
                    Pmodel obj = d.toObject(Pmodel.class);
                    Pdatalist.add(obj);

                }
                Padapter.notifyDataSetChanged();



            }

        });


    }
}
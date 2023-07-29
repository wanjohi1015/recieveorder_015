package com.blueprint.blueprintdeliv;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ordersgraph extends Fragment {
    View vi;
    FirebaseFirestore db;
    CollectionReference child;
    List<model> datalist ;
    GraphView graphView;
    DataPoint [] dp;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");
    Calendar calendar;



    public Ordersgraph() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.ordersgraph, parent, false);
        graphView = (GraphView)vi.findViewById(R.id.idGraphView);





        return vi;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datalist = new ArrayList<>();
         calendar = Calendar.getInstance();
        db = FirebaseFirestore.getInstance();
        child = db.collection("Orders");
        child.orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                assert value != null;
               dp = new DataPoint[(int)value.size()];
                int index = 0;

                for (DocumentSnapshot lcn : value.getDocuments()) {
                    Titlemodel mch = lcn.toObject(Titlemodel.class);

                    assert mch != null;



                    graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if(isValueX){
                                return simpleDateFormat.format(new Date((long)value));
                            }else {

                                return super.formatLabel(value, false);
                            }
                        }
                    });
                    dp[index] = new DataPoint(mch.getTimestamp(),mch.getTotalprice());
                    index++;


                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                graphView.addSeries(series);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(7);
                series.setThickness(4);
                graphView.setTitle("Order Sales");
                graphView.setTitleTextSize(50);
                graphView.setTitleColor(Color.RED);
                graphView.setVisibility(View.VISIBLE);
                graphView.getViewport().setScalable(true);

                graphView.getViewport().setScrollable(true);

                series.setColor(Color.rgb(0,80,100));
                series.setTitle("Orders Progress");
                graphView.getLegendRenderer().setVisible(true);
                graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


                GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Date");
                gridLabel.setVerticalAxisTitle("Amount in Ksh");


            }
        });
    }



    }

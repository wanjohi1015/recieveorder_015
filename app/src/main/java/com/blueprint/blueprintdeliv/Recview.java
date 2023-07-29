package com.blueprint.blueprintdeliv;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Recview extends Fragment {
    RecyclerView recview;
    List<Titlemodel> titleArrayList;
    ArrayList<model> datalist;
    Button report;
    FirebaseFirestore db;
    View vi;
    Titleadapter adapter;
    Calendar calendar;
    Locale id = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", id);
    @ServerTimestamp Date date_minimal,date_maximal;
    EditText input_minimal, input_maximal;
    String input1;
    String input2;
    int addsales,ordersales,quantity,addquantities,index;
    Button btn_minimal,btn_maximal;
    CollectionReference child;
    TextView totalsales,totalquantities;
    List<Integer> sales = new ArrayList<>();
    List<Integer> nquantities = new ArrayList<>();
    ImageView cancel;
    Titlemodel titlemodell;




    public Recview() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        vi = inflater.inflate(R.layout.recview, parent, false);
        calendar = Calendar.getInstance();
        recview = vi.findViewById(R.id.recview);
        recview.setNestedScrollingEnabled(true);
        cancel = (ImageView)vi.findViewById(R.id.btn_cancel);
        totalsales = (TextView)vi.findViewById(R.id.sales);
        totalsales.setTypeface(null, Typeface.BOLD);
        report = (Button)vi.findViewById(R.id.showreport);
        input_minimal = vi.findViewById(R.id.input_minimal);
        input_maximal = vi.findViewById(R.id.input_maximal);
        btn_minimal = (Button)vi.findViewById(R.id.btn_minimal);
        btn_maximal = (Button)vi.findViewById(R.id.btn_maximal);




        btn_minimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        input_minimal.setText(simpleDateFormat.format(calendar.getTime()));
                        date_minimal = calendar.getTime();

                         input1 = input_minimal.getText().toString();
                         input2 = input_maximal.getText().toString();
                        report.setEnabled(!input1.isEmpty() || !input2.isEmpty());
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btn_maximal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        input_maximal.setText(simpleDateFormat.format(calendar.getTime()));
                        date_maximal = calendar.getTime();


                         input1 = input_maximal.getText().toString();
                         input2 = input_minimal.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()){
                            report.setEnabled(false);
                        }else {
                            report.setEnabled(true);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               reportdata();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_maximal.setEnabled(true);
                input_minimal.setEnabled(true);
                input_maximal.getText().clear();
                input_minimal.getText().clear();
                requireActivity().recreate();



            }

        });





        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new Titleadapter( getContext(), titleArrayList);
        recview.setLayoutManager(layoutManager);
        recview.setAdapter(adapter);




        return vi;




    }

    private void reportdata() {
        db = FirebaseFirestore.getInstance();
        child = db.collection("Orders");
        child.orderBy("timestamp").startAt(date_minimal).endAt(date_maximal).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        assert value != null;
                        titleArrayList.clear();
                        for (DocumentSnapshot lcn : value.getDocuments()) {
                            Titlemodel mch = lcn.toObject(Titlemodel.class);
                            assert mch != null;
                            ordersales = mch.getTotalprice();
                            sales.add(ordersales);
                            titleArrayList.add(mch);

                        }
                        adapter.notifyDataSetChanged();

                        for (int i = 0; i < sales.size(); i++) {
                            addsales += Integer.parseInt(String.valueOf(sales.get(i)));

                            totalsales.setText("KSH " + String.valueOf(addsales));

                        }

                    }


        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = FirebaseFirestore.getInstance();

        titleArrayList = new ArrayList<>();
        datalist = new ArrayList<>();








         child = db.collection("Orders");
         child.orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                QuerySnapshot tasks = task.getResult();

                RetrieveOrders(tasks);










                }


        });
        child.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                QuerySnapshot tasks = task.getResult();


                for (DocumentSnapshot ssp : tasks.getDocuments()) {
                    List<model> datalist = (List<model>) ssp.get("titleitem");


                    assert datalist != null;
                    datalist.add(new model());

                }
                adapter.notifyDataSetChanged();
            }
        });











    }

    private void RetrieveOrders(@NonNull QuerySnapshot tasks){

        for (DocumentSnapshot dsp : tasks.getDocuments()) {

            Titlemodel mch = dsp.toObject(Titlemodel.class);
            titleArrayList.add(mch);
            adapter.notifyDataSetChanged();


        }

    }




    }


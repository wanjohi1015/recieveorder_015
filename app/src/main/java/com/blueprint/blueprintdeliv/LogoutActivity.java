package com.blueprint.blueprintdeliv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity  extends AppCompatActivity {

    TextView cancel, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        cancel=findViewById(R.id.cancel);
        logout=findViewById(R.id.logout);



        logout.setOnClickListener(v -> {
            if (v.isClickable()) {

                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(i);
            }

        });
        cancel.setOnClickListener(v -> {
            if (v.isClickable()) {

                Intent i1 = new Intent(LogoutActivity.this, Recview.class);
                startActivity(i1);
            }

        });





    }
}

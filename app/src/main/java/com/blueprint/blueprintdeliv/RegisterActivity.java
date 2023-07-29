package com.blueprint.blueprintdeliv;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    EditText etemail, etname, etpassword;
    FirebaseAuth mAuth;
    Button signUpBtn;
    FirebaseFirestore firestore;
    String name, email, password;
    ImageView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        etname = findViewById(R.id.editTextName);
        etemail = findViewById(R.id.editTextEmail);
        etpassword = findViewById(R.id.editTextPassword);
        signUpBtn = findViewById(R.id.cirRegisterButton);
        firestore = FirebaseFirestore.getInstance();
        view = findViewById(R.id.arrowSwitch);


        mAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getText().toString();
                password = etpassword.getText().toString();
                email = etemail.getText().toString();

                if (name.isEmpty()) {


                    etname.setError("Enter name");
                } else if (email.isEmpty()) {

                    etemail.setError("Enter Email");


                } else if (password.isEmpty() || password.length() < 6) {

                    etpassword.setError("Password length must be more than 6");

                } else {

                    createUsers(name, email, password);

                }


            }


        });
    }

         private void createUsers( String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                FirebaseUser user = mAuth.getCurrentUser();

                assert user != null;
                String userid = user.getUid();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("username", name);
                hashMap.put("email", email);
                hashMap.put("userid", userid);


                firestore.collection("Users").document().set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(RegisterActivity.this, "Welcome, You Registered Successfully !!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                        finishAffinity();

                    }
                });
            }
        });

    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }



}

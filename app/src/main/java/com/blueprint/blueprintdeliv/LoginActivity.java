package com.blueprint.blueprintdeliv;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText TextEmail, TextPassword;
    private FirebaseAuth mAuth;
    ImageView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        TextEmail = findViewById(R.id.editTextEmail);
        TextPassword = findViewById(R.id.editTextPassword);
        Button signinBtn = findViewById(R.id.cirLoginButton);
        view = findViewById(R.id.arrowSwitch);

        mAuth = FirebaseAuth.getInstance();
        signinBtn.setOnClickListener(v -> loginUser());



    }
    private void loginUser(){
        String email = TextEmail.getText().toString();
        String pass = TextPassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email , pass)
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(LoginActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this , MainActivity.class));
                            finishAffinity();
                        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show());
            }else{
                TextPassword.setError("Empty Fields Are not Allowed");
            }
        }else if(email.isEmpty()){
            TextEmail.setError("Empty Fields Are not Allowed");
        }else{
            TextEmail.setError("Please Enter Correct Email");
        }
    }
    @Override
    public void onStart() {

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser!=null) {

            startActivity(new Intent(this, MainActivity.class));




        }
        super.onStart();
    }


    public void onLoginClick(View view){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

}

package com.blueprint.blueprintdeliv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.flatdialoglibrary.dialog.FlatDialog;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {



    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    ImageView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("New Orders");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deliveries Board");
        setSupportActionBar(toolbar);




        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);
        logout = findViewById(R.id.imageView2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);


        tabLayout.addTab(tabLayout.newTab().setText("Orders"));
        tabLayout.addTab(tabLayout.newTab().setText("Progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Products"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }


            private void showLoginDialog() {
                final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
                flatDialog. setTitle("   Logout?")
                        .setFirstButtonText("ACCEPT")
                        .setSecondButtonText("CANCEL")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseAuth.getInstance().signOut();
                                Intent mySuperIntent = new Intent(MainActivity.this, LogoutActivity.class);
                                startActivity(mySuperIntent);

                                flatDialog.dismiss();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }


}


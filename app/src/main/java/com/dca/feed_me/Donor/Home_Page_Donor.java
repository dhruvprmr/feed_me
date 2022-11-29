package com.dca.feed_me.Donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dca.feed_me.Login.LoginActivity;
import com.dca.feed_me.NGO.Home_Page_NGO;
import com.dca.feed_me.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Page_Donor extends AppCompatActivity {
    NavigationView navigation_view;
    DrawerLayout mDrawer;
    TextView nav_name,nav_email,nav_type;
    private DatabaseReference ref;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_donor);

    //FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Navigation View
        navigation_view = findViewById(R.id.navigation_view_donor);

        //FirebaseAuth Setup
        fAuth = FirebaseAuth.getInstance();


        //Navigation Menu(Drawer functionality)
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                        NavController navController = navHostFragment.getNavController();
                        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.donor_nav_graph);
                        navGraph.setStartDestination(R.id.fragment_Home_Page_Donor);
                        navController.setGraph(navGraph);
                        break;

                    case R.id.spots:
                        NavHostFragment navHostFragment1 = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                        NavController navController1 = navHostFragment1.getNavController();
                        NavGraph navGraph1 = navController1.getNavInflater().inflate(R.navigation.donor_nav_graph);
                        navGraph1.setStartDestination(R.id.fragment_Spots_donor);
                        navController1.setGraph(navGraph1);
                        break;

                    case R.id.settings:
                        NavHostFragment navHostFragment2 = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                        NavController navController2 = navHostFragment2.getNavController();
                        NavGraph navGraph2 = navController2.getNavInflater().inflate(R.navigation.donor_nav_graph);
                        navGraph2.setStartDestination(R.id.fragment_Settings_Donor);
                        navController2.setGraph(navGraph2);
                        break;

                    case R.id.donate:
                        NavHostFragment navHostFragment3 = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                        NavController navController3 = navHostFragment3.getNavController();
                        NavGraph navGraph3 = navController3.getNavInflater().inflate(R.navigation.donor_nav_graph);
                        navGraph3.setStartDestination(R.id.fragment_Donation_donor);
                        navController3.setGraph(navGraph3);
                        break;

                    case R.id.logout:
                        FirebaseAuth fAuth = FirebaseAuth.getInstance();
                        AlertDialog.Builder builder=new AlertDialog.Builder(Home_Page_Donor.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Are you sure to Log Out?");
                        builder.setCancelable(true);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fAuth.signOut();
                                Intent i = new Intent(Home_Page_Donor.this, LoginActivity.class);
                                startActivity(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.create();
                        dialog.show();
                        break;
                }
                mDrawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        });

        //Declaration
        mDrawer = findViewById(R.id.drawer_layout_donor);


        nav_name = navigation_view.getHeaderView(0).findViewById(R.id.nav_header_name);
        nav_email = navigation_view.getHeaderView(0).findViewById(R.id.nav_header_email);
        nav_type = navigation_view.getHeaderView(0).findViewById(R.id.nav_header_type);


        ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String name = snapshot.child("Name").getValue().toString();
                    nav_name.setText(name);
                    String email = snapshot.child("Email").getValue().toString();
                    nav_email.setText(email);
                    String type = snapshot.child("Type").getValue().toString();
                    nav_type.setText(type);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void OpenDrawer()
    {
        mDrawer.openDrawer(Gravity.LEFT);
    }
}
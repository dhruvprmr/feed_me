package com.dca.feed_me.Splash_Screen;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.dca.feed_me.Donor.Home_Page_Donor;
import com.dca.feed_me.Login.LoginActivity;
import com.dca.feed_me.NGO.Home_Page_NGO;
import com.dca.feed_me.R;
import com.dca.feed_me.Volunteer.Home_Page_Volunteer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        int SPLASH_SCREEN_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {

            FirebaseAuth fAuth;
            fAuth = FirebaseAuth.getInstance();

            //Firebase User for current User;
            FirebaseUser user = fAuth.getCurrentUser();
            if(user!=null) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String getRole = snapshot.child("Type").getValue().toString();
                        if (getRole.equals("Donor")) {
                            Intent i = new Intent(Splash_Screen.this, Home_Page_Donor.class);
                            startActivity(i);
                        }else if(getRole.equals("NGO")){
                            Intent i = new Intent(Splash_Screen.this, Home_Page_NGO.class);
                            startActivity(i);
                        }else if (getRole.equals("Volunteer")){
                            Intent i = new Intent(Splash_Screen.this, Home_Page_Volunteer.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }else{
                Intent i = new Intent(Splash_Screen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
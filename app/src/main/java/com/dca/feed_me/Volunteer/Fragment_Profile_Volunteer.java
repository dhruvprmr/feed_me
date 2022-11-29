package com.dca.feed_me.Volunteer;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.Donor.Fragment_Profile_Donor;
import com.dca.feed_me.Donor.Home_Page_Donor;
import com.dca.feed_me.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

public class Fragment_Profile_Volunteer extends Fragment {
    TextView app_bar_text,profile_volunteer_name,profile_volunteer_email,profile_volunteer_phone,profile_volunteer_username,profile_volunteer_type;
    ImageView ham_icon,back_icon,edit_profile;
    SimpleArcDialog simpleArcDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__profile__volunteer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        profile_volunteer_name = view.findViewById(R.id.profile_volunteer_name);
        profile_volunteer_email = view.findViewById(R.id.profile_volunteer_email);
        profile_volunteer_phone = view.findViewById(R.id.profile_volunteer_phone);
        profile_volunteer_username = view.findViewById(R.id.profile_volunteer_username);
        profile_volunteer_type = view.findViewById(R.id.profile_volunteer_type);
        back_icon = view.findViewById(R.id.back_icon);
        edit_profile = view.findViewById(R.id.edit_profile_donor);

        //SimpleArcLoader
        simpleArcDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);

        app_bar_text.setText("Profile");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    profile_volunteer_name.setText(snapshot.child("Name").getValue().toString());
                    profile_volunteer_email.setText(snapshot.child("Email").getValue().toString());
                    profile_volunteer_phone.setText(snapshot.child("Phone").getValue().toString());
                    profile_volunteer_username.setText(snapshot.child("Username").getValue().toString());
                    profile_volunteer_type.setText(snapshot.child("Type").getValue().toString());
                    simpleArcDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Details are not available at this moment", Toast.LENGTH_SHORT).show();
                    simpleArcDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ham_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);
        edit_profile.setVisibility(View.VISIBLE);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Profile_Volunteer.this).navigate(R.id.fragment_Edit_Profile_Volunteer);
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Profile_Volunteer.this).navigate(R.id.fragment_Settings_Volunteer);
            }
        });
    }
}
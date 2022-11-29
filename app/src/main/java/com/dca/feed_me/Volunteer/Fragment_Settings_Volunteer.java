package com.dca.feed_me.Volunteer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dca.feed_me.R;

public class Fragment_Settings_Volunteer extends Fragment {
    TextView app_bar_text,txt_profile,txt_About_Us_volunteer,txt_ChangePass_volunteer,txt_ContactUs_volunteer;
    ImageView ham_icon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__settings__volunteer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        txt_profile = view.findViewById(R.id.txt_profile_volunteer);
        txt_About_Us_volunteer = view.findViewById(R.id.txt_About_Us_volunteer);
        txt_ContactUs_volunteer = view.findViewById(R.id.txt_Contact_us_volunteer);
        txt_ChangePass_volunteer = view.findViewById(R.id.txt_change_password_volunteer);

        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_Volunteer)getActivity()).OpenDrawer();
            }
        });

        app_bar_text.setText("Settings");


        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Volunteer.this).navigate(R.id.fragment_Profile_Volunteer);
            }
        });

        txt_About_Us_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Volunteer.this).navigate(R.id.fragment_AboutUs_Volunteer);
            }
        });
        txt_ChangePass_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Volunteer.this).navigate(R.id.fragment_Change_Password_Volunteer);
            }
        });
        txt_ContactUs_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Volunteer.this).navigate(R.id.fragment_ContactUs_Volunteer);
            }
        });
    }
}
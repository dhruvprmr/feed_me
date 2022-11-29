package com.dca.feed_me.Donor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.dca.feed_me.Login.LoginActivity;
import com.dca.feed_me.R;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_Settings_Donor extends Fragment {
    TextView app_bar_text,txt_profile,txt_contact_us,txt_change_password,txt_about_Us;
    ImageView ham_icon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__settings__donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        txt_profile = view.findViewById(R.id.txt_profile);
        txt_contact_us = view.findViewById(R.id.txt_Contact_us);
        txt_about_Us = view.findViewById(R.id.txt_About_Us);
        txt_change_password = view.findViewById(R.id.txt_change_password);

        app_bar_text.setText("Settings");

        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_Donor)getActivity()).OpenDrawer();
            }
        });

        txt_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Donor.this).navigate(R.id.fragment_ContactUs_Donor);
            }
        });

        txt_about_Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Donor.this).navigate(R.id.fragment_AboutUs_Donor);
            }
        });

        txt_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Donor.this).navigate(R.id.fragment_Reset_Password_Donor);
            }
        });

        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_Donor.this).navigate(R.id.fragment_Profile_Donor);
            }
        });
    }
}
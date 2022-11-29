package com.dca.feed_me.NGO;

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

public class Fragment_Settings_NGO extends Fragment {
    TextView app_bar_text,txt_profile,txt_About_Us_ngo,txt_ChangePass_NGO,txt_ContactUs_NGO;
    ImageView ham_icon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__settings__n_g_o, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        txt_profile = view.findViewById(R.id.txt_profile_ngo);
        txt_About_Us_ngo = view.findViewById(R.id.txt_About_Us_ngo);
        txt_ContactUs_NGO = view.findViewById(R.id.txt_Contact_us_ngo);
        txt_ChangePass_NGO = view.findViewById(R.id.txt_change_password_ngo);

        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_NGO)getActivity()).OpenDrawer();
            }
        });

        app_bar_text.setText("Settings");


        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_NGO.this).navigate(R.id.fragment_Profile_NGO);
            }
        });

        txt_About_Us_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_NGO.this).navigate(R.id.fragment_AboutUs_NGO);
            }
        });
        txt_ChangePass_NGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_NGO.this).navigate(R.id.fragment_Change_Password_NGO);
            }
        });
        txt_ContactUs_NGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Settings_NGO.this).navigate(R.id.fragment_ContactUs_NGO);
            }
        });
    }
}
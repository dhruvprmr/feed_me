package com.dca.feed_me.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.Donor.Home_Page_Donor;
import com.dca.feed_me.NGO.Home_Page_NGO;
import com.dca.feed_me.R;
import com.dca.feed_me.Volunteer.Home_Page_Volunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.HashMap;

public class Fragment_Registration extends Fragment {

    EditText register_name,register_username,register_phone,register_email,register_password
            ,register_name_ngo,register_username_ngo,register_phone_ngo,register_email_ngo,register_password_ngo
            ,register_name_volunteer,register_email_volunteer,register_username_volunteer,register_phone_volunteer,register_password_volunteer;

    TextInputLayout register_input_layout,register_input_layout_ngo,register_input_layout_volunteer;
    Button register_button,register_button_ngo,register_button_volunteer;
    TextView txt_register_login,register_now_txt;
    Spinner register_spinner;
    SimpleArcDialog simpleArcDialog;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth fAuth;
    String[] select_role = {"Select Role","Donor","NGO","Volunteer"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        txt_register_login = view.findViewById(R.id.txt_register_login);
        register_now_txt = view.findViewById(R.id.register_now_txt);
        register_spinner = view.findViewById(R.id.register_spinner);
        fAuth = FirebaseAuth.getInstance();


        //Donor Layout Items
        register_name = view.findViewById(R.id.edt_register_name_donor);
        register_username = view.findViewById(R.id.edt_register_username_donor);
        register_phone = view.findViewById(R.id.edt_register_phone_donor);
        register_email = view.findViewById(R.id.edt_register_email_donor);
        register_password = view.findViewById(R.id.edt_register_password_donor);
        register_input_layout = view.findViewById(R.id.register_password_layout_donor);
        register_button = view.findViewById(R.id.btn_register_donor);

        //NGO Layout Items
        register_name_ngo = view.findViewById(R.id.edt_register_name_ngo);
        register_username_ngo = view.findViewById(R.id.edt_register_username_ngo);
        register_phone_ngo = view.findViewById(R.id.edt_register_phone_ngo);
        register_input_layout_ngo = view.findViewById(R.id.register_password_layout_ngo);
        register_email_ngo = view.findViewById(R.id.edt_register_email_ngo);
        register_password_ngo = view.findViewById(R.id.edt_register_password_ngo);
        register_button_ngo = view.findViewById(R.id.btn_register_ngo);


        //Volunteer Declaration
        register_name_volunteer = view.findViewById(R.id.edt_register_name_volunteer);
        register_username_volunteer = view.findViewById(R.id.edt_register_username_volunteer);
        register_phone_volunteer = view.findViewById(R.id.edt_register_phone_volunteer);
        register_email_volunteer = view.findViewById(R.id.edt_register_email_volunteer);
        register_input_layout_volunteer = view.findViewById(R.id.register_password_layout_volunteer);
        register_password_volunteer = view.findViewById(R.id.edt_register_password_volunteer);
        register_button_volunteer = view.findViewById(R.id.btn_register_volunteer);


        //SimpleArcLoader Declaration
        simpleArcDialog = new SimpleArcDialog(getActivity());
        ArcConfiguration configuration = new ArcConfiguration(getActivity());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);

        //Spinner Functionality
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,select_role);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        register_spinner.setAdapter(spinner_adapter);


        register_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinner_value = parent.getItemAtPosition(position).toString();
                switch (position){

                    case 0:
                        register_now_txt.setText("Register Now");

                        //Donor Items Visibility
                        register_name.setVisibility(View.GONE);
                        register_username.setVisibility(View.GONE);
                        register_email.setVisibility(View.GONE);
                        register_password.setVisibility(View.GONE);
                        register_input_layout.setVisibility(View.GONE);
                        register_button.setVisibility(View.GONE);
                        register_phone.setVisibility(View.GONE);

                        //NGO Items Visibility
                        register_name_ngo.setVisibility(View.GONE);
                        register_username_ngo.setVisibility(View.GONE);
                        register_input_layout_ngo.setVisibility(View.GONE);
                        register_password_ngo.setVisibility(View.GONE);
                        register_email_ngo.setVisibility(View.GONE);
                        register_phone_ngo.setVisibility(View.GONE);
                        register_button_ngo.setVisibility(View.GONE);

                        //Volunteer Items Visibility
                        register_name_volunteer.setVisibility(View.GONE);
                        register_username_volunteer.setVisibility(View.GONE);
                        register_email_volunteer.setVisibility(View.GONE);
                        register_password_volunteer.setVisibility(View.GONE);
                        register_input_layout_volunteer.setVisibility(View.GONE);
                        register_phone_volunteer.setVisibility(View.GONE);
                        register_button_volunteer.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "Select your role", Toast.LENGTH_SHORT).show();

                        break;
                    case 1:

                        register_now_txt.setText("Donor Registration Page ");

                        //Donor Items Visibility
                        register_name.setVisibility(View.VISIBLE);
                        register_username.setVisibility(View.VISIBLE);
                        register_email.setVisibility(View.VISIBLE);
                        register_phone.setVisibility(View.VISIBLE);
                        register_password.setVisibility(View.VISIBLE);
                        register_input_layout.setVisibility(View.VISIBLE);
                        register_button.setVisibility(View.VISIBLE);

                        //NGO Items Visibility
                        register_name_ngo.setVisibility(View.GONE);
                        register_username_ngo.setVisibility(View.GONE);
                        register_input_layout_ngo.setVisibility(View.GONE);
                        register_password_ngo.setVisibility(View.GONE);
                        register_email_ngo.setVisibility(View.GONE);
                        register_phone_ngo.setVisibility(View.GONE);
                        register_button_ngo.setVisibility(View.GONE);

                        //Volunteer Items Visibility
                        register_name_volunteer.setVisibility(View.GONE);
                        register_username_volunteer.setVisibility(View.GONE);
                        register_email_volunteer.setVisibility(View.GONE);
                        register_password_volunteer.setVisibility(View.GONE);
                        register_input_layout_volunteer.setVisibility(View.GONE);
                        register_phone_volunteer.setVisibility(View.GONE);
                        register_button_volunteer.setVisibility(View.GONE);


                        break;

                    case 2:

                        register_now_txt.setText("NGO Registration Page ");

                        //Donor Items Visibility
                        register_name.setVisibility(View.GONE);
                        register_username.setVisibility(View.GONE);
                        register_email.setVisibility(View.GONE);
                        register_password.setVisibility(View.GONE);
                        register_input_layout.setVisibility(View.GONE);
                        register_phone.setVisibility(View.GONE);
                        register_button.setVisibility(View.GONE);

                        //NGO Items Visibility
                        register_name_ngo.setVisibility(View.VISIBLE);
                        register_username_ngo.setVisibility(View.VISIBLE);
                        register_input_layout_ngo.setVisibility(View.VISIBLE);
                        register_password_ngo.setVisibility(View.VISIBLE);
                        register_email_ngo.setVisibility(View.VISIBLE);
                        register_phone_ngo.setVisibility(View.VISIBLE);
                        register_button_ngo.setVisibility(View.VISIBLE);

                        //Volunteer Items Visibility
                        register_name_volunteer.setVisibility(View.GONE);
                        register_username_volunteer.setVisibility(View.GONE);
                        register_email_volunteer.setVisibility(View.GONE);
                        register_password_volunteer.setVisibility(View.GONE);
                        register_input_layout_volunteer.setVisibility(View.GONE);
                        register_phone_volunteer.setVisibility(View.GONE);
                        register_button_volunteer.setVisibility(View.GONE);

                        break;

                    case 3:

                        register_now_txt.setText("Volunteer Registration Page");

                        //Donor Items visibility
                        register_name.setVisibility(View.GONE);
                        register_username.setVisibility(View.GONE);
                        register_email.setVisibility(View.GONE);
                        register_password.setVisibility(View.GONE);
                        register_input_layout.setVisibility(View.GONE);
                        register_button.setVisibility(View.GONE);
                        register_phone.setVisibility(View.GONE);

                        //NGO Items Visibility
                        register_name_ngo.setVisibility(View.GONE);
                        register_username_ngo.setVisibility(View.GONE);
                        register_input_layout_ngo.setVisibility(View.GONE);
                        register_password_ngo.setVisibility(View.GONE);
                        register_email_ngo.setVisibility(View.GONE);
                        register_phone_ngo.setVisibility(View.GONE);
                        register_button_ngo.setVisibility(View.GONE);

                        //Volunteer Items Visibility
                        register_name_volunteer.setVisibility(View.VISIBLE);
                        register_username_volunteer.setVisibility(View.VISIBLE);
                        register_email_volunteer.setVisibility(View.VISIBLE);
                        register_password_volunteer.setVisibility(View.VISIBLE);
                        register_input_layout_volunteer.setVisibility(View.VISIBLE);
                        register_phone_volunteer.setVisibility(View.VISIBLE);
                        register_button_volunteer.setVisibility(View.VISIBLE);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Registration Process Donor
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String donor_name = register_name.getText().toString();
                String donor_username = register_username.getText().toString().trim();
                String donor_phone = register_phone.getText().toString().trim();
                String donor_email = register_email.getText().toString().trim();
                String donor_pass = register_password.getText().toString().trim();

                if(TextUtils.isEmpty(donor_name)){
                    register_name.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(donor_username)){
                    register_username.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(donor_phone)){
                    register_phone.setError("Phone No. is Required");
                    return;
                }
                if(TextUtils.isEmpty(donor_email)){
                    register_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(donor_pass)){
                    register_password.setError("Password is Required");
                    return;
                }
                if(register_password.length()<6){
                    register_password.setError("Password must have 6 characters");
                    return;
                }else{
                    //Donor Registration
                    simpleArcDialog.show();
                    fAuth.createUserWithEmailAndPassword(donor_email,donor_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                HashMap<String,Object> donor_users = new HashMap<>();
                                donor_users.put("Name",donor_name);
                                donor_users.put("Username",donor_username);
                                donor_users.put("Phone",donor_phone);
                                donor_users.put("Email",donor_email);
                                donor_users.put("Type","Donor");

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(donor_users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getContext(), "Donor Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    NavHostFragment.findNavController(Fragment_Registration.this).navigate(R.id.fragment_Login);
                                                    simpleArcDialog.dismiss();
                                                }
                                            }
                              });
                            }else{
                                Toast.makeText(getContext(), "Error While Registering Donor" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        //Registration Process NGO
        register_button_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ngo_name = register_name_ngo.getText().toString();
                String ngo_username = register_username_ngo.getText().toString().trim();
                String ngo_phone = register_phone_ngo.getText().toString().trim();
                String ngo_email = register_email_ngo.getText().toString().trim();
                String ngo_pass = register_password_ngo.getText().toString().trim();

                if(TextUtils.isEmpty(ngo_name)){
                    register_name_ngo.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(ngo_username)){
                    register_username_ngo.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(ngo_phone)){
                    register_phone_ngo.setError("Phone number is Required");
                    return;
                }
                if(TextUtils.isEmpty(ngo_email)){
                    register_email_ngo.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(ngo_pass)){
                    register_password_ngo.setError("Password is Required");
                    return;
                }
                if(register_password_ngo.length()<6){
                    register_password_ngo.setError("Password must have 6 characters");
                    return;
                }else {

                    //NGO Firebase
                    simpleArcDialog.show();
                    fAuth.createUserWithEmailAndPassword(ngo_email, ngo_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                HashMap<String, Object> ngo_users = new HashMap<>();
                                ngo_users.put("Name", ngo_name);
                                ngo_users.put("Username", ngo_username);
                                ngo_users.put("Phone",ngo_phone);
                                ngo_users.put("Email", ngo_email);
                                ngo_users.put("Type", "NGO");

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(ngo_users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "NGO Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    simpleArcDialog.dismiss();
                                                    NavHostFragment.findNavController(Fragment_Registration.this).navigate(R.id.fragment_Login);
                                                }
                                            }
                                        });
                            }else{
                                Toast.makeText(getContext(), "Error While Registering NGO" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        //Registration Page Volunteer
        register_button_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String volunteer_name = register_name_volunteer.getText().toString();
                String volunteer_username = register_username_volunteer.getText().toString().trim();
                String volunteer_phone = register_phone_volunteer.getText().toString().trim();
                String volunteer_email = register_email_volunteer.getText().toString().trim();
                String volunteer_password = register_password_volunteer.getText().toString().trim();

                if (TextUtils.isEmpty(volunteer_name)) {
                    register_name_volunteer.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(volunteer_username)) {
                    register_username_volunteer.setError("Username is Required");
                    return;
                }
                if (TextUtils.isEmpty(volunteer_phone)) {
                    register_phone_volunteer.setError("Phone number is Required");
                    return;
                }
                if (TextUtils.isEmpty(volunteer_email)) {
                    register_email_volunteer.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(volunteer_password)) {
                    register_password_volunteer.setError("Password is Required");
                    return;
                }
                if (register_password_volunteer.length() < 6) {
                    register_password_volunteer.setError("Password must have 6 characters");
                    return;
                } else {

                    //Volunteer Registration in Firebase
                    simpleArcDialog.show();
                    fAuth.createUserWithEmailAndPassword(volunteer_email, volunteer_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                HashMap<String, Object> volunteer_users = new HashMap<>();
                                volunteer_users.put("Name", volunteer_name);
                                volunteer_users.put("Username", volunteer_username);
                                volunteer_users.put("Phone",volunteer_phone);
                                volunteer_users.put("Email", volunteer_email);
                                volunteer_users.put("Type", "Volunteer");

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(volunteer_users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Volunteer Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    simpleArcDialog.dismiss();
                                                    NavHostFragment.findNavController(Fragment_Registration.this).navigate(R.id.fragment_Login);
                                                }
                                            }
                                        });
                            }else{
                                Toast.makeText(getContext(), "Error While Registering Volunteer" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });


        txt_register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Registration.this).navigate(R.id.fragment_Login);
            }
        });
    }
}
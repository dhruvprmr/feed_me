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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.Donor.Home_Page_Donor;
import com.dca.feed_me.NGO.Home_Page_NGO;
import com.dca.feed_me.R;
import com.dca.feed_me.Volunteer.Home_Page_Volunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

public class Fragment_Login extends Fragment {

    SimpleArcDialog simpleArcDialog;
    TextView txt_signup,txt_ForgetPass_login;
    EditText edt_login_email,edt_login_password;
    Button login_btn;
    RadioButton donor_radio,ngo_radio,volunteer_radio;
    CheckBox check_login_remember;
    FirebaseAuth fAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        txt_signup = view.findViewById(R.id.txt_signup);
        edt_login_email = view.findViewById(R.id.edt_login_email);
        edt_login_password = view.findViewById(R.id.edt_login_password);
        donor_radio = view.findViewById(R.id.radio_donor);
        ngo_radio = view.findViewById(R.id.radio_ngo);
        volunteer_radio = view.findViewById(R.id.radio_volunteer);
        login_btn = view.findViewById(R.id.btn_login);
        check_login_remember = view.findViewById(R.id.check_login_remember);
        txt_ForgetPass_login = view.findViewById(R.id.txt_login_Forgetpassword);

        txt_ForgetPass_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Login.this).navigate(R.id.fragment_Forget_Password);
            }
        });

        fAuth = FirebaseAuth.getInstance();

        //On Login Button Click Listener
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convert EditText to String
                String email = edt_login_email.getText().toString().trim();
                String pass = edt_login_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    edt_login_email.setError("Email is required");
                }
                if (TextUtils.isEmpty(pass)){
                    edt_login_password.setError("Password is required");
                }
                if(edt_login_password.length()<6){
                    edt_login_password.setError("Password must have 6 Characters");
                }
                else
                {
                    simpleArcDialog = new SimpleArcDialog(getContext());
                    ArcConfiguration configuration = new ArcConfiguration(getContext());
                    simpleArcDialog.setConfiguration(configuration);
                    configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
                    configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
                    configuration.setText("Please wait..");
                    simpleArcDialog.setCancelable(false);
                    simpleArcDialog.show();

                    fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //Donor Login
                                if(donor_radio.isChecked()){

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String getRole = snapshot.child("Type").getValue().toString();
                                            String donor = "Donor";
                                            if(getRole.equals(donor)){
                                                Intent i = new Intent(getContext(),Home_Page_Donor.class);
                                                startActivity(i);
                                                Toast.makeText(getContext(), "Donor Login Successfully", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }else{
                                                Toast.makeText(getContext(), "You have selected Wrong role", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    //NGO Login
                                }else if (ngo_radio.isChecked()){

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String getRole = snapshot.child("Type").getValue().toString();

                                            System.out.println("Your Role is : " + getRole);
                                            String ngo = "NGO";
                                            if(getRole.equals(ngo)){
                                                Intent i = new Intent(getContext(),Home_Page_NGO.class);
                                                startActivity(i);
                                                Toast.makeText(getContext(), "NGO Login Successfully", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }else{
                                                Toast.makeText(getContext(), "You have selected Wrong role", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }

                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    //Volunteer Login
                                }else if(volunteer_radio.isChecked()){

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String getRole = snapshot.child("Type").getValue().toString();

                                            System.out.println("Your Role is : " + getRole);
                                            String volunteer = "Volunteer";
                                            if(getRole.equals(volunteer)){
                                                Intent i = new Intent(getContext(),Home_Page_Volunteer.class);
                                                startActivity(i);
                                                Toast.makeText(getContext(), "Volunteer Login Successfully", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }else{
                                                Toast.makeText(getContext(), "You have selected Wrong role", Toast.LENGTH_SHORT).show();
                                                simpleArcDialog.dismiss();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                            }else{
                                Toast.makeText(getContext(), "Error while Login" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }


                        }
                    });


                }
            }
        });

    //Sign Up Text Functionality
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Login.this).navigate(R.id.fragment_Registration);
            }
        });

    }
}
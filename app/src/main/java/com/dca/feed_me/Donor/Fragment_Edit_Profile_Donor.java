package com.dca.feed_me.Donor;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.Login.Fragment_Registration;
import com.dca.feed_me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;

public class Fragment_Edit_Profile_Donor extends Fragment {
    TextView app_bar_text;
    ImageView ham_icon, back_icon;
    EditText edit_profile_name_donor, edit_profile_phone_donor, edit_profile_email_donor, edit_profile_username_donor;
    SimpleArcDialog simpleArcDialog;
    Button update_profile_donor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__edit__profile__donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        back_icon = view.findViewById(R.id.back_icon);
        edit_profile_name_donor = view.findViewById(R.id.edit_profile_name_donor);
        edit_profile_email_donor = view.findViewById(R.id.edit_profile_email_donor);
        edit_profile_phone_donor = view.findViewById(R.id.edit_profile_phone_donor);
        edit_profile_username_donor = view.findViewById(R.id.edit_profile_username_donor);
        update_profile_donor = view.findViewById(R.id.update_profile_donor);

        //SimpleArcLoader
        simpleArcDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);


        app_bar_text.setText("Edit Profile");

        ham_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Edit_Profile_Donor.this).navigate(R.id.fragment_Profile_Donor);
            }
        });

        simpleArcDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    edit_profile_name_donor.setText(snapshot.child("Name").getValue().toString());
                    edit_profile_phone_donor.setText(snapshot.child("Phone").getValue().toString());
                    edit_profile_email_donor.setText(snapshot.child("Email").getValue().toString());
                    edit_profile_username_donor.setText(snapshot.child("Username").getValue().toString());
                    simpleArcDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Details are not available at this moment", Toast.LENGTH_SHORT).show();
                    simpleArcDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        update_profile_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDonorData();
                NavHostFragment.findNavController(Fragment_Edit_Profile_Donor.this).navigate(R.id.fragment_Home_Page_Donor);
            }
        });
    }

    private void updateDonorData() {

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = edit_profile_name_donor.getText().toString();
                String email = edit_profile_email_donor.getText().toString();
                String phone = edit_profile_phone_donor.getText().toString();
                String username = edit_profile_username_donor.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    edit_profile_name_donor.setError("Enter Your Name");
                } else if (TextUtils.isEmpty(email)) {
                    edit_profile_email_donor.setError("Enter Your Email");
                } else if (TextUtils.isEmpty(username)) {
                    edit_profile_username_donor.setError("Enter Your Username");
                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, Object> donor_users = new HashMap<>();
                                donor_users.put("Name", name);
                                donor_users.put("Username", username);
                                donor_users.put("Phone",phone);
                                donor_users.put("Email", email);
                                donor_users.put("Type", "Donor");

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(donor_users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Donor Updated Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getContext(), "Error While Updating Donor" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
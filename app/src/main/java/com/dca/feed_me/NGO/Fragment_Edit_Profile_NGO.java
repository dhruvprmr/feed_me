package com.dca.feed_me.NGO;

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

import com.dca.feed_me.Donor.Fragment_Edit_Profile_Donor;
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

public class Fragment_Edit_Profile_NGO extends Fragment {
    TextView app_bar_text;
    ImageView ham_icon,back_icon;
    SimpleArcDialog simpleArcDialog;
    Button update_profile_ngo;
    EditText edit_profile_name_ngo,edit_profile_email_ngo,edit_profile_username_ngo,edit_profile_phone_ngo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__edit__profile__n_g_o, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        back_icon = view.findViewById(R.id.back_icon);
        edit_profile_email_ngo = view.findViewById(R.id.edit_profile_email_ngo);
        edit_profile_name_ngo = view.findViewById(R.id.edit_profile_name_ngo);
        edit_profile_username_ngo = view.findViewById(R.id.edit_profile_username_ngo);
        edit_profile_phone_ngo = view.findViewById(R.id.edit_profile_phone_ngo);
        update_profile_ngo = view.findViewById(R.id.update_profile_ngo);

        ham_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Edit_Profile_NGO.this).navigate(R.id.fragment_Profile_NGO);
            }
        });

        //App Bar Text
        app_bar_text.setText("Edit Profile");


        //SimpleArcLoader
        simpleArcDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);


        simpleArcDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    edit_profile_name_ngo.setText(snapshot.child("Name").getValue().toString());
                    edit_profile_phone_ngo.setText(snapshot.child("Phone").getValue().toString());
                    edit_profile_email_ngo.setText(snapshot.child("Email").getValue().toString());
                    edit_profile_username_ngo.setText(snapshot.child("Username").getValue().toString());
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

        update_profile_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNGO();
                NavHostFragment.findNavController(Fragment_Edit_Profile_NGO.this).navigate(R.id.fragment_Home_Page_NGO);
            }
        });
    }

    private void updateNGO() {

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = edit_profile_name_ngo.getText().toString();
                String email = edit_profile_email_ngo.getText().toString();
                String phone = edit_profile_phone_ngo.getText().toString();
                String username = edit_profile_username_ngo.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    edit_profile_name_ngo.setError("Enter Your Name");
                } else if (TextUtils.isEmpty(email)) {
                    edit_profile_email_ngo.setError("Enter Your Email");
                } else if (TextUtils.isEmpty(username)) {
                    edit_profile_username_ngo.setError("Enter Your Username");
                } else if(TextUtils.isEmpty(phone)){
                    edit_profile_phone_ngo.setError("Enter Your Phone");
                }else{

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String, Object> ngo_user = new HashMap<>();
                                    ngo_user.put("Name", name);
                                    ngo_user.put("Username", username);
                                    ngo_user.put("Phone",phone);
                                    ngo_user.put("Email", email);
                                    ngo_user.put("Type", "NGO");

                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(ngo_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), "NGO Updated Successfully", Toast.LENGTH_SHORT).show();
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
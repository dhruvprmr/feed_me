package com.dca.feed_me.Donor;

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

import com.dca.feed_me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Reset_Password_Donor extends Fragment {

    TextView app_bar_text;
    ImageView back_icon,ham_icon;
    EditText currentPassword_donor,newPassword_donor;
    Button change_pass_donor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__reset__password__donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        back_icon = view.findViewById(R.id.back_icon);
        ham_icon = view.findViewById(R.id.ham_icon);
        currentPassword_donor = view.findViewById(R.id.currentPassword_donor);
        newPassword_donor = view.findViewById(R.id.newPassword_donor);
        change_pass_donor = view.findViewById(R.id.change_pass_donor);

        app_bar_text.setText("Change Your Password");

        back_icon.setVisibility(View.VISIBLE);
        ham_icon.setVisibility(View.GONE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Reset_Password_Donor.this).navigate(R.id.fragment_Settings_Donor);
            }
        });

        change_pass_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPassword_donor.getText().toString();
                if (TextUtils.isEmpty(newPassword)) {
                    newPassword_donor.setError("Enter New Password");
                }
                if (newPassword_donor.length() < 6) {
                    newPassword_donor.setError("Password Must have 6 character");
                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Password not changed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    System.out.println(newPassword);
                }
            }
        });
    }
}
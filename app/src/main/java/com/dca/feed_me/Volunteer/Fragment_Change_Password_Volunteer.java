package com.dca.feed_me.Volunteer;

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

import com.dca.feed_me.NGO.Fragment_Change_Password_NGO;
import com.dca.feed_me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Change_Password_Volunteer extends Fragment {

    TextView app_bar_text;
    ImageView back_icon,ham_icon;
    EditText currentPassword_Volunteer,newPassword_Volunteer;
    Button change_pass_Volunteer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__change__password__volunteer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        back_icon = view.findViewById(R.id.back_icon);
        ham_icon = view.findViewById(R.id.ham_icon);
        currentPassword_Volunteer = view.findViewById(R.id.currentPassword_volunteer);
        newPassword_Volunteer = view.findViewById(R.id.newPassword_volunteer);
        change_pass_Volunteer = view.findViewById(R.id.change_pass_volunteer);

        app_bar_text.setText("Change Your Password");

        back_icon.setVisibility(View.VISIBLE);
        ham_icon.setVisibility(View.GONE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Change_Password_Volunteer.this).navigate(R.id.fragment_Settings_Volunteer);
            }
        });

        change_pass_Volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPassword_Volunteer.getText().toString();

                if(TextUtils.isEmpty(newPassword)){
                    newPassword_Volunteer.setError("Enter New Password");
                }if(newPassword_Volunteer.length()<6){
                    newPassword_Volunteer.setError("Password Must have 6 character");
                }else{

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(Fragment_Change_Password_Volunteer.this).navigate(R.id.fragment_Settings_Volunteer);
                            }else{
                                Toast.makeText(getContext(), "Error" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
}
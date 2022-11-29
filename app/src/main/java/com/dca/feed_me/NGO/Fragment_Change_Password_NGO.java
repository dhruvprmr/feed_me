package com.dca.feed_me.NGO;

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

public class Fragment_Change_Password_NGO extends Fragment {

    TextView app_bar_text;
    ImageView back_icon,ham_icon;
    EditText currentPassword_NGO,newPassword_NGO;
    Button change_pass_ngo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__change__password__n_g_o, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        back_icon = view.findViewById(R.id.back_icon);
        ham_icon = view.findViewById(R.id.ham_icon);
        currentPassword_NGO = view.findViewById(R.id.currentPassword_ngo);
        newPassword_NGO = view.findViewById(R.id.newPassword_ngo);
        change_pass_ngo = view.findViewById(R.id.change_pass_ngo);

        app_bar_text.setText("Change Your Password");

        back_icon.setVisibility(View.VISIBLE);
        ham_icon.setVisibility(View.GONE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Change_Password_NGO.this).navigate(R.id.fragment_Settings_NGO);
            }
        });

        change_pass_ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPassword_NGO.getText().toString();

                if(TextUtils.isEmpty(newPassword)){
                    newPassword_NGO.setError("Enter New Password");
                }if(newPassword_NGO.length()<6){
                    newPassword_NGO.setError("Password Must have 6 character");
                }else{

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(Fragment_Change_Password_NGO.this).navigate(R.id.fragment_Settings_NGO);
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
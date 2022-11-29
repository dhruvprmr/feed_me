package com.dca.feed_me.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_Forget_Password extends Fragment {
TextView app_bar_text;
ImageView ham_icon,back_icon;
EditText forgot_pass_email_login;
Button send_email_login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__forget__password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        send_email_login = view.findViewById(R.id.send_email_login);
        forgot_pass_email_login = view.findViewById(R.id.forgot_pass_email_login);
        back_icon = view.findViewById(R.id.back_icon);

        ham_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_Forget_Password.this).navigate(R.id.fragment_Login);
            }
        });

        app_bar_text.setText("Forgot Password");

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        send_email_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgot_pass = forgot_pass_email_login.getText().toString();
                fAuth.sendPasswordResetEmail(forgot_pass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "Reset link sent on email", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                      }
                   }
                });
            }
        });
    }
}
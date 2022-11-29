package com.dca.feed_me.Volunteer;

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

import com.dca.feed_me.NGO.Fragment_ContactUs_NGO;
import com.dca.feed_me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.HashMap;

public class Fragment_ContactUs_Volunteer extends Fragment {

    EditText contactUs_name_volunteer,contactUs_email_volunteer,contactUs_phone_volunteer,contactUs_message_volunteer;
    Button send_ContactUs_volunteer;
    TextView app_bar_text;
    ImageView ham_icon,back_icon;
    SimpleArcDialog simpleArcDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__contact_us__volunteer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        contactUs_name_volunteer = view.findViewById(R.id.contactUs_name_volunteer);
        contactUs_email_volunteer = view.findViewById(R.id.contactUs_email_volunteer);
        contactUs_phone_volunteer = view.findViewById(R.id.contactUs_phone_volunteer);
        contactUs_message_volunteer = view.findViewById(R.id.contactUs_message_volunteer);
        send_ContactUs_volunteer = view.findViewById(R.id.send_contactUs_volunteer);
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        back_icon = view.findViewById(R.id.back_icon);

        app_bar_text.setText("Contact Us");

        //SimpleArcDialog
        simpleArcDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);


        ham_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment_ContactUs_Volunteer.this).navigate(R.id.fragment_Settings_Volunteer);
            }
        });

        send_ContactUs_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleArcDialog.show();

                String name = contactUs_name_volunteer.getText().toString();
                String email = contactUs_email_volunteer.getText().toString().trim();
                String phone = contactUs_phone_volunteer.getText().toString().trim();
                String message = contactUs_message_volunteer.getText().toString();
                if(TextUtils.isEmpty(name)){
                    contactUs_name_volunteer.setError("Enter Your Name");
                }if (TextUtils.isEmpty(email)){
                    contactUs_email_volunteer.setError("Enter Your Email");
                }if(TextUtils.isEmpty(phone)){
                    contactUs_phone_volunteer.setError("Enter Your Phone");
                }if(TextUtils.isEmpty(message)){
                    contactUs_message_volunteer.setError("Enter Your Message");
                }else{
                    HashMap<String,Object> queries = new HashMap<>();
                    queries.put("name",name);
                    queries.put("email",email);
                    queries.put("phone",phone);
                    queries.put("message",message);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("contact_Us").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref.setValue(queries).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Your Request Sent Successfully", Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                                NavHostFragment.findNavController(Fragment_ContactUs_Volunteer.this).navigate(R.id.fragment_Home_Page_Volunteer);

                            }else{
                                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                simpleArcDialog.dismiss();
                            }
                        }
                    });
                }simpleArcDialog.dismiss();
            }
        });

    }
}
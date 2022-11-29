package com.dca.feed_me.Donor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Fragment_Donation_donor extends Fragment {

    TextView app_bar_text;
    ImageView ham_icon;
    EditText donate_name_donor,donate_phone_donor,donate_place_donor,items_donor,quantity_donor,cook_time_donor;
    Button donate_button_donor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__donation_donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        donate_name_donor = view.findViewById(R.id.donate_name_donor);
        donate_place_donor = view.findViewById(R.id.donate_place_donor);
        items_donor = view.findViewById(R.id.items_donor);
        quantity_donor = view.findViewById(R.id.quantity_donor);
        cook_time_donor = view.findViewById(R.id.cook_time_donor);
        donate_phone_donor = view.findViewById(R.id.donate_phone_donor);
        donate_button_donor = view.findViewById(R.id.donate_button_donor);

        //AppBar Text
        app_bar_text.setText("Donate Now");

        //HamIcon Function
        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_Donor)getActivity()).OpenDrawer();
            }
        });

        donate_button_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = donate_name_donor.getText().toString();
                String place = donate_place_donor.getText().toString();
                String items = items_donor.getText().toString();
                String phone = donate_phone_donor.getText().toString();
                String time = cook_time_donor.getText().toString();
                String quantity = quantity_donor.getText().toString();

                HashMap<String,Object> donation = new HashMap<>();
                donation.put("name",name);
                donation.put("phone",phone);
                donation.put("place",place);
                donation.put("items",items);
                donation.put("time",time);
                donation.put("quantity",quantity);
                donation.put("status","pending");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("donations").push();
                ref.setValue(donation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Donation Request Made", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(Fragment_Donation_donor.this).navigate(R.id.fragment_Home_Page_Donor);
                        }else{
                            Toast.makeText(getContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
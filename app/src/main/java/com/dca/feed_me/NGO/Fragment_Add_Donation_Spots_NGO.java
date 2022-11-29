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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class Fragment_Add_Donation_Spots_NGO extends Fragment {

    TextView app_bar_text;
    ImageView ham_icon;
    EditText donation_spot_name,donation_spot_address,donation_spot_pin,donation_spot_landmark;
    Button save_location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__add__donation__spots__n_g_o, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);
        donation_spot_name = view.findViewById(R.id.donation_spot_name);
        donation_spot_address = view.findViewById(R.id.donation_spot_address);
        donation_spot_pin = view.findViewById(R.id.donation_spot_pin);
        donation_spot_landmark = view.findViewById(R.id.donation_spot_landmark);
        save_location = view.findViewById(R.id.save_location);

        app_bar_text.setText("Add Donation Spot");

        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_NGO)getActivity()).OpenDrawer();
            }
        });

        //Save Function
        save_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = donation_spot_name.getText().toString();
                String add = donation_spot_address.getText().toString();
                String pin = donation_spot_pin.getText().toString().trim();
                String landmark = donation_spot_landmark.getText().toString();

                if(TextUtils.isEmpty(name)){
                    donation_spot_name.setError("Place Name is Required");
                }if(TextUtils.isEmpty(add)){
                    donation_spot_address.setError("Address is Required");
                }if(TextUtils.isEmpty(pin)){
                    donation_spot_pin.setError("Pincode is Required");
                }if(TextUtils.isEmpty(landmark)){
                    donation_spot_landmark.setError("Landmark is Required");
                }else{
                    Random random = new Random();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("spots").push();

                    HashMap<String,Object> spots = new HashMap<>();
                    spots.put("name",name);
                    spots.put("address",add);
                    spots.put("pincode",pin);
                    spots.put("landmark",landmark);

                    ref.setValue(spots).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Spot Saved Successfully", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(Fragment_Add_Donation_Spots_NGO.this).navigate(R.id.fragment_Home_Page_NGO);
                            }
                        }
                    });
                }
            }
        });
    }
}
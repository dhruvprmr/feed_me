package com.dca.feed_me.NGO;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dca.feed_me.Adapter.DonationAdapter;
import com.dca.feed_me.Model.Donation;
import com.dca.feed_me.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

public class Fragment_Donation_Request_NGO extends Fragment {

    TextView app_bar_text;
    ImageView ham_icon;
    RecyclerView recyclerView;
    SimpleArcDialog simpleArcDialog;
    DonationAdapter donationAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__donation__request__n_g_o, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration
        app_bar_text = view.findViewById(R.id.app_bar_text);
        ham_icon = view.findViewById(R.id.ham_icon);


        //Simple Arc Dialog
        simpleArcDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(getContext());
        simpleArcDialog.setConfiguration(configuration);
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#7ac03f"), Color.parseColor("#1a1a1a")});
        configuration.setText("Please wait..");
        simpleArcDialog.setCancelable(false);

        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home_Page_NGO)getActivity()).OpenDrawer();
            }
        });
        app_bar_text.setText("Donation Requests");

        simpleArcDialog.show();

        Query query = FirebaseDatabase.getInstance().getReference("donations")
                .orderByChild("status").equalTo("pending");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){

                        recyclerView = view.findViewById(R.id.recycler_view_donations_ngo);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        FirebaseRecyclerOptions<Donation> options = new FirebaseRecyclerOptions.Builder<Donation>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("donations").orderByChild("status").equalTo("pending"), Donation.class)
                                .build();

                        donationAdapter = new DonationAdapter(options);
                        recyclerView.setAdapter(donationAdapter);
                        donationAdapter.startListening();
                        simpleArcDialog.dismiss();
                    }
                }else {
                    Toast.makeText(getContext(), "Donations are not Available", Toast.LENGTH_SHORT).show();
                    simpleArcDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
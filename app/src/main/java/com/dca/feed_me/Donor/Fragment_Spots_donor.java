package com.dca.feed_me.Donor;

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
import com.dca.feed_me.Adapter.SpotsAdapter;
import com.dca.feed_me.Model.Spots;
import com.dca.feed_me.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Spots_donor extends Fragment {

    private List<Spots> spotsList;
    SpotsAdapter spotsAdapter;
    RecyclerView recyclerView;
    TextView app_bar_text;
    ImageView ham_icon;
    SimpleArcDialog simpleArcDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__spots_donor, container, false);
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

        app_bar_text.setText("Needy Spots");
        ham_icon.setOnClickListener(v -> ((Home_Page_Donor)getActivity()).OpenDrawer());

        recyclerView = view.findViewById(R.id.recycler_view_spots);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        simpleArcDialog.show();

        spotsList = new ArrayList<>();
        spotsAdapter = new SpotsAdapter(getContext(),spotsList);

        recyclerView.setAdapter(spotsAdapter);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("spots");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                spotsList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Spots spots = dataSnapshot.getValue(Spots.class);
                    spotsList.add(spots);
                }

                spotsAdapter.notifyDataSetChanged();
                simpleArcDialog.dismiss();

                if(spotsList.isEmpty()){
                    Toast.makeText(getContext(), "Spots are not available at the moment", Toast.LENGTH_SHORT).show();
                    simpleArcDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
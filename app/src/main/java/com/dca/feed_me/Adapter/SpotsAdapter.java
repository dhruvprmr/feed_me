package com.dca.feed_me.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dca.feed_me.Model.Spots;
import com.dca.feed_me.R;

import java.util.List;

public class SpotsAdapter extends RecyclerView.Adapter<SpotsAdapter.ViewHolder> {

    private Context context;
    private List<Spots> spotsList;

    public SpotsAdapter(Context context, List<Spots> spotsList) {
        this.context = context;
        this.spotsList = spotsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.donation_spots,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Spots spots =  spotsList.get(position);

        holder.tv_name_spots.setText(spots.getName());
        holder.tv_address_spots.setText(spots.getAddress());
        holder.tv_pincode_spots.setText(spots.getPincode());
        holder.tv_landmark_spots.setText(spots.getLandmark());

    }

    @Override
    public int getItemCount() {
        return spotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tv_name_spots,tv_address_spots,tv_pincode_spots,tv_landmark_spots;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Declaration
            tv_name_spots = itemView.findViewById(R.id.tv_name_spots);
            tv_address_spots = itemView.findViewById(R.id.tv_address_spots);
            tv_pincode_spots = itemView.findViewById(R.id.tv_pincode_spots);
            tv_landmark_spots = itemView.findViewById(R.id.tv_landmark_spots);

        }
    }

}

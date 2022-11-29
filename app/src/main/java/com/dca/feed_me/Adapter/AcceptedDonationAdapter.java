package com.dca.feed_me.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dca.feed_me.Model.AcceptedDonation;
import com.dca.feed_me.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class AcceptedDonationAdapter extends FirebaseRecyclerAdapter<AcceptedDonation,AcceptedDonationAdapter.ViewHolder> {
    public AcceptedDonationAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final AcceptedDonation acceptedDonation) {
        holder.tv_name_donation.setText(acceptedDonation.getName());
        holder.tv_phone_donation.setText(acceptedDonation.getPhone());
        holder.tv_items.setText(acceptedDonation.getItems());
        holder.tv_place.setText(acceptedDonation.getPlace());
        holder.tv_quantity.setText(acceptedDonation.getQuantity());
        holder.tv_time.setText(acceptedDonation.getTime());

        //Accept Donation In Volunteer
        holder.donation_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("donations")
                        .child(getRef(position).getKey()).removeValue();

                Toast.makeText(v.getContext(), "Donation Delivered Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        //Reject Donation In Volunteer
        holder.donation_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = holder.tv_name_donation.getText().toString();
                String phone = holder.tv_phone_donation.getText().toString();
                String items = holder.tv_items.getText().toString();
                String place = holder.tv_place.getText().toString();
                String quantity = holder.tv_quantity.getText().toString();
                String time = holder.tv_time.getText().toString();

                HashMap<String, Object> updateDonation = new HashMap<>();
                updateDonation.put("name", name);
                updateDonation.put("phone", phone);
                updateDonation.put("items", items);
                updateDonation.put("place", place);
                updateDonation.put("quantity", quantity);
                updateDonation.put("time", time);
                updateDonation.put("status", "pending");

                FirebaseDatabase.getInstance().getReference().child("donations")
                        .child(getRef(position).getKey()).updateChildren(updateDonation)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Rejected Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }// End of OnBindViewMethod

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_donations,parent,false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView donation_accept,donation_reject;
        TextView tv_name_donation,tv_phone_donation,tv_items,tv_place,tv_quantity,tv_time;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_name_donation =itemView.findViewById(R.id.tv_name_donation_accepted);
            tv_phone_donation =itemView.findViewById(R.id.tv_phone_accepted_donation);
            tv_items =itemView.findViewById(R.id.tv_items_accepted);
            tv_place =itemView.findViewById(R.id.tv_place_accepted);
            tv_quantity =itemView.findViewById(R.id.tv_quantity_accepted);
            tv_time =itemView.findViewById(R.id.tv_time_accepted);

            donation_accept = itemView.findViewById(R.id.donation_accept);
            donation_reject = itemView.findViewById(R.id.donation_reject);
        }
    }
}
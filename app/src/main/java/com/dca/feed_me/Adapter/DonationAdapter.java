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

import com.dca.feed_me.Model.Donation;
import com.dca.feed_me.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DonationAdapter extends FirebaseRecyclerAdapter<Donation,DonationAdapter.ViewHolder> {
    public DonationAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Donation donation) {
        holder.tv_name_donation.setText(donation.getName());
        holder.tv_phone_donation.setText(donation.getPhone());
        holder.tv_items.setText(donation.getItems());
        holder.tv_place.setText(donation.getPlace());
        holder.tv_quantity.setText(donation.getQuantity());
        holder.tv_time.setText(donation.getTime());
        holder.donation_accept.setOnClickListener(new View.OnClickListener() {
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
                updateDonation.put("status", "accept");

                FirebaseDatabase.getInstance().getReference().child("donations")
                        .child(getRef(position).getKey()).updateChildren(updateDonation)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
                            }
                });
            }
        });

        holder.donation_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("donations")
                        .child(getRef(position).getKey()).removeValue();

                Toast.makeText(v.getContext(), "Donation Rejected", Toast.LENGTH_SHORT).show();
            }
        });
    }// End of OnBindViewMethod

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donations,parent,false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView donation_accept,donation_reject;
        TextView tv_name_donation,tv_phone_donation,tv_items,tv_place,tv_quantity,tv_time;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_name_donation =itemView.findViewById(R.id.tv_name_donation);
            tv_phone_donation =itemView.findViewById(R.id.tv_phone_donation);
            tv_items =itemView.findViewById(R.id.tv_items);
            tv_place =itemView.findViewById(R.id.tv_place);
            tv_quantity =itemView.findViewById(R.id.tv_quantity);
            tv_time =itemView.findViewById(R.id.tv_time);

            donation_accept = itemView.findViewById(R.id.donation_accept);
            donation_reject = itemView.findViewById(R.id.donation_reject);
        }
    }
}
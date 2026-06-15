package com.example.ongi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ongi.R;
import com.example.ongi.model.Donation;

import android.content.Context;
import android.content.Intent;

import com.example.ongi.DetailActivity;

import java.util.List;

public class DonationAdapter
        extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    List<Donation> donationList;

    boolean showAddress;

    // Browse용
    public DonationAdapter(List<Donation> donationList) {

        this.donationList = donationList;

        this.showAddress = true;

    }

    // AI용
    public DonationAdapter(

            List<Donation> donationList,

            boolean showAddress) {

        this.donationList = donationList;

        this.showAddress = showAddress;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(

            @NonNull ViewGroup parent,

            int viewType) {

        View view =

                LayoutInflater

                        .from(parent.getContext())

                        .inflate(

                                R.layout.item_donation,

                                parent,

                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(

            @NonNull ViewHolder holder,

            int position) {

        Donation donation =

                donationList.get(position);


        holder.txtName.setText(

                donation.getName());

        holder.txtCategory.setText(

                donation.getCategory());


        // 주소 보여줄지 말지

        if(showAddress){

            holder.txtAddress.setVisibility(

                    View.VISIBLE);

            holder.txtAddress.setText(

                    donation.getAddress());

        }

        else{

            holder.txtAddress.setVisibility(

                    View.GONE);

        }


        holder.itemView.setOnClickListener(v -> {

            Context context =

                    v.getContext();


            SharedPreferences prefs =

                    context.getSharedPreferences(

                            "recent",

                            Context.MODE_PRIVATE);

            SharedPreferences.Editor editor =

                    prefs.edit();


            editor.putString(

                    "name",

                    donation.getName());

            editor.putString(

                    "category",

                    donation.getCategory());

            editor.putString(

                    "address",

                    donation.getAddress());

            editor.putString(

                    "phone",

                    donation.getPhone());

            editor.putString(

                    "homepage",

                    donation.getHomepage());

            editor.apply();


            Intent intent =

                    new Intent(

                            context,

                            DetailActivity.class);


            intent.putExtra(

                    "name",

                    donation.getName());

            intent.putExtra(

                    "image",

                    donation.getImage());

            intent.putExtra(

                    "category",

                    donation.getCategory());

            intent.putExtra(

                    "phone",

                    donation.getPhone());

            intent.putExtra(

                    "address",

                    donation.getAddress());

            intent.putExtra(

                    "homepage",

                    donation.getHomepage());


            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {

        return donationList.size();

    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtName;

        TextView txtCategory;

        TextView txtAddress;

        public ViewHolder(

                @NonNull View itemView) {

            super(itemView);


            txtName =

                    itemView.findViewById(

                            R.id.txtName);

            txtCategory =

                    itemView.findViewById(

                            R.id.txtCategory);

            txtAddress =

                    itemView.findViewById(

                            R.id.txtAddress);

        }

    }

}
package com.example.proekt1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Driver;

import java.util.ArrayList;  // Import ArrayList
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private List<Driver> driverList = new ArrayList<>();  // Initialize to avoid null pointer
    private OnDriverClickListener onDriverClickListener;

    // Constructor
    public DriverAdapter(List<Driver> driverList, OnDriverClickListener onDriverClickListener) {
        if (driverList != null) {
            this.driverList = driverList;
        }
        this.onDriverClickListener = onDriverClickListener;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.bind(driver);
    }

    @Override
    public int getItemCount() {
        return driverList.isEmpty() ? 0 : driverList.size(); // Safe check for empty list
    }

    // ViewHolder class to hold driver views
    public class DriverViewHolder extends RecyclerView.ViewHolder {

        private TextView textDriverName, textVehicleDetails;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            textDriverName = itemView.findViewById(R.id.textDriverName);
            textVehicleDetails = itemView.findViewById(R.id.textVehicleDetails);

            itemView.setOnClickListener(v -> {
                if (onDriverClickListener != null) {
                    onDriverClickListener.onDriverClick(driverList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Driver driver) {
            textDriverName.setText(driver.getName());
            textVehicleDetails.setText(driver.getVehicleModel() + " - " + driver.getVehicleColor());
        }
    }

    // Interface for handling driver clicks
    public interface OnDriverClickListener {
        void onDriverClick(Driver driver);
    }
}

package com.example.proekt1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private List<Vehicle> vehicleList;

    public VehicleAdapter(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.tvModel.setText(vehicle.getModel());
        holder.tvYear.setText(vehicle.getYear());
        holder.tvPlate.setText(vehicle.getPlate());
        holder.tvAvailability.setText(vehicle.getAvailability());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {

        TextView tvModel, tvYear, tvPlate, tvAvailability;

        public VehicleViewHolder(View itemView) {
            super(itemView);
            tvModel = itemView.findViewById(R.id.tvVehicleModel);
            tvYear = itemView.findViewById(R.id.tvVehicleYear);
            tvPlate = itemView.findViewById(R.id.tvVehiclePlate);
            tvAvailability = itemView.findViewById(R.id.tvVehicleAvailability);
        }
    }
}


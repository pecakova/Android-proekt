package com.example.proekt1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proekt1.models.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context context;
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.context = context;
        this.tripList = tripList;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.textFrom.setText(trip.getFrom());
        holder.textTo.setText(trip.getTo());
        holder.textDriver.setText(trip.getDriverName());
        holder.textVehicle.setText(trip.getVehicleModel());
    }


    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView textFrom, textTo, textDriver, textVehicle;

        public TripViewHolder(View itemView) {
            super(itemView);
            textFrom = itemView.findViewById(R.id.textFrom);
            textTo = itemView.findViewById(R.id.textTo);
            textDriver = itemView.findViewById(R.id.textDriver);
            textVehicle = itemView.findViewById(R.id.textVehicle);
        }
    }
}

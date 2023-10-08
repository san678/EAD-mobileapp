package com.example.mobileapp.Train_Management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {

    private List<Train> trainList;
    private LayoutInflater inflater;

    public TrainAdapter(Context context, List<Train> trainList) {
        this.inflater = LayoutInflater.from(context);
        this.trainList = trainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.train_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Train train = trainList.get(position);
        holder.trainIDTextView.setText(train.getTrainID());
        Log.d("TrainListAdapter", "Train ID set: " + train.getTrainID());

        holder.trainNameTextView.setText(train.getTrainName());
        Log.d("TrainListAdapter", "Train Name set: " + train.getTrainName());

        if (train.getDTime() != null) {
            holder.departureTimeTextView.setText(train.getDTime());
            Log.d("TrainListAdapter", "Departure Time set: " + train.getDTime());
        } else {
            holder.departureTimeTextView.setText("N/A");
            Log.d("TrainListAdapter", "Departure Time set to N/A");
        }

        if (train.getATime() != null) {
            holder.arrivalTimeTextView.setText(train.getATime());
            Log.d("TrainListAdapter", "Arrival Time set: " + train.getATime());
        } else {
            holder.arrivalTimeTextView.setText("N/A");
            Log.d("TrainListAdapter", "Arrival Time set to N/A");
        }

        holder.statusTextView.setText(train.getStatus());
        Log.d("TrainListAdapter", "Train Status: " + train.getStatus());
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trainIDTextView, trainNameTextView, departureTimeTextView, arrivalTimeTextView, statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trainIDTextView = itemView.findViewById(R.id.trainIDTextView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            departureTimeTextView = itemView.findViewById(R.id.departureTimeTextView);
            arrivalTimeTextView = itemView.findViewById(R.id.arrivalTimeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);

            trainIDTextView = itemView.findViewById(R.id.trainIDTextView);
            Log.d("TrainListAdapter", "trainIDTextView initialized: " + (trainIDTextView != null));

            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            Log.d("TrainListAdapter", "trainNameTextView initialized: " + (trainNameTextView != null));

            departureTimeTextView = itemView.findViewById(R.id.departureTimeTextView);
            Log.d("TrainListAdapter", "departureTimeTextView initialized: " + (departureTimeTextView != null));

            arrivalTimeTextView = itemView.findViewById(R.id.arrivalTimeTextView);
            Log.d("TrainListAdapter", "arrivalTimeTextView initialized: " + (arrivalTimeTextView != null));

            statusTextView = itemView.findViewById(R.id.statusTextView);
            Log.d("TrainListAdapter", "statusTextView initialized: " + (statusTextView != null));

        }
    }
}
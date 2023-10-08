package com.example.mobileapp.Train_Reservation_Management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;

import java.util.List;
public class TrainReservationAdapter extends RecyclerView.Adapter<TrainReservationAdapter.ViewHolder> {

    private List<TrainReservation> reservationList;
    private LayoutInflater inflater;
    private Context context;
    private String userID;

    public TrainReservationAdapter(Context context, List<TrainReservation> reservationList) {
        this.inflater = LayoutInflater.from(context);
        this.reservationList = reservationList;
        this.context = context;
        this.userID = userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reservation_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainReservation reservation = reservationList.get(position);

        Log.d("ReservationListAdapter", "Reservation ID: " + reservation.getID());

        holder.reservationTrainIDTextView.setText(reservation.getTrainID());
        holder.reservationBookingStatusTextView.setText(reservation.getBookingStatus());
        holder.reservationReservationDateTextView.setText(reservation.getFormattedReservationDate());
        holder.reservationBookingDateTextView.setText(reservation.getBookingDate());

        Button updateButton = holder.itemView.findViewById(R.id.updateButton);
        Button viewButton = holder.itemView.findViewById(R.id.viewButton);
        Button cancelButton = holder.itemView.findViewById(R.id.cancelButton);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateReservationActivity(reservation);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewReservationActivity(reservation);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReservation(reservation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reservationTrainIDTextView, reservationBookingStatusTextView, reservationReservationDateTextView, reservationBookingDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reservationTrainIDTextView = itemView.findViewById(R.id.reservationTrainIDTextView);
            reservationBookingStatusTextView = itemView.findViewById(R.id.reservationBookingStatusTextView);
            reservationReservationDateTextView = itemView.findViewById(R.id.reservationReservationDateTextView);
            reservationBookingDateTextView = itemView.findViewById(R.id.reservationBookingDateTextView);
        }
    }

    private void cancelReservation(TrainReservation reservation) {
        TrainReservationApiClient.cancelReservationFromAPI(reservation, new TrainReservationApiClient.OnReservationCanceledListener() {
            @Override
            public void onReservationCanceled() {
                reservationList.remove(reservation);
                notifyDataSetChanged();
                Toast.makeText(context, "Reservation canceled successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "You can only cancel reservations at least 5 days before the reservation date.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openUpdateReservationActivity(TrainReservation reservation) {
        Intent intent = new Intent(context, UpdateTrainReservation.class);
        intent.putExtra("reservation", reservation);
        intent.putExtra("userID", userID);
        ((Activity) context).startActivityForResult(intent, 1001);
    }

    private void openViewReservationActivity(TrainReservation reservation) {
        Intent intent = new Intent(context, TrainReservationView.class);
        intent.putExtra("reservation", reservation);
        ((Activity) context).startActivityForResult(intent, 1001);
    }
}
package com.example.mobileapp.Train_Reservation_Management;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.Train_Management.TrainDetails;
import com.example.mobileapp.User_Management.ProfileActivity;
import com.example.mobileapp.User_Management.SignInActivity;

public class TrainReservationView extends AppCompatActivity {

    private TextView travelerNameTextText, nicTextText, trainIDTextText, departureLocationTextText,
            destinationLocationTextText, numPassengersTextText, ageTextText, ticketClassTextText,
            seatSelectionTextText, emailTextText, phoneTextText, reservationDateTextText, bookingDateTextText;
    private TrainReservation reservation;

    private Toolbar toolbar;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Reservation Details");

        reservation = (TrainReservation) getIntent().getSerializableExtra("reservation");

        travelerNameTextText = findViewById(R.id.travelerNameTextText);
        nicTextText = findViewById(R.id.nicTextText);
        trainIDTextText = findViewById(R.id.trainIDTextText);
        departureLocationTextText = findViewById(R.id.departureLocationTextText);
        destinationLocationTextText = findViewById(R.id.destinationLocationTextText);
        numPassengersTextText = findViewById(R.id.numPassengersTextText);
        ageTextText = findViewById(R.id.ageTextText);
        ticketClassTextText = findViewById(R.id.ticketClassTextText);
        seatSelectionTextText = findViewById(R.id.seatSelectionTextText);
        emailTextText = findViewById(R.id.emailTextText);
        phoneTextText = findViewById(R.id.phoneTextText);
        reservationDateTextText = findViewById(R.id.reservationDateTextText);

        populateFields();

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);

        userID = getIntent().getStringExtra("userID");

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationView.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationView.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationView.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationView.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationView.this, ProfileActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void populateFields() {
        travelerNameTextText.setText(reservation.getTravelerName());
        nicTextText.setText(reservation.getNIC());
        trainIDTextText.setText(reservation.getTrainID());
        departureLocationTextText.setText(reservation.getDepartureLocation());
        destinationLocationTextText.setText(reservation.getDestinationLocation());
        numPassengersTextText.setText(String.valueOf(reservation.getNumPassengers()));
        ageTextText.setText(String.valueOf(reservation.getAge()));
        ticketClassTextText.setText(reservation.getTicketClass());
        seatSelectionTextText.setText(reservation.getSeatSelection());
        emailTextText.setText(reservation.getEmail());
        phoneTextText.setText(reservation.getPhone());
        reservationDateTextText.setText(reservation.getFormattedReservationDate());
    }

    private void updateReservation() {
        // Retrieve updated data from TextText fields
        String travelerName = travelerNameTextText.getText().toString();
        String nic = nicTextText.getText().toString();
        String trainID = trainIDTextText.getText().toString();
        String departureLocation = departureLocationTextText.getText().toString();
        String destinationLocation = destinationLocationTextText.getText().toString();
        int numPassengers = Integer.parseInt(numPassengersTextText.getText().toString());
        int age = Integer.parseInt(ageTextText.getText().toString());
        String ticketClass = ticketClassTextText.getText().toString();
        String seatSelection = seatSelectionTextText.getText().toString();
        String email = emailTextText.getText().toString();
        String phone = phoneTextText.getText().toString();
        String reservationDate = reservationDateTextText.getText().toString();

        TrainReservation updatedReservation = new TrainReservation(reservation.getID(), travelerName, nic, reservation.getUserId(), trainID, reservation.getBookingStatus(),
                departureLocation, destinationLocation, numPassengers, age, ticketClass, seatSelection, email, phone, reservationDate, reservation.getBookingDate(), reservation.getFormattedReservationDate(), reservation.getFormattedBookingDate());

        TrainReservationApiClient.updateReservationInAPI(updatedReservation, new TrainReservationApiClient.OnReservationUpdatedListener() {
            @Override
            public void onReservationUpdated() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(TrainReservationView.this, "Error updating reservation", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
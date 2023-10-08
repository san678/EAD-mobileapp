package com.example.mobileapp.Train_Reservation_Management;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class UpdateTrainReservation extends AppCompatActivity {

    private EditText travelerNameEditText, nicEditText, trainIDEditText, departureLocationEditText,
            destinationLocationEditText, numPassengersEditText, ageEditText, ticketClassEditText,
            seatSelectionEditText, emailEditText, phoneEditText, bookingDateEditText;
    private TextView reservationDateTextView;
    private Button updateButton;
    private TrainReservation reservation;

    private Toolbar toolbar;
    private String userID;

    private Spinner ticketClassSpinner;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Update Reservation");

        reservation = (TrainReservation) getIntent().getSerializableExtra("reservation");

        userID = getIntent().getStringExtra("userID");
        Log.d("ReservationDetailActivity", "Received userID: " + userID);

        travelerNameEditText = findViewById(R.id.travelerNameEditText);
        nicEditText = findViewById(R.id.nicEditText);
        trainIDEditText = findViewById(R.id.trainIDEditText);
        departureLocationEditText = findViewById(R.id.departureLocationEditText);
        destinationLocationEditText = findViewById(R.id.destinationLocationEditText);
        numPassengersEditText = findViewById(R.id.numPassengersEditText);
        ageEditText = findViewById(R.id.ageEditText);
        ticketClassSpinner = findViewById(R.id.ticketClassSpinner);
        seatSelectionEditText = findViewById(R.id.seatSelectionEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        reservationDateTextView = findViewById(R.id.editTextReservationDate);
        updateButton = findViewById(R.id.updateButton);

        populateFields();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReservation();
            }
        });

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrainReservation.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrainReservation.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrainReservation.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrainReservation.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrainReservation.this, ProfileActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });

        reservationDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Spinner ticketClassSpinner = findViewById(R.id.ticketClassSpinner);
        String[] ticketClasses = getResources().getStringArray(R.array.ticket_classes); // Replace with your array resource
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ticketClasses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketClassSpinner.setAdapter(adapter);
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

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Handle the selected date
            String formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
            reservationDateTextView.setText(formattedDate);
        }
    };

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener, // Set the listener
                // Set the initial date (optional)
                2023, 9, 17 // Year, month (0-based), day
        );
        datePickerDialog.show();
    }

    private void signOut() {
        Intent intentone = new Intent(this, SignInActivity.class);
        startActivity(intentone);
        finish();
    }

    private void populateFields() {
        travelerNameEditText.setText(reservation.getTravelerName());
        nicEditText.setText(reservation.getNIC());
        trainIDEditText.setText(reservation.getTrainID());
        departureLocationEditText.setText(reservation.getDepartureLocation());
        destinationLocationEditText.setText(reservation.getDestinationLocation());
        numPassengersEditText.setText(String.valueOf(reservation.getNumPassengers()));
        ageEditText.setText(String.valueOf(reservation.getAge()));
//        ticketClassEditText.setText(reservation.getTicketClass());
        seatSelectionEditText.setText(reservation.getSeatSelection());
        emailEditText.setText(reservation.getEmail());
        phoneEditText.setText(reservation.getPhone());
        reservationDateTextView.setText(reservation.getReservationDate());
    }

    private void updateReservation() {
        // Retrieve updated data from EditText fields
        String travelerName = travelerNameEditText.getText().toString();
        String nic = nicEditText.getText().toString();
        String trainID = trainIDEditText.getText().toString();
        String departureLocation = departureLocationEditText.getText().toString();
        String destinationLocation = destinationLocationEditText.getText().toString();
        int numPassengers = Integer.parseInt(numPassengersEditText.getText().toString());
        int age = Integer.parseInt(ageEditText.getText().toString());
        String ticketClass = ticketClassSpinner.getSelectedItem().toString();
        String seatSelection = seatSelectionEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String reservationDate = reservationDateTextView.getText().toString();

        // Validate numPassengers
        if (numPassengers < 1 || numPassengers > 4) {
            numPassengersEditText.setError("Number of passengers must be between 1 and 4.");
            Toast.makeText(this, "Number of passengers must be between 1 and 4.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate ticketClass
        if (!ticketClass.matches("[A-Ca-c]")) {
            ticketClassEditText.setError("Ticket class must be A, B, or C.");
            Toast.makeText(this, "Ticket class must be A, B, or C.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nic.matches("\\d{10,12}")) {
            // NIC validation failed
            nicEditText.setError("NIC must have 10 to 12 digits.");
            Toast.makeText(this, "NIC must have 10 to 12 digits.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.matches("\\d{10}")) {
            // Phone validation failed
            phoneEditText.setError("Phone must have 10 digits.");
            Toast.makeText(this, "Phone must have 10 digits.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Email validation failed
            emailEditText.setError("Invalid email address.");
            Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        TrainReservation updatedReservation = new TrainReservation(reservation.getID(), travelerName, nic, reservation.getUserId(), trainID, reservation.getBookingStatus(),
                departureLocation, destinationLocation, numPassengers, age, ticketClass, seatSelection, email, phone, reservationDate, reservation.getBookingDate(), reservation.getFormattedReservationDate(), reservation.getFormattedBookingDate());

        TrainReservationApiClient.updateReservationInAPI(updatedReservation, new TrainReservationApiClient.OnReservationUpdatedListener() {
            @Override
            public void onReservationUpdated() {
                Intent intent = new Intent(UpdateTrainReservation.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

                Toast.makeText(UpdateTrainReservation.this, "Reservation updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Finish the current activity to prevent going back to it using the back button
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(UpdateTrainReservation.this, "You can only update reservations at least 5 days before the reservation date", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
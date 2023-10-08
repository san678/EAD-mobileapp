package com.example.mobileapp.Train_Reservation_Management;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.Train_Management.TrainDetails;
import com.example.mobileapp.User_Management.ProfileActivity;
import com.example.mobileapp.User_Management.SignInActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class TrainReservationActivity extends AppCompatActivity {

    private EditText editTextTravelerName;
    private EditText editTextNIC;
    private EditText editTextTrainID;
    private EditText editTextDepartureLocation;
    private EditText editTextDestinationLocation;
    private EditText editTextNumPassengers;
    private EditText editTextAge;
    private EditText editTextTicketClass;
    private EditText editTextSeatSelection;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private TextView textviewReservationDate;

    private Toolbar toolbar;

    private Spinner ticketClassSpinner;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Add Reservation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        editTextTravelerName = findViewById(R.id.editTextTravelerName);
        editTextNIC = findViewById(R.id.editTextNIC);
        editTextTrainID = findViewById(R.id.editTextTrainID);
        editTextDepartureLocation = findViewById(R.id.editTextDepartureLocation);
        editTextDestinationLocation = findViewById(R.id.editTextDestinationLocation);
        editTextNumPassengers = findViewById(R.id.editTextNumPassengers);
        editTextAge = findViewById(R.id.editTextAge);
        ticketClassSpinner = findViewById(R.id.ticketClassSpinner);
        editTextSeatSelection = findViewById(R.id.editTextSeatSelection);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        textviewReservationDate = findViewById(R.id.editTextReservationDate);

        Button buttonSubmitReservation = findViewById(R.id.buttonSubmitReservation);
        buttonSubmitReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReservation();
            }
        });

        userID = getIntent().getStringExtra("userID");
        Log.d("ReservationActivity", "Received userID: " + userID);

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationActivity.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationActivity.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationActivity.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationActivity.this, ProfileActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });

        ImageButton calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Spinner ticketClassSpinner = findViewById(R.id.ticketClassSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ticket_classes, android.R.layout.simple_spinner_item);
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

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle the selected date
                        String formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        textviewReservationDate.setText(formattedDate);
                    }
                },
                // Set the initial date (optional)
                2023, 9, 17 // Year, month (0-based), day
        );
        datePickerDialog.show();
    }



    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity if needed
    }

    private void createReservation() {
        String travelerName = editTextTravelerName.getText().toString();
        Log.d("ReservationActivity", "Traveler Name: " + travelerName);

        String nic = editTextNIC.getText().toString();
        Log.d("ReservationActivity", "NIC: " + nic);

        String userID = getIntent().getStringExtra("userID");
        Log.d("ReservationActivity", "User ID: " + userID);

        String trainID = editTextTrainID.getText().toString();
        Log.d("ReservationActivity", "Train ID: " + trainID);

        String departureLocation = editTextDepartureLocation.getText().toString();
        Log.d("ReservationActivity", "Departure Location: " + departureLocation);

        String destinationLocation = editTextDestinationLocation.getText().toString();
        Log.d("ReservationActivity", "Destination Location: " + destinationLocation);

        int age = Integer.parseInt(editTextAge.getText().toString());
        Log.d("ReservationActivity", "Age: " + age);

        String seatSelection = editTextSeatSelection.getText().toString();
        Log.d("ReservationActivity", "Seat Selection: " + seatSelection);

        String bookingStatus = "Active";
        Log.d("ReservationActivity", "booking Status: " + bookingStatus);

        String email = editTextEmail.getText().toString();
        Log.d("ReservationActivity", "Email: " + email);

        String phone = editTextPhone.getText().toString();
        Log.d("ReservationActivity", "Phone: " + phone);

        String reservationDate = textviewReservationDate.getText().toString();
        Log.d("ReservationActivity", "Reservation Date: " + reservationDate);


        LocalDateTime currentDateTime = LocalDateTime.now();


        String bookingDate = currentDateTime.toString();


        Log.d("ReservationActivity", "booking Date: " + bookingDate);


        if (!nic.matches("\\d{10,12}")) {
            // NIC validation failed
            editTextNIC.setError("NIC must have 10 to 12 digits.");
            Toast.makeText(this, "NIC must have 10 to 12 digits.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!phone.matches("\\d{10}")) {
            // Phone validation failed
            editTextPhone.setError("Phone must have 10 digits.");
            Toast.makeText(this, "Phone must have 10 digits.", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Email validation failed
            editTextEmail.setError("Invalid email address.");
            Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        int numPassengers;
        try {
            numPassengers = Integer.parseInt(editTextNumPassengers.getText().toString());
            Log.d("ReservationActivity", "Number of Passengers: " + numPassengers);
        } catch (NumberFormatException e) {
            editTextNumPassengers.setError("Invalid number of passengers");
            Toast.makeText(this, "Invalid number of passengers", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numPassengers < 1 || numPassengers > 4) {
            editTextNumPassengers.setError("Number of passengers must be between 1 and 4");
            Toast.makeText(this, "Number of passengers must be between 1 and 4", Toast.LENGTH_SHORT).show();
            return;
        }

        String ticketClass = ticketClassSpinner.getSelectedItem().toString();

        TrainReservation reservation = new TrainReservation(null, travelerName, nic, userID, trainID, bookingStatus,
                departureLocation, destinationLocation, numPassengers, age,
                ticketClass, seatSelection, email, phone, reservationDate, bookingDate, "", "");

        CreateReservationTask task = new CreateReservationTask(userID);
        task.execute(reservation);


        Log.d("ReservationActivity", "Reservation: " + reservation);
    }

    private class CreateReservationTask extends AsyncTask<TrainReservation, Void, String> {
        private String userID;

        public CreateReservationTask(String userID) {
            this.userID = userID;
        }

        @Override
        protected String doInBackground(TrainReservation... reservations) {
            TrainReservation reservation = reservations[0];
            try {
                URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/bookings/add");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);


                reservation.setUserId(userID);


                Gson gson = new Gson();
                String jsonInputString = gson.toJson(reservation);
                Log.d("ReservationActivity", "jsonInputString: " + jsonInputString);
                OutputStream os = connection.getOutputStream();
                os.write(jsonInputString.getBytes("utf-8"));

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {

                Log.d("ReservationActivity", "Reservation created successfully. Response: " + response);
                Toast.makeText(TrainReservationActivity.this, "Reservation created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrainReservationActivity.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            } else {

                Log.e("ReservationActivity", "Error creating reservation.");
                Toast.makeText(TrainReservationActivity.this, "Reservation date must be within 30 days from the booking date.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
package com.example.mobileapp.Train_Reservation_Management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.Train_Management.TrainDetails;
import com.example.mobileapp.User_Management.ProfileActivity;
import com.example.mobileapp.User_Management.SignInActivity;

import java.util.List;

public class TrainReservationDetails extends AppCompatActivity implements TrainReservationApiClient.OnReservationDataReceivedListener {

    private RecyclerView recyclerView;
    private TrainReservationAdapter reservationListAdapter;
    private TrainReservationApiClient reservationApiClient;
    private List<TrainReservation> reservationList;
    private Toolbar toolbar;
    private String userID;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Reservation List");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.reservationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reservationApiClient = new TrainReservationApiClient();

        userID = getIntent().getStringExtra("userID");
        Log.d("ReservationDetailActivity", "Send userID: " + userID);

        reservationApiClient.getReservationsForUserFromAPI(userID, this);

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationDetails.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationDetails.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationDetails.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationDetails.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainReservationDetails.this, ProfileActivity.class);
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

    @Override
    public void onReservationDataReceived(List<TrainReservation> reservationList) {
        Log.d("ReservationDetails", "Received: " + reservationList.size());

        for (TrainReservation reservation : reservationList) {
            Log.d("ReservationDetails", "Reservation ID: " + reservation.getID());
        }

        reservationListAdapter = new TrainReservationAdapter(this, reservationList);
        reservationListAdapter.setUserID(userID); // Set the userID in the adapter
        recyclerView.setAdapter(reservationListAdapter);
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("ReservationListActivity", errorMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            TrainReservation updatedReservation = (TrainReservation) data.getSerializableExtra("updatedReservation");
            String updatedUserID = data.getStringExtra("userID"); // Renamed to updatedUserID

            int position = reservationList.indexOf(updatedReservation);
            if (position != -1) {
                reservationList.set(position, updatedReservation);
                reservationListAdapter.notifyItemChanged(position);
                reservationListAdapter.setUserID(updatedUserID); // Set the userID in the adapter
            }
        }
    }
}
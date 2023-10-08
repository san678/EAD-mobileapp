package com.example.mobileapp.Train_Management;

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
import com.example.mobileapp.Train_Reservation_Management.TrainReservationActivity;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationDetails;
import com.example.mobileapp.User_Management.ProfileActivity;
import com.example.mobileapp.User_Management.SignInActivity;

import java.util.List;

public class TrainDetails extends AppCompatActivity implements TrainAPI.OnTrainDataReceivedListener {

    private RecyclerView recyclerView;
    private TrainAdapter trainListAdapter;
    private TrainAPI trainApiClient;
    private Toolbar toolbar;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Train List");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.trainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainApiClient = new TrainAPI();
        trainApiClient.getActiveTrainsFromAPI(this);

        userID = getIntent().getStringExtra("userID");

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainDetails.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainDetails.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainDetails.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainDetails.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainDetails.this, ProfileActivity.class);
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
    public void onTrainDataReceived(List<Train> trainList) {
        trainListAdapter = new TrainAdapter(this, trainList);
        recyclerView.setAdapter(trainListAdapter);
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("TrainListActivity", errorMessage);
    }
}
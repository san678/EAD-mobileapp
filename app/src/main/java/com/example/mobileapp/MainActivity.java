package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.mobileapp.Train_Reservation_Management.TrainReservationActivity;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationDetails;
import com.example.mobileapp.Train_Management.TrainDetails;
import com.example.mobileapp.User_Management.ProfileActivity;
import com.example.mobileapp.User_Management.SignInActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Home");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        String userID = getIntent().getStringExtra("userID");
        Log.d("mainActivity", "Received userID: " + userID);

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                Log.d("mainActivity", "Received userID: " + userID);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                Log.d("mainActivity", "Received userID: " + userID);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                Log.d("mainActivity", "Received userID: " + userID);
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                Log.d("mainActivity", "Received userID: " + userID);
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
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
}
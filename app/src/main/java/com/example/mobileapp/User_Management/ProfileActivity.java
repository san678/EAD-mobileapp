package com.example.mobileapp.User_Management;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationActivity;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationDetails;
import com.example.mobileapp.Train_Management.TrainDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView nicTextView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private TextView usernameTextView;
    private TextView contactNumberTextView;

    private User user;
    private Toolbar toolbar;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("My Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        userID = getIntent().getStringExtra("userID");

        Button updateButton = findViewById(R.id.updateButton);
        Button deactivateButton = findViewById(R.id.deactivateButton);

        nicTextView = findViewById(R.id.nicTextView);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        contactNumberTextView = findViewById(R.id.contactNumberTextView);

        Log.d("ProfileActivity", "Starting ProfileActivity with User ID: " + userID);

        new FetchUserDataTask().execute(userID);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    Log.d("ProfileActivity", "User ID: " + userID);
                    Intent intent = new Intent(ProfileActivity.this, UpdateUser.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, 1001);
                } else {
                    Toast.makeText(ProfileActivity.this, "User data not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    new DeactivateUserTask().execute(userID);
                    Log.d("DeactivateUserTask", "Getid: " + userID);
                } else {
                    Toast.makeText(ProfileActivity.this, "User data not available", Toast.LENGTH_SHORT).show();
                }
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
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);

                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);

                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TrainDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
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

    private class DeactivateUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userID = params[0];
            Log.d("DeactivateUserTask", "Userid: " + userID);
            return deactivateUser(userID);
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && response.equals("Success")) {
                Toast.makeText(ProfileActivity.this, "User deactivated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(ProfileActivity.this, "Error deactivating user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String deactivateUser(String userID) {
        try {
            URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/updatestatusbyid/" + userID);
            Log.d("DeactivateUserTask", "Url: " + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonStatusData = new JSONObject();
            jsonStatusData.put("UserStatus", "Inactive");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonStatusData.toString().getBytes("UTF-8"));
            outputStream.close();

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            Log.d("DeactivateUserTask", "Response Code: " + responseCode);
            Log.d("DeactivateUserTask", "Response Message: " + responseMessage);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "Success";
            } else {
                return "Error. Response code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DeactivateUserTask", "Error deactivating user: " + e.getMessage());
            return "Error. Exception: " + e.getMessage();
        }
    }

    private class FetchUserDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String userID = params[0];
            String apiUrl = "http://hasthikagamala-001-site1.btempurl.com/api/users/get/" + userID;
            Log.d("ProfileActivity", "Getapi: " + apiUrl);

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    return new JSONObject(responseData);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject userData) {
            if (userData != null) {
                try {
                    String userID = userData.getString("ID");
                    String NIC = userData.getString("NIC");
                    String firstName = userData.getString("FirstName");
                    String lastName = userData.getString("LastName");
                    String username = userData.getString("UserName");
                    String email = userData.getString("Email");
                    String contactNumber = userData.getString("PhoneNumber");

                    Log.d("FetchUserDataTask", "User Data Retrieved:");
                    Log.d("FetchUserDataTask", "User ID: " + userID);
                    Log.d("FetchUserDataTask", "NIC: " + NIC);
                    Log.d("FetchUserDataTask", "First Name: " + firstName);
                    Log.d("FetchUserDataTask", "Last Name: " + lastName);
                    Log.d("FetchUserDataTask", "UserName: " + username);
                    Log.d("FetchUserDataTask", "Email: " + email);
                    Log.d("FetchUserDataTask", "Contact Number: " + contactNumber);

                    user = new User( userID, NIC, firstName, lastName, username, email, "", "", contactNumber);

                    nicTextView.setText("NIC: " + NIC);
                    firstNameTextView.setText("First Name: " + firstName);
                    lastNameTextView.setText("Last Name: " + lastName);
                    usernameTextView.setText("UserName: " + username);
                    emailTextView.setText("Email: " + email);
                    contactNumberTextView.setText("Contact Number: " + contactNumber);

                    Log.d("FetchUserDataTask", "User Data Retrieved:");
                    Log.d("FetchUserDataTask", "User ID: " + userID);
                    Log.d("FetchUserDataTask", "NIC: " + NIC);
                    Log.d("FetchUserDataTask", "First Name: " + firstName);
                    Log.d("FetchUserDataTask", "Last Name: " + lastName);
                    Log.d("FetchUserDataTask", "UserName: " + username);
                    Log.d("FetchUserDataTask", "Email: " + email);
                    Log.d("FetchUserDataTask", "Contact Number: " + contactNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            String userID = getIntent().getStringExtra("userID");
            new FetchUserDataTask().execute(userID);
        }
    }
}
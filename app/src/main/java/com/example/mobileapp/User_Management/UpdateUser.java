package com.example.mobileapp.User_Management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationActivity;
import com.example.mobileapp.Train_Reservation_Management.TrainReservationDetails;
import com.example.mobileapp.Train_Management.TrainDetails;

public class UpdateUser extends AppCompatActivity {

    private EditText updatedUserNameEditText, updatedFirstNameEditText, updatedLastNameEditText, updatedPhoneNumberEditText, updatedEmailEditText;
    private Button updateButton;
    private User user;
    private Toolbar toolbar;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Update Profile");

        user = (User) getIntent().getSerializableExtra("user");

        userID = getIntent().getStringExtra("userID");


        updatedFirstNameEditText = findViewById(R.id.updatedFirstNameEditText);
        updatedLastNameEditText = findViewById(R.id.updatedLastNameEditText);
        updatedUserNameEditText = findViewById(R.id.updatedUserNameEditText);
        updatedEmailEditText = findViewById(R.id.updatedEmailEditText);
        updatedPhoneNumberEditText = findViewById(R.id.updatedPhoneNumberEditText);
        updateButton = findViewById(R.id.updateButton);

        populateFields();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        String userID = getIntent().getStringExtra("userID");

        ImageButton Button1 = findViewById(R.id.button1);
        ImageButton Button2 = findViewById(R.id.button2);
        ImageButton Button3 = findViewById(R.id.button3);
        ImageButton Button4 = findViewById(R.id.button4);
        ImageButton Button5 = findViewById(R.id.button5);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, TrainReservationActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, TrainReservationDetails.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, TrainDetails.class);
                intent.putExtra("userID", userID);

                startActivity(intent);

            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, ProfileActivity.class);
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
        String ID = user.getID();
        String NIC = user.getNIC();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();
        String contactNumber = user.getPhoneNumber();

        Log.d("UpdateUserActivity", "Populating fields with data:");
        Log.d("UpdateUserActivity", "Id: " + ID);
        Log.d("UpdateUserActivity", "Nic: " + NIC);
        Log.d("UpdateUserActivity", "First Name: " + firstName);
        Log.d("UpdateUserActivity", "Last Name: " + lastName);
        Log.d("UpdateUserActivity", "UserName: " + userName);
        Log.d("UpdateUserActivity", "Email: " + email);
        Log.d("UpdateUserActivity", "Contact Number: " + contactNumber);

        updatedFirstNameEditText.setText(firstName);
        updatedLastNameEditText.setText(lastName);
        updatedUserNameEditText.setText(userName);
        updatedEmailEditText.setText(email);
        updatedPhoneNumberEditText.setText(contactNumber);
    }

    private boolean isValidNIC(String nic) {
        return nic.matches("[0-9]+") && nic.length() >= 10 && nic.length() <= 12;
    }

    private boolean isValidPhoneNumber(String contactNumber) {
        return contactNumber.matches("[0-9]+") && contactNumber.length() == 10;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return email.matches(emailRegex);
    }

    private void updateUser() {

        String firstName = updatedFirstNameEditText.getText().toString();
        String lastName = updatedLastNameEditText.getText().toString();
        String userName = updatedUserNameEditText.getText().toString();
        String email = updatedEmailEditText.getText().toString();
        String contactNumber = updatedPhoneNumberEditText.getText().toString();

        if (!isValidNIC(user.getNIC())) {
            Toast.makeText(UpdateUser.this, "Invalid NIC format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhoneNumber(contactNumber)) {
            Toast.makeText(UpdateUser.this, "Invalid contact number format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(UpdateUser.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }


        Log.d("UpdateUserActivity", "FirstName: " + firstName);
        Log.d("UpdateUserActivity", "LastName: " + lastName);
        Log.d("UpdateUserActivity", "UserName: " + userName);
        Log.d("UpdateUserActivity", "Email: " + email);
        Log.d("UpdateUserActivity", "PhoneNumber: " + contactNumber);
        Log.d("UpdateUserActivity", "ID: " + user.getID());

        User updatedUser = new User(user.getID(), user.getNIC(), firstName, lastName, userName, email, user.getPassword(), user.getRePassword(), contactNumber);

        Log.d("UpdateUserActivity", "UpdatedUser: " + updatedUser);

        UserAPI.updateUserInAPI(updatedUser, new UserAPI.OnUserUpdatedListener() {
            @Override
            public void onUserUpdated() {
                setResult(RESULT_OK);
                finish();
                Toast.makeText(UpdateUser.this, "User updated successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(UpdateUser.this, "Error updating user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
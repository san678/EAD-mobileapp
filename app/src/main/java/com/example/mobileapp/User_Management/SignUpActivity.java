package com.example.mobileapp.User_Management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.*;

public class SignUpActivity extends AppCompatActivity {

    private EditText etNIC, etFirstName, etLastName, etEmail, etUserName, etPassword, etRePassword, etPhoneNumber;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNIC = findViewById(R.id.etNIC);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        TextView txtSignIn = findViewById(R.id.txtSignIn);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUp() {
        String nic = etNIC.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String reenteredPassword = etRePassword.getText().toString().trim();
        String contactNumber = etPhoneNumber.getText().toString().trim();

        if (nic.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || reenteredPassword.isEmpty() || contactNumber.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(reenteredPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add NIC and contact number format validation
        if (!isValidNIC(nic)) {
            Toast.makeText(this, "Invalid NIC format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhoneNumber(contactNumber)) {
            Toast.makeText(this, "Invalid contact number format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePassword(password)) {
            Toast.makeText(this, "Password must be at least 8 characters long and include at least one lowercase letter, one uppercase letter, and one number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(reenteredPassword)) {

            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        // Hash the re-entered password
        String hashedRePassword = hashPassword(reenteredPassword);

        User user = new User("", nic, firstName, lastName, username, email, hashedPassword, hashedRePassword, contactNumber);


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(user));

        Request request = new Request.Builder()
                .url("http://hasthikagamala-001-site1.btempurl.com/api/users/signup")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String responseData = response.body().string();
                    SignUpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                } else {

                    final String errorResponse = response.body().string();
                    SignUpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(SignUpActivity.this, "Sign up failed: " + errorResponse, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isValidNIC(String nic) {
        return nic.matches("[0-9]+") && nic.length() >= 10 && nic.length() <= 12;
    }

    private boolean isValidPhoneNumber(String contactNumber) {
        return contactNumber.matches("[0-9]+") && contactNumber.length() == 10;
    }

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return email.matches(emailRegex);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hashString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hashString.append('0');
                hashString.append(hex);
            }

            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
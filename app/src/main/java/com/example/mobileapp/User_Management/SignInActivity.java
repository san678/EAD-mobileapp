package com.example.mobileapp.User_Management;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.MainActivity;
import com.example.mobileapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {
    private EditText nicEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        nicEditText = findViewById(R.id.nicEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nic = nicEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (nic.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "NIC and password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Hash the password
                String hashedPassword = hashPassword(password);

                JSONObject json = new JSONObject();
                try {
                    json.put("NIC", nic);
                    json.put("Password", hashedPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new SignInTask().execute(json.toString());
            }
        });

        TextView txtSignUp = findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
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

    private class SignInTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            String url = "http://hasthikagamala-001-site1.btempurl.com/api/users/signin";

            RequestBody body = RequestBody.create(JSON, params[0]);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                Log.d("SignInTask", "Response Data: " + responseData);


                if (response.isSuccessful()) {
                    JSONObject json = new JSONObject(responseData);
                    String userID = json.getString("ID");
                    return userID;
                } else {
                    return null;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String userID) {
            if (userID != null) {
                Toast.makeText(SignInActivity.this, "Sign-in successful!", Toast.LENGTH_SHORT).show();
                Log.d("SignInActivity", "User ID: " + userID);

                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            } else {
                Toast.makeText(SignInActivity.this, "Incorrect NIC or password or User account is deactivated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
package com.example.mobileapp.User_Management;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserAPI {

    private static final String API_URL = "http://hasthikagamala-001-site1.btempurl.com/api/users/getall"; // Replace with your API endpoint

    public interface OnUserDataReceivedListener {
        void onUserDataReceived(List<User> userList);
        void onError(String errorMessage);
    }

    public void getActiveUsersFromAPI(final OnUserDataReceivedListener listener) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(API_URL);
                    Log.d("UserApiClient", "Getallapi: " + API_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    return stringBuilder.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    try {
                        @SuppressLint("StaticFieldLeak") User user = parseJson(response);
                        List<User> userList = new ArrayList<>();
                        userList.add(user);
                        listener.onUserDataReceived(userList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError("Error parsing JSON");
                    }
                } else {
                    listener.onError("Error fetching data from API");
                }
            }
        };

        task.execute();
    }

    public static void updateUserInAPI(final User user, final OnUserUpdatedListener listener) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/updatebyid/" + user.getID());
                    Log.d("UpdateUserActivity", "Url: " + url);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.setRequestProperty("Content-Type", "application/json");

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("FirstName", user.getFirstName());
                    requestBody.put("LastName", user.getLastName());
                    requestBody.put("UserName", user.getUserName());
                    requestBody.put("email", user.getEmail());
                    requestBody.put("PhoneNumber", user.getPhoneNumber());

                    Log.d("UserApiClient", "Request Body: " + requestBody.toString());

                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(requestBody.toString().getBytes("UTF-8"));
                    os.close();

                    int responseCode = connection.getResponseCode();

                    Log.d("UserApiClient", "ResponseCode: " + responseCode);

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Success";
                    } else {
                        return "Error";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null && response.equals("Success")) {
                    listener.onUserUpdated();
                } else {
                    listener.onError("Error updating user");
                }
            }
        };

        task.execute();
    }

    public interface OnUserUpdatedListener {
        void onUserUpdated();
        void onError(String errorMessage);
    }

    public static void deleteUserFromAPI(final User user, final OnUserDeletedListener listener) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/delete/" + user.getID());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Success";
                    } else {
                        return "Error";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null && response.equals("Success")) {
                    listener.onUserDeleted();
                } else {
                    listener.onError("Error deleting user");
                }
            }
        };

        task.execute();
    }

    public interface OnUserDeletedListener {
        void onUserDeleted();
        void onError(String errorMessage);
    }

    private User parseJson(String json) throws JSONException {
        Log.d("UserApiClient", "Parsing JSON: " + json); // Log the JSON string being parsed

        JSONObject jsonObject = new JSONObject(json);

        String ID = jsonObject.getString("ID");
        String NIC = jsonObject.getString("NIC");
        String firstName = jsonObject.getString("FirstName");
        String lastName = jsonObject.getString("LastName");
        String userName = jsonObject.getString("UserName");
        String email = jsonObject.getString("Email");
        String password = jsonObject.getString("Password");
        String reenteredPassword = jsonObject.getString("RePassword");
        String contactNumber = jsonObject.getString("PhoneNumber");
        String userType = jsonObject.getString("UserType");
        String userStatus = jsonObject.getString("UserStatus");

        Log.d("UserApiClient", "Parsed User Data: ID=" + ID + ", NIC=" + NIC + ", UserName=" + userName +
                ", UserType=" + userType + ", FirstName=" + firstName + ", LastName=" + lastName +
                ", Email=" + email + ", PhoneNumber=" + contactNumber + ", Password=" + password +
                ", RePassword=" + reenteredPassword + ", UserStatus=" + userStatus); // Log the parsed user data

        User user = new User(ID, NIC, firstName, lastName, userName, email, password, reenteredPassword, contactNumber);
        return user;
    }
            private static String fetchNICFromAPI(String userID) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/getNIC/" + userID);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String nic = reader.readLine();

                    return nic;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

    public static String updateUserStatusToAPI(String userId, String status) {
        try {
            URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/updatestatusbyid/" + userId);
            Log.d("UserApiClient", "Statusapi: " + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonStatusData = new JSONObject();
            jsonStatusData.put("UserStatus", status);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonStatusData.toString().getBytes("UTF-8"));
            outputStream.close();

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            Log.d("UserApiClient", "Response Code: " + responseCode);
            Log.d("UserApiClient", "Response Message: " + responseMessage);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "Success";
            } else {
                return "Error. Response code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("UserApiClient", "Error updating user status: " + e.getMessage()); // Added log statement
            return "Error. Exception: " + e.getMessage();
        }
    }

    public void getUserByIDFromAPI(String userID, final OnUserDataReceivedListener listener) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/users/get/" + userID);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    String response = stringBuilder.toString();
                    Log.d("getUserByIDFromAPI", "Response: " + response); // Add this log statement

                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("getUserByIDFromAPI", "Error fetching data from API: " + e.getMessage()); // Add this log statement
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response != null) {
                    Log.d("UserApiClient", "API Response: " + response);
                    try {
                        User user = parseJson(response);
                        List<User> userList = new ArrayList<>();
                        userList.add(user); // Add user to list
                        listener.onUserDataReceived(userList); // Call listener with list containing user
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError("Error parsing JSON");
                    }
                } else {
                    listener.onError("Error fetching data from API");
                }
            }
        };

        task.execute();
    }
}
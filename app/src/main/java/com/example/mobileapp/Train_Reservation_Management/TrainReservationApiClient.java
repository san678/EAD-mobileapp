package com.example.mobileapp.Train_Reservation_Management;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrainReservationApiClient {

    public interface OnReservationDataReceivedListener {
        void onReservationDataReceived(List<TrainReservation> reservationList);
        void onError(String errorMessage);
    }

    public void getReservationsForUserFromAPI(String userId, final OnReservationDataReceivedListener listener) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... userId) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/bookings/getall" + "/" + userId[0]);
                    Log.d("ReservationApiClient", "API call made for userID: " + userId[0]);
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
                        List<TrainReservation> reservationList = parseJson(response);
                        Log.d("ReservationClient", "API Response: " + response);
                        listener.onReservationDataReceived(reservationList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onError("Error parsing JSON");
                    }
                } else {
                    listener.onError("Error fetching data from API");
                }
            }
        };

        task.execute(userId);
    }

    public static void cancelReservationFromAPI(final TrainReservation reservation, final OnReservationCanceledListener listener) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/bookings/cancel/" + reservation.getID());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.setRequestProperty("Content-Type", "application/json");

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("TravelerName", reservation.getTravelerName());
                    requestBody.put("NIC", reservation.getNIC());
                    requestBody.put("TrainID", reservation.getTrainID());
                    requestBody.put("BookingStatus", reservation.getBookingStatus());
                    requestBody.put("DepartureLocation", reservation.getDepartureLocation());
                    requestBody.put("DestinationLocation", reservation.getDestinationLocation());
                    requestBody.put("NumPassengers", reservation.getNumPassengers());
                    requestBody.put("Age", reservation.getAge());
                    requestBody.put("TicketClass", reservation.getTicketClass());
                    requestBody.put("SeatSelection", reservation.getSeatSelection());
                    requestBody.put("Email", reservation.getEmail());
                    requestBody.put("Phone", reservation.getPhone());
                    requestBody.put("bookingDate", reservation.getBookingDate());
                    requestBody.put("ReservationDate", reservation.getReservationDate());
                    requestBody.put("FormattedReservationDate", reservation.getFormattedReservationDate());
                    requestBody.put("FormattedBookingDate", reservation.getFormattedBookingDate());

                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(requestBody.toString().getBytes("UTF-8"));
                    os.close();

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
                    listener.onReservationCanceled();
                } else {
                    listener.onError("Error canceling reservation");
                }
            }
        };

        task.execute();
    }

    public interface OnReservationCanceledListener {
        void onReservationCanceled();
        void onError(String errorMessage);
    }

    private List<TrainReservation> parseJson(String json) throws JSONException {
        List<TrainReservation> reservationList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String ID = jsonObject.optString("ID");
                String travelerName = jsonObject.optString("TravelerName");
                String NIC = jsonObject.optString("NIC");
                String userID = jsonObject.optString("userID");
                String trainID = jsonObject.optString("TrainID");
                String bookingStatus = jsonObject.optString("BookingStatus");
                String departureLocation = jsonObject.optString("DepartureLocation");
                String destinationLocation = jsonObject.optString("DestinationLocation");
                int numPassengers = jsonObject.optInt("NumPassengers");
                int age = jsonObject.optInt("Age");
                String ticketClass = jsonObject.optString("TicketClass");
                String seatSelection = jsonObject.optString("SeatSelection");
                String email = jsonObject.optString("Email");
                String phone = jsonObject.optString("Phone");
                String reservationDate = jsonObject.optString("ReservationDate");
                String bookingDate = jsonObject.optString("FormattedBookingDate");
                String formattedReservationDate = jsonObject.optString("FormattedReservationDate");
                String formattedBookingDate = jsonObject.optString("FormattedBookingDate");
                Log.d("ReservationApiClient", "Parsed reservations: " + reservationList.size());

                TrainReservation reservation = new TrainReservation(ID, travelerName, NIC, userID, trainID, bookingStatus, departureLocation,
                        destinationLocation, numPassengers, age, ticketClass, seatSelection, email, phone, reservationDate, bookingDate, formattedReservationDate, formattedBookingDate);
                reservationList.add(reservation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ReservationApiClient", "Error parsing JSON: " + e.getMessage());
        }

        return reservationList;
    }

    public static void updateReservationInAPI(final TrainReservation reservation, final OnReservationUpdatedListener listener) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://hasthikagamala-001-site1.btempurl.com/api/bookings/update/" + reservation.getID());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.setRequestProperty("Content-Type", "application/json");

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("TravelerName", reservation.getTravelerName());
                    requestBody.put("NIC", reservation.getNIC());
                    requestBody.put("TrainID", reservation.getTrainID());
                    requestBody.put("BookingStatus", reservation.getBookingStatus());
                    requestBody.put("DepartureLocation", reservation.getDepartureLocation());
                    requestBody.put("DestinationLocation", reservation.getDestinationLocation());
                    requestBody.put("NumPassengers", reservation.getNumPassengers());
                    requestBody.put("Age", reservation.getAge());
                    requestBody.put("TicketClass", reservation.getTicketClass());
                    requestBody.put("SeatSelection", reservation.getSeatSelection());
                    requestBody.put("Email", reservation.getEmail());
                    requestBody.put("Phone", reservation.getPhone());
                    requestBody.put("ReservationDate", reservation.getReservationDate());
                    requestBody.put("FormattedReservationDate", reservation.getFormattedReservationDate());
                    requestBody.put("FormattedBookingDate", reservation.getFormattedBookingDate());

                    Log.d("ReservationApiClient", "TravelerName: " + reservation.getTravelerName());
                    Log.d("ReservationApiClient", "NIC: " + reservation.getNIC());
                    Log.d("ReservationApiClient", "TrainID: " + reservation.getTrainID());
                    Log.d("ReservationApiClient", "BookingStatus: " + reservation.getBookingStatus());
                    Log.d("ReservationApiClient", "DepartureLocation: " + reservation.getDepartureLocation());
                    Log.d("ReservationApiClient", "DestinationLocation: " + reservation.getDestinationLocation());
                    Log.d("ReservationApiClient", "NumPassengers: " + reservation.getNumPassengers());
                    Log.d("ReservationApiClient", "Age: " + reservation.getAge());
                    Log.d("ReservationApiClient", "TicketClass: " + reservation.getTicketClass());
                    Log.d("ReservationApiClient", "SeatSelection: " + reservation.getSeatSelection());
                    Log.d("ReservationApiClient", "Email: " + reservation.getEmail());
                    Log.d("ReservationApiClient", "Phone: " + reservation.getPhone());
                    Log.d("ReservationApiClient", "ReseravtinDate: " + reservation.getReservationDate());
                    Log.d("ReservationApiClient", "FormattedReservationDate: " + reservation.getFormattedReservationDate());
                    Log.d("ReservationApiClient", "FormattedBookingDate: " + reservation.getFormattedBookingDate());

                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(requestBody.toString().getBytes("UTF-8"));
                    os.close();

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
                    listener.onReservationUpdated();
                } else {
                    listener.onError("Error updating reservation");
                }
            }
        };

        task.execute();
    }

    public interface OnReservationUpdatedListener {
        void onReservationUpdated();
        void onError(String errorMessage);
    }
}
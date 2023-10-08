package com.example.mobileapp.Train_Management;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrainAPI {

    private static final String API_URL = "http://hasthikagamala-001-site1.btempurl.com/api/trains/getall";

    public interface OnTrainDataReceivedListener {
        void onTrainDataReceived(List<Train> trainList);
        void onError(String errorMessage);
    }

    public void getActiveTrainsFromAPI(final OnTrainDataReceivedListener listener) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(API_URL);
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
                        List<Train> trainList = parseJson(response);
                        listener.onTrainDataReceived(trainList);
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

    private List<Train> parseJson(String json) throws JSONException {
        List<Train> trainList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String trainID = jsonObject.getString("TrainID");
            Log.d("TrainDetails", "TrainID: " + trainID);

            String trainName = jsonObject.getString("TrainName");
            Log.d("TrainDetails", "TrainName: " + trainName);

            String departureTime = jsonObject.getString("DTime");
            Log.d("TrainDetails", "DTime: " + departureTime);

            String arrivalTime = jsonObject.getString("ATime");
            Log.d("TrainDetails", "ATime: " + arrivalTime);

            String status = jsonObject.getString("TrainStatus");
            Log.d("TrainDetails", "TrainStatus: " + status);

            Train train = new Train(trainID, trainName, departureTime, arrivalTime, status);
            trainList.add(train);

            Log.d("TrainApiClient","Train" + train);
        }

        return trainList;
    }
}
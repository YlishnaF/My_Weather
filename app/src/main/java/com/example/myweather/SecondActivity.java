package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission_group.LOCATION;

public class SecondActivity extends AppCompatActivity {
    Button bnOK;
    TextInputEditText locTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bnOK = findViewById(R.id.oklocationbtn);
        locTv = findViewById(R.id.location_et);

        bnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResult = new Intent();
                intentResult.putExtra("LOCATION", locTv.getText().toString());
                setResult(RESULT_OK,intentResult);
                finish();
//                try {
//                        Log.d("MyLog", "City " + ((TextInputEditText)findViewById(R.id.location_et)).getText());
//
//                        final String urls = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",((TextInputEditText)findViewById(R.id.location_et)).getText(), Key.WEATHER_API_KEY);
//                        final URL url = new URL(urls);
//
//                        final Handler handler = new Handler();
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("MyLog", "зашли в новый поток");
//                                HttpsURLConnection urlConnection = null;
//                                try {
//                                    urlConnection = (HttpsURLConnection) url.openConnection();
//                                    urlConnection.setRequestMethod("GET");
//                                    urlConnection.setReadTimeout(10000);
//                                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                                    String result = getLines(in);
//                                    Gson gson = new Gson();
//                                    final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
//
//                                    handler.post(new Runnable() {
//                                        @Override
//                                        public void run()
//                                        {
//                                            setData(weatherRequest);
//
//                                        }
//                                    });
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                } finally {
//                                    if (null != urlConnection) {
//                                        urlConnection.disconnect();
//                                    }
//                                }
//                            }
//                        }).start();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }

                }

//                private String getLines(BufferedReader in){
//                    return in.lines().collect(Collectors.joining("\n"));
//                }
//
//                private void setData(WeatherRequest weatherRequest){
//                    String city = (weatherRequest.getName());
//                    String temperature = String.valueOf(Math.round((weatherRequest.getMain().getTemp())-273.15));
//                    String pressure = String.format("%d", weatherRequest.getMain().getPressure());
//                    String humidity = String.format("%d", weatherRequest.getMain().getHumidity());
//                    String windSpeed = String.format("%d", weatherRequest.getWind().getSpeed());
//
//                    Intent intent = new Intent();
//                    intent.putExtra("City", city);
//                    intent.putExtra("temperature", temperature);
//                    intent.putExtra("pressure", pressure);
//                    intent.putExtra("humidity", humidity);
//                    intent.putExtra("windSpeed", windSpeed);
//
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }

        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("city", ((TextInputEditText)findViewById(R.id.location_et)).getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        locTv = findViewById(R.id.location_et);
        locTv.setText(savedInstanceState.getString("city"));
    }
}
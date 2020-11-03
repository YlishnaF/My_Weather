package com.example.myweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE = 5;
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid=";


    private TextView city;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView windSpeed;
    ImageView ivSearch;
    TextView loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ivSearch = findViewById(R.id.searchiv);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    private void init() {
            city = findViewById(R.id.locationshowtv);
            temperature = findViewById(R.id.textView2);
            pressure = findViewById(R.id.pressuretv);
            humidity = findViewById(R.id.humiditytv);
            windSpeed = findViewById(R.id.windtv);
            ImageView refresh = findViewById(R.id.refresh);
            refresh.setOnClickListener(clickListener);
        }

    View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
        public void onClick(View v) {

            try {
               Log.d("MyLog", "City " + ((TextView)findViewById(R.id.locationshowtv)).getText());

                final String urls = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",((TextView)findViewById(R.id.locationshowtv)).getText(), Key.WEATHER_API_KEY);
                final URL url = new URL(urls);

                final Handler handler = new Handler();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MyLog", "зашли в новый поток");
                        HttpsURLConnection urlConnection = null;
                        try {
                            urlConnection = (HttpsURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setReadTimeout(10000);
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            String result = getLines(in);
                            Gson gson = new Gson();
                            final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayWeather(weatherRequest);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != urlConnection) {
                                urlConnection.disconnect();
                            }
                        }
                    }
                }).start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        private String getLines(BufferedReader in){
            return in.lines().collect(Collectors.joining("\n"));
        }

        private void displayWeather(WeatherRequest weatherRequest){
            city.setText(weatherRequest.getName());
            temperature.setText(String.valueOf(Math.round((weatherRequest.getMain().getTemp())-273.15)));
            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
            windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loc = findViewById(R.id.locationshowtv);
//        temperature = findViewById(R.id.textView2);
//        pressure = findViewById(R.id.pressuretv);
        if (data == null) {
            return;
        }
        String city = data.getStringExtra("LOCATION");
        loc.setText(city);
//        String city = data.getStringExtra("City");
//        String temperature = data.getStringExtra("temperature");
//        String pressureS = data.getStringExtra("pressure");
//        loc.setText(city);
//        temperature.setText(temperature);
//        pressure.setText(pressureS);
    }
}




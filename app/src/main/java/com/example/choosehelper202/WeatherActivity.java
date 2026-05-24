package com.example.choosehelper202;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    private TextView tvWeather;
    private EditText etCity;
    private String tempStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tvWeather = findViewById(R.id.tv_weather);
        etCity = findViewById(R.id.et_city);
        Button btnRefresh = findViewById(R.id.btn_refresh);
        Button btnBack = findViewById(R.id.btn_back);
        Button btnToCloth = findViewById(R.id.btn_to_cloth);

        getWeather("beijing");
        btnRefresh.setOnClickListener(v -> {
            String city = etCity.getText().toString().trim();
            if(city.isEmpty()) city = "beijing";
            getWeather(city);
        });
        btnBack.setOnClickListener(v -> finish());
        btnToCloth.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClothActivity.class);
            intent.putExtra("temp", tempStr);
            startActivity(intent);
        });
    }

    private void getWeather(String city) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://wttr.in/"+city+"?format=j1&lang=zh")
                        .build();
                Response response = client.newCall(request).execute();
                JSONObject obj = new JSONObject(response.body().string());
                JSONObject current = obj.getJSONArray("current_condition").getJSONObject(0);

                String temp = current.getString("temp_C");
                String desc = current.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
                String humi = current.getString("humidity");
                String wind = current.getString("windspeedKmph");
                String feel = current.getString("FeelsLikeC");
                String visi = current.getString("visibility");
                tempStr = temp;

                new Handler(Looper.getMainLooper()).post(() -> {
                    tvWeather.setText("城市："+city+"\n天气："+desc+"\n温度："+temp+"℃\n体感："+feel+"℃\n湿度："+humi+"%\n风速："+wind+"km/h\n能见度："+visi+"km");
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> tvWeather.setText("获取失败，请输入拼音城市"));
            }
        }).start();
    }
}
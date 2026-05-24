package com.example.choosehelper202;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherActivity extends AppCompatActivity {
    EditText etCity;
    TextView tvResult, tvClothSuggest;
    Button btnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etCity = findViewById(R.id.et_city);
        tvResult = findViewById(R.id.tv_result);
        tvClothSuggest = findViewById(R.id.tv_cloth_suggest);
        btnQuery = findViewById(R.id.btn_query);

        btnQuery.setOnClickListener(v -> {
            String input = etCity.getText().toString().trim();
            final String city = input.isEmpty() ? "北京" : input;
            new Thread(() -> getWeather(city)).start();
        });
    }

    private void getWeather(String city) {
        try {
            // 免费可用天气接口（无KEY，国内可直接访问）
            String encodeCity = URLEncoder.encode(city, "UTF-8");
            String api = "https://api.66mz.cn/api/weather?city=" + encodeCity;

            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject json = new JSONObject(sb.toString());
            if (json.getInt("code") != 200) {
                runOnUiThread(() -> tvResult.setText("城市输入错误"));
                return;
            }

            JSONObject data = json.getJSONObject("data");
            String cityName = data.getString("city");
            String weather = data.getString("weather");
            String temperature = data.getString("temperature");
            String wind = data.getString("wind");

            // 穿搭建议
            String finalSuggest = getSuggest(weather, Double.parseDouble(temperature));

            runOnUiThread(() -> {
                tvResult.setText("城市：" + cityName
                        + "\n天气：" + weather
                        + "\n温度：" + temperature + "℃"
                        + "\n风向：" + wind);
                tvClothSuggest.setText("👕 穿搭建议：" + finalSuggest);
            });

        } catch (Exception e) {
            runOnUiThread(() -> tvResult.setText("获取失败：网络错误或城市错误"));
        }
    }

    private String getSuggest(String weather, double temp) {
        if (weather.contains("雨")) return "带伞，穿防水外套";
        if (weather.contains("雪")) return "穿羽绒服、保暖裤";
        if (temp >= 30) return "短袖、短裤、防晒";
        if (temp >= 20) return "衬衫、薄外套";
        if (temp >= 10) return "卫衣、针织衫";
        return "厚外套、注意保暖";
    }
}
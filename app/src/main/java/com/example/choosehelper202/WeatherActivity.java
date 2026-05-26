package com.example.choosehelper202;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    // UI 控件
    private TextView tvCity, tvTemp, tvWeather, tvWind, tvHumidity, tvUv, tvAdvice;
    private TextView tvDay1, tvTemp1, tvDay2, tvTemp2, tvDay3, tvTemp3;
    private EditText etCity;
    private Button btnSearch, btnGetLocation, btnToCloth;
    private ImageView bg;

    // 网络请求
    private OkHttpClient client = new OkHttpClient();

    // ⚠️ 重要：请替换成你在和风天气官网申请的免费 API Key
    private static final String WEATHER_API_KEY = "076c8d38ce504ae280907c8c45152dc1";
    private static final String BASE_URL = "https://devapi.qweather.com/v7/";

    // 定位相关
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        // 基础天气
        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_temp);
        tvWeather = findViewById(R.id.tv_weather);
        tvWind = findViewById(R.id.tv_wind);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvUv = findViewById(R.id.tv_uv);
        tvAdvice = findViewById(R.id.tv_advice);

        // 未来三天
        tvDay1 = findViewById(R.id.tv_day1);
        tvTemp1 = findViewById(R.id.tv_temp1);
        tvDay2 = findViewById(R.id.tv_day2);
        tvTemp2 = findViewById(R.id.tv_temp2);
        tvDay3 = findViewById(R.id.tv_day3);
        tvTemp3 = findViewById(R.id.tv_temp3);

        // 交互控件
        etCity = findViewById(R.id.et_city);
        btnSearch = findViewById(R.id.btn_search);
        btnGetLocation = findViewById(R.id.btn_get_location);
        btnToCloth = findViewById(R.id.btn_to_cloth);

        // 按钮事件
        btnSearch.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String city = etCity.getText().toString().trim();
            if (!city.isEmpty()) fetchWeather(city);
            else Toast.makeText(this, "请输入城市名", Toast.LENGTH_SHORT).show();
        });

        btnGetLocation.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            getLocationAndFetchWeather();
        });

        btnToCloth.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            startActivity(new Intent(WeatherActivity.this, ClothActivity.class));
        });

        // 默认加载北京天气
        fetchWeather("北京");
    }

    // ========== 网络请求 ==========
    private void fetchWeather(String location) {
        // 实时天气
        String nowUrl = BASE_URL + "weather/now?location=" + location + "&key=" + WEATHER_API_KEY;
        Request nowReq = new Request.Builder().url(nowUrl).build();
        client.newCall(nowReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(WeatherActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    runOnUiThread(() -> parseNowWeather(json));
                }
            }
        });

        // 未来3天预报
        String dailyUrl = BASE_URL + "weather/3d?location=" + location + "&key=" + WEATHER_API_KEY;
        Request dailyReq = new Request.Builder().url(dailyUrl).build();
        client.newCall(dailyReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(WeatherActivity.this, "获取预报失败", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    runOnUiThread(() -> parseDailyWeather(json));
                }
            }
        });
    }

    // 解析实时天气
    private void parseNowWeather(String json) {
        try {
            JSONObject root = new JSONObject(json);
            if (!"200".equals(root.getString("code"))) {
                Toast.makeText(this, "城市不存在或API错误", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject now = root.getJSONObject("now");
            String city = root.optString("fxLink", "");
            String temp = now.getString("temp") + "℃";
            String text = now.getString("text");
            String windDir = now.getString("windDir");
            String windScale = now.getString("windScale") + "级";
            String humidity = now.getString("humidity") + "%";

            tvCity.setText(city);
            tvTemp.setText(temp);
            tvWeather.setText(text);
            tvWind.setText(windDir + " " + windScale);
            tvHumidity.setText("💧 湿度 " + humidity);
            // 紫外线需要单独请求（简化处理，用温度模拟）
            tvUv.setText("☀️ 紫外线 " + (Integer.parseInt(now.getString("temp")) > 25 ? "较强" : "中等"));

            // 根据温度生成穿搭建议
            int tempInt = Integer.parseInt(now.getString("temp"));
            String advice = getDressingAdvice(tempInt);
            tvAdvice.setText("👕 穿搭建议：" + advice);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 解析未来三天预报
    private void parseDailyWeather(String json) {
        try {
            JSONObject root = new JSONObject(json);
            JSONArray daily = root.getJSONArray("daily");
            String[] days = {"明天", "后天", "大后天"};
            TextView[] dayTvs = {tvDay1, tvDay2, tvDay3};
            TextView[] tempTvs = {tvTemp1, tvTemp2, tvTemp3};

            for (int i = 0; i < 3 && i < daily.length(); i++) {
                JSONObject day = daily.getJSONObject(i);
                String date = day.getString("fxDate");
                String tempMin = day.getString("tempMin") + "℃";
                String tempMax = day.getString("tempMax") + "℃";
                dayTvs[i].setText(days[i] + "\n" + date);
                tempTvs[i].setText(tempMin + "~" + tempMax);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 穿衣推荐逻辑
    private String getDressingAdvice(int temp) {
        if (temp >= 28) return "短袖、短裤、防晒衣、遮阳帽";
        else if (temp >= 20) return "T恤、薄外套、牛仔裤";
        else if (temp >= 10) return "卫衣、夹克、长裤";
        else if (temp >= 0) return "毛衣、棉服、围巾";
        else return "羽绒服、厚毛衣、手套、帽子";
    }

    // ========== 定位功能 ==========
    private void getLocationAndFetchWeather() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            startLocation();
        }
    }

    private void startLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                fetchWeather(lat + "," + lon);
            }
            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override public void onProviderEnabled(@NonNull String provider) {}
            @Override public void onProviderDisabled(@NonNull String provider) {}
        }, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocation();
            } else {
                Toast.makeText(this, "需要位置权限才能定位", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
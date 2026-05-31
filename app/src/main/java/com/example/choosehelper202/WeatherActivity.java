package com.example.choosehelper202;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

    private static final String TAG = "WeatherDebug";

    private TextView tvCity, tvTemp, tvWeather, tvWind, tvHumidity, tvUv, tvAdvice;
    private TextView tvDay1, tvTemp1, tvDay2, tvTemp2, tvDay3, tvTemp3;
    private EditText etCity;
    private Button btnSearch, btnGetLocation, btnToCloth;
    private ImageView bg;

    // ⚠️ 请确认这个 Key 是高德「Web服务」类型的 Key
    private static final String AMAP_API_KEY = "dc34cab7ce8074521a1700c4d5a54ce9";
    private static final String WEATHER_BASE_URL = "https://restapi.amap.com/v3/weather/weatherInfo";

    private OkHttpClient client = new OkHttpClient();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.d(TAG, "onCreate 执行");

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_temp);
        tvWeather = findViewById(R.id.tv_weather);
        tvWind = findViewById(R.id.tv_wind);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvUv = findViewById(R.id.tv_uv);
        tvAdvice = findViewById(R.id.tv_advice);
        tvDay1 = findViewById(R.id.tv_day1);
        tvTemp1 = findViewById(R.id.tv_temp1);
        tvDay2 = findViewById(R.id.tv_day2);
        tvTemp2 = findViewById(R.id.tv_temp2);
        tvDay3 = findViewById(R.id.tv_day3);
        tvTemp3 = findViewById(R.id.tv_temp3);
        etCity = findViewById(R.id.et_city);
        btnSearch = findViewById(R.id.btn_search);
        btnGetLocation = findViewById(R.id.btn_get_location);
        btnToCloth = findViewById(R.id.btn_to_cloth);

        btnSearch.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            String city = etCity.getText().toString().trim();
            if (!city.isEmpty()) getCityCodeFromCityName(city);
            else Toast.makeText(this, "请输入城市名", Toast.LENGTH_SHORT).show();
        });

        btnGetLocation.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            getCurrentLocation();
        });

        btnToCloth.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            startActivity(new Intent(WeatherActivity.this, ClothActivity.class));
        });

        // 默认请求北京天气（城市代码 110000）
        fetchWeather("110000");
    }

    private void getCityCodeFromCityName(String cityName) {
        String url = "https://restapi.amap.com/v3/geocode/geo?key=" + AMAP_API_KEY + "&address=" + cityName;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "网络错误: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e(TAG, "地理编码失败", e);
            }
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "地理编码返回: " + json);
                try {
                    JSONObject obj = new JSONObject(json);
                    if ("1".equals(obj.getString("status"))) {
                        JSONArray geocodes = obj.getJSONArray("geocodes");
                        if (geocodes.length() > 0) {
                            String adcode = geocodes.getJSONObject(0).getString("adcode");
                            mainHandler.post(() -> fetchWeather(adcode));
                        } else mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "未找到该城市", Toast.LENGTH_SHORT).show());
                    } else {
                        mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "地理编码失败: " + obj.optString("info"), Toast.LENGTH_SHORT).show());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "解析地理编码失败", e);
                    mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "解析城市失败", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void fetchWeather(String cityCode) {
        String liveUrl = WEATHER_BASE_URL + "?key=" + AMAP_API_KEY + "&city=" + cityCode + "&extensions=base";
        String forecastUrl = WEATHER_BASE_URL + "?key=" + AMAP_API_KEY + "&city=" + cityCode + "&extensions=all";

        Log.d(TAG, "请求实时: " + liveUrl);
        client.newCall(new Request.Builder().url(liveUrl).build()).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "实时天气请求失败: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e(TAG, "实时请求失败", e);
            }
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "实时返回: " + json);
                mainHandler.post(() -> parseLiveWeather(json));
            }
        });

        client.newCall(new Request.Builder().url(forecastUrl).build()).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mainHandler.post(() -> Toast.makeText(WeatherActivity.this, "预报请求失败", Toast.LENGTH_SHORT).show());
                Log.e(TAG, "预报请求失败", e);
            }
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "预报返回: " + json);
                mainHandler.post(() -> parseForecastWeather(json));
            }
        });
    }

    private void parseLiveWeather(String json) {
        try {
            JSONObject root = new JSONObject(json);
            if (!"1".equals(root.getString("status"))) {
                Toast.makeText(this, "实时接口错误: " + root.optString("info"), Toast.LENGTH_SHORT).show();
                return;
            }
            JSONArray lives = root.getJSONArray("lives");
            if (lives.length() == 0) {
                Toast.makeText(this, "无天气数据", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject live = lives.getJSONObject(0);
            tvCity.setText(live.getString("city"));
            tvTemp.setText(live.getString("temperature") + "℃");
            tvWeather.setText(live.getString("weather"));
            tvWind.setText(live.getString("winddirection") + " " + live.getString("windpower") + "级");
            tvHumidity.setText("💧 湿度 " + live.getString("humidity") + "%");

            int temp = Integer.parseInt(live.getString("temperature"));
            String uv = temp > 25 ? "较强" : (temp > 15 ? "中等" : "弱");
            tvUv.setText("☀️ 紫外线 " + uv);
            tvAdvice.setText("👕 穿搭建议：" + getDressingAdvice(temp));

            Toast.makeText(this, "天气更新成功", Toast.LENGTH_SHORT).show(); // 调试用
        } catch (Exception e) {
            Log.e(TAG, "解析实时异常", e);
            Toast.makeText(this, "解析实时数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseForecastWeather(String json) {
        try {
            JSONObject root = new JSONObject(json);
            if (!"1".equals(root.getString("status"))) return;
            JSONArray forecasts = root.getJSONArray("forecasts");
            if (forecasts.length() == 0) return;
            JSONArray casts = forecasts.getJSONObject(0).getJSONArray("casts");
            String[] days = {"明天", "后天", "大后天"};
            TextView[] dayViews = {tvDay1, tvDay2, tvDay3};
            TextView[] tempViews = {tvTemp1, tvTemp2, tvTemp3};
            for (int i = 0; i < 3 && i < casts.length(); i++) {
                JSONObject cast = casts.getJSONObject(i);
                dayViews[i].setText(days[i] + "\n" + cast.getString("date"));
                tempViews[i].setText("白天:" + cast.getString("daytemp") + "℃ 夜晚:" + cast.getString("nighttemp") + "℃");
            }
        } catch (Exception e) {
            Log.e(TAG, "解析预报异常", e);
        }
    }

    private String getDressingAdvice(int temp) {
        if (temp >= 28) return "短袖、短裤、防晒衣";
        else if (temp >= 20) return "T恤、薄外套、牛仔裤";
        else if (temp >= 10) return "卫衣、夹克、长裤";
        else if (temp >= 0) return "毛衣、棉服、围巾";
        else return "羽绒服、厚毛衣、手套";
    }

    // 定位相关方法（保持原有实现，略）
    private void getCurrentLocation() { /* 留空或保留原代码 */ }
    private void startLocation() {}
    private void getCityCodeFromLatLon(double lat, double lon) {}
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {}
}
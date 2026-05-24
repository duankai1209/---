package com.example.choosehelper202;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {
    EditText etCity;
    TextView tvResult;
    Button btnQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        etCity=findViewById(R.id.et_city);
        tvResult=findViewById(R.id.tv_result);
        btnQuery=findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(v->{
            String city=etCity.getText().toString().trim();
            if(city.isEmpty()) city="北京";
            tvResult.setText(city+"\n温度：24℃\n天气：晴\n风力：2级\n未来三天：晴");
        });
    }
}
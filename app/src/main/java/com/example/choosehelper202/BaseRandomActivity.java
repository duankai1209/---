package com.example.choosehelper202;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public abstract class BaseRandomActivity extends AppCompatActivity {
    protected ArrayList<String> list=new ArrayList<>();
    protected ArrayAdapter<String> adapter;
    protected TextView tvTitle;
    protected ListView lv;
    protected EditText et;
    protected Button btnAdd,btnRandom,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_random);
        tvTitle=findViewById(R.id.tv_title);
        lv=findViewById(R.id.lv_list);
        et=findViewById(R.id.et_input);
        btnAdd=findViewById(R.id.btn_add);
        btnRandom=findViewById(R.id.btn_random);
        btnBack=findViewById(R.id.btn_back);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        initData();
        setEvent();
    }
    protected abstract void initData();
    private void setEvent(){
        btnAdd.setOnClickListener(v->{
            String s=et.getText().toString().trim();
            if(!s.isEmpty()){list.add(s);adapter.notifyDataSetChanged();et.setText("");}
        });
        btnRandom.setOnClickListener(v->{
            if(!list.isEmpty()) tvTitle.setText("结果："+list.get((int)(Math.random()*list.size())));
        });
        btnBack.setOnClickListener(v->finish());
        lv.setOnItemLongClickListener((a,v,p,id)->{
            new AlertDialog.Builder(this).setItems(new String[]{"删除"},(d,w)->{list.remove(p);adapter.notifyDataSetChanged();}).show();
            return true;
        });
    }
}
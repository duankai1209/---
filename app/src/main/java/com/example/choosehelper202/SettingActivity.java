package com.example.choosehelper202;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SettingActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> photoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    SharedPreferences.Editor editor = getSharedPreferences("bgConfig", MODE_PRIVATE).edit();
                    editor.putString("custom_bg_uri", uri.toString());
                    editor.putInt("bg_type", 2);
                    editor.apply();
                    Toast.makeText(this, "壁纸已设置", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGrant -> { if (isGrant) openAlbum(); });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button btnPick = findViewById(R.id.btn_pick_photo);
        Button btnReset = findViewById(R.id.btn_reset_bg);

        btnPick.setOnClickListener(v -> checkPermission());
        btnReset.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences("bgConfig", MODE_PRIVATE).edit();
            editor.putInt("bg_type", 1);
            editor.apply();
            finish();
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            } else openAlbum();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        photoLauncher.launch(intent);
    }
}
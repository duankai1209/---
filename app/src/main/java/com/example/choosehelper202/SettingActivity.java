package com.example.choosehelper202;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SettingActivity extends AppCompatActivity {
    private Button btnChangeBg, btnClearCache, btnResetBg;  // 新增恢复默认按钮
    private ImageView bg;

    // 图片选择启动器
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        BgUtil.saveBg(this, uri);
                        // 重新设置当前背景
                        BgUtil.setGlobalBg(this, bg);
                        Toast.makeText(this, "背景已更换", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bg = findViewById(R.id.bg);
        BgUtil.setGlobalBg(this, bg);

        btnChangeBg = findViewById(R.id.btn_change_bg);
        btnClearCache = findViewById(R.id.btn_clear_cache);
        btnResetBg = findViewById(R.id.btn_reset_bg);   // 绑定新按钮

        btnChangeBg.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        btnClearCache.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            // 清除Glide缓存
            new Thread(() -> {
                Glide.get(SettingActivity.this).clearDiskCache();
                runOnUiThread(() -> Toast.makeText(SettingActivity.this, "缓存已清理", Toast.LENGTH_SHORT).show());
            }).start();
        });

        // 恢复默认背景
        btnResetBg.setOnClickListener(v -> {
            AnimUtil.clickAnim(v);
            resetToDefaultBg();
        });
    }

    /**
     * 恢复默认背景：清除保存的图片URI，并刷新当前界面背景
     */
    private void resetToDefaultBg() {
        SharedPreferences sp = getSharedPreferences("bgConfig", MODE_PRIVATE);
        sp.edit().remove("bg_image_uri").apply();
        // 刷新当前Activity的背景
        BgUtil.setGlobalBg(this, bg);
        Toast.makeText(this, "已恢复默认背景", Toast.LENGTH_SHORT).show();
    }
}
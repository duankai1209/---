package com.example.choosehelper202;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BgUtil {
    private static final String BG_SP = "bgConfig";
    private static final String BG_URI = "bg_image_uri";

    public static void setGlobalBg(Activity activity, ImageView bgView) {
        SharedPreferences sp = activity.getSharedPreferences(BG_SP, 0);
        String uriStr = sp.getString(BG_URI, "");
        if (!uriStr.isEmpty()) {
            Uri uri = Uri.parse(uriStr);
            Glide.with(activity).load(uri).into(bgView);
        }
    }

    public static void saveBg(Activity activity, Uri uri) {
        SharedPreferences sp = activity.getSharedPreferences(BG_SP, 0);
        sp.edit().putString(BG_URI, uri.toString()).apply();
    }
}
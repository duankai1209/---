package com.example.choosehelper202;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class HistoryUtil {
    private static final Gson gson = new Gson();

    public static void saveHistory(Context context, String pageTag, String result) {
        SharedPreferences sp = context.getSharedPreferences(pageTag + "_history", Context.MODE_PRIVATE);
        String json = sp.getString("his", "[]");
        ArrayList<String> history = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        if (history == null) history = new ArrayList<>();
        history.add(0, result);
        if (history.size() > 20) history = new ArrayList<>(history.subList(0, 20));
        sp.edit().putString("his", gson.toJson(history)).apply();
    }

    public static List<String> getHistory(Context context, String pageTag) {
        SharedPreferences sp = context.getSharedPreferences(pageTag + "_history", Context.MODE_PRIVATE);
        String json = sp.getString("his", "[]");
        ArrayList<String> history = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        return history == null ? new ArrayList<>() : history;
    }

    public static void clearHistory(Context context, String pageTag) {
        SharedPreferences sp = context.getSharedPreferences(pageTag + "_history", Context.MODE_PRIVATE);
        sp.edit().remove("his").apply();
    }
}
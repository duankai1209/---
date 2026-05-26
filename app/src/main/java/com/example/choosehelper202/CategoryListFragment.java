package com.example.choosehelper202;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Random;

public class CategoryListFragment extends Fragment {
    private ListView lvList;
    private EditText etInput;
    private Button btnAdd, btnRandom, btnFav, btnFavRandom, btnClearFav;
    private ArrayList<String> dataList = new ArrayList<>();
    private ArrayList<String> favList = new ArrayList<>();
    private String pageTag;      // 例如 "indoor", "outdoor", "relax", "entertainment", "food"
    private Gson gson = new Gson();
    private SharedPreferences spFav;
    private ArrayAdapter<String> adapter;

    // 工厂方法
    public static CategoryListFragment newInstance(String categoryTag, ArrayList<String> defaultData) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putString("categoryTag", categoryTag);
        args.putStringArrayList("defaultData", defaultData);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        pageTag = args.getString("categoryTag");
        ArrayList<String> defaultData = args.getStringArrayList("defaultData");
        if (defaultData != null) dataList.addAll(defaultData);

        lvList = view.findViewById(R.id.lvList);
        etInput = view.findViewById(R.id.etInput);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnRandom = view.findViewById(R.id.btnRandom);
        btnFav = view.findViewById(R.id.btnFav);
        btnFavRandom = view.findViewById(R.id.btnFavRandom);
        btnClearFav = view.findViewById(R.id.btnClearFav);

        spFav = requireActivity().getSharedPreferences("fav_" + pageTag, android.content.Context.MODE_PRIVATE);
        loadFav();

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataList);
        lvList.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String s = etInput.getText().toString().trim();
            if (!s.isEmpty()) {
                dataList.add(s);
                adapter.notifyDataSetChanged();
                etInput.setText("");
                Toast.makeText(getContext(), "已添加: " + s, Toast.LENGTH_SHORT).show();
            }
        });

        btnRandom.setOnClickListener(v -> {
            if (dataList.isEmpty()) {
                Toast.makeText(getContext(), "列表为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String res = dataList.get(new Random().nextInt(dataList.size()));
            Toast.makeText(getContext(), "🎲 随机结果： " + res, Toast.LENGTH_LONG).show();
            saveHistory(res);
        });

        btnFav.setOnClickListener(v -> {
            if (dataList.isEmpty()) return;
            String res = dataList.get(new Random().nextInt(dataList.size()));
            if (!favList.contains(res)) {
                favList.add(res);
                saveFav();
                Toast.makeText(getContext(), "❤️ 收藏成功：" + res, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "已收藏", Toast.LENGTH_SHORT).show();
            }
        });

        btnFavRandom.setOnClickListener(v -> {
            if (favList.isEmpty()) {
                Toast.makeText(getContext(), "收藏为空", Toast.LENGTH_SHORT).show();
                return;
            }
            String res = favList.get(new Random().nextInt(favList.size()));
            Toast.makeText(getContext(), "⭐ 收藏随机：" + res, Toast.LENGTH_LONG).show();
        });

        btnClearFav.setOnClickListener(v -> {
            favList.clear();
            saveFav();
            Toast.makeText(getContext(), "已清空收藏", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadFav() {
        String json = spFav.getString("fav", "[]");
        favList = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        if (favList == null) favList = new ArrayList<>();
    }

    private void saveFav() {
        String json = gson.toJson(favList);
        spFav.edit().putString("fav", json).apply();
    }

    private void saveHistory(String result) {
        SharedPreferences sp = requireActivity().getSharedPreferences(pageTag + "_history", android.content.Context.MODE_PRIVATE);
        String json = sp.getString("his", "[]");
        ArrayList<String> history = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
        if (history == null) history = new ArrayList<>();
        history.add(0, result);
        if (history.size() > 20) history = new ArrayList<>(history.subList(0, 20));
        sp.edit().putString("his", gson.toJson(history)).apply();
    }

    // 供外部调用获取当前分类列表中的随机一项
    public String getRandomItem() {
        if (dataList.isEmpty()) return null;
        return dataList.get(new Random().nextInt(dataList.size()));
    }
}
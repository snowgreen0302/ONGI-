package com.example.ongi;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import android.widget.ImageButton;
import android.widget.TextView;

public class MyFragment extends Fragment {

    TextView txtRecentName;
    TextView txtRecentCategory;
    CardView cardRecent;
    ImageButton btnBack;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_my,
                        container,
                        false);

        cardRecent =
                view.findViewById(R.id.cardRecent);

        // 연결
        txtRecentName =
                view.findViewById(R.id.txtRecentName);

        txtRecentCategory =
                view.findViewById(R.id.txtRecentCategory);

        btnBack =
                view.findViewById(R.id.btnBack);


        // 뒤로가기 버튼
        btnBack.setOnClickListener(v -> {

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout,
                            new HomeFragment())
                    .commit();

        });

        // 최근 본 데이터 불러오기
        SharedPreferences prefs =
                requireActivity()
                        .getSharedPreferences(
                                "recent",
                                requireActivity().MODE_PRIVATE);

        String recentName =
                prefs.getString(
                        "name",
                        "최근 본 없음");

        String recentCategory =
                prefs.getString(
                        "category",
                        "");

        String recentAddress =
                prefs.getString(
                        "address",
                        "");

        String recentPhone =
                prefs.getString(
                        "phone",
                        "");

        String recentHomepage =
                prefs.getString(
                        "homepage",
                        "");

        // 화면에 출력
        txtRecentName.setText(recentName);
        txtRecentCategory.setText(recentCategory);

        cardRecent.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            getContext(),
                            DetailActivity.class);

            intent.putExtra(
                    "name",
                    recentName);

            intent.putExtra(
                    "category",
                    recentCategory);

            intent.putExtra(
                    "address",
                    recentAddress);

            intent.putExtra(
                    "phone",
                    recentPhone);

            intent.putExtra(
                    "homepage",
                    recentHomepage);

            intent.putExtra(
                    "image",
                    R.drawable.ic_launcher_foreground);

            startActivity(intent);

        });

        return view;
    }
}
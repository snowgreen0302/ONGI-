package com.example.ongi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ongi.adapter.DonationAdapter;
import com.example.ongi.model.Donation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AIFragment extends Fragment {

    EditText edtQuestion;

    Button btnAsk;

    Button btnNext;

    TextView txtAnswer;

    RecyclerView recyclerRecommend;

    DonationAdapter recommendAdapter;

    List<Donation> recommendList;

    List<Donation> matchedList;

    int currentIndex = 0;


    public AIFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(
                        R.layout.fragment_ai,
                        container,
                        false);

        List<Donation> list =

                DonationRepository
                        .donationList;


        edtQuestion =
                view.findViewById(
                        R.id.edtQuestion);

        btnAsk =
                view.findViewById(
                        R.id.btnAsk);

        txtAnswer =
                view.findViewById(
                        R.id.txtAnswer);

        recyclerRecommend =
                view.findViewById(
                        R.id.recyclerRecommend);

        recommendList =
                new ArrayList<>();

        matchedList =
                new ArrayList<>();

        recommendAdapter =

                new DonationAdapter(

                        recommendList,

                        false);

        recyclerRecommend.setLayoutManager(

                new GridLayoutManager(
                        getContext(),

                        2));

        recyclerRecommend.setAdapter(
                recommendAdapter);

        btnNext =
                view.findViewById(
                        R.id.btnNext);

        btnAsk.setOnClickListener(v -> {

            String question =

                    edtQuestion
                            .getText()
                            .toString();

            txtAnswer.setText(
                    "AI가 생각중입니다...");

            new Thread(() -> {

                AIResponse response =

                        askGemini(question);


                requireActivity()

                        .runOnUiThread(() -> {

                            txtAnswer.setText(

                                    response.message);


                            makeRecommendList(
                                    response);

                            currentIndex = 0;

                            showRecommend();

                        });

            }).start();

        });

        btnNext.setOnClickListener(v -> {

            currentIndex += 2;

            showRecommend();

        });

        return view;
    }


    private void makeRecommendList(
            AIResponse response) {

        matchedList.clear();

        for (Donation donation

                : DonationRepository.donationList) {

            boolean categoryMatch =

                    response.category.isEmpty()

                            ||

                            donation.getCategory()

                                    .contains(

                                            response.category);


            boolean regionMatch =

                    response.region.isEmpty()

                            ||

                            donation.getAddress()

                                    .contains(

                                            response.region);


            if (categoryMatch

                    &&

                    regionMatch) {

                matchedList.add(

                        donation);

            }

        }

    }

    private void showRecommend() {

        recommendList.clear();

        if (currentIndex >= matchedList.size()) {

            txtAnswer.append(

                    "\n\n더 추천할 단체가 없습니다.");

            recommendAdapter.notifyDataSetChanged();

            return;
        }

        for (int i = currentIndex;

             i < currentIndex + 2

                     && i < matchedList.size();

             i++) {


            recommendList.add(

                    matchedList.get(i));

        }

        recommendAdapter.notifyDataSetChanged();

    }

    private AIResponse askGemini(String question) {

        AIResponse response =

                new AIResponse();

        try {

            URL url =

                    new URL(

                            "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent");


            HttpURLConnection conn =

                    (HttpURLConnection)

                            url.openConnection();


            conn.setRequestMethod(
                    "POST");

            conn.setRequestProperty(

                    "Content-Type",

                    "application/json");

            conn.setRequestProperty(

                    "X-goog-api-key",

                    BuildConfig.GEMINI_API_KEY);

            conn.setDoOutput(true);


            String prompt =

                    "사용자의 질문을 분석해서 "

                            +

                            "반드시 아래 JSON 형식으로만 답해.\n"

                            +

                            "{"

                            +

                            "\"message\":\"\","

                            +

                            "\"region\":\"\","

                            +

                            "\"category\":\"\","

                            +

                            "\"keyword\":\"\""

                            +

                            "}\n\n"

                            +

                            "message는 사용자의 질문을 반복하지 말고 "

                            +

                            "친절한 상담 문장으로 작성해.\n"

                            +

                            "예시:\n"

                            +

                            "{"

                            +

                            "\"message\":\"안녕하세요! 😊\\n\\n서울특별시에서 활동하는 "

                            +

                            "보건복지 분야 단체를 찾아봤어요.\\n\\n"

                            +

                            "아래 단체들은 어떠신가요?\","

                            +

                            "\"region\":\"서울특별시\","

                            +

                            "\"category\":\"보건복지\","

                            +

                            "\"keyword\":\"\""

                            +

                            "}\n\n"

                            +

                            "질문 : "

                            +

                            question;


            JSONObject body =

                    new JSONObject();


            JSONArray contents =

                    new JSONArray();


            JSONObject content =

                    new JSONObject();

            JSONArray parts =

                    new JSONArray();

            JSONObject part =

                    new JSONObject();


            part.put(

                    "text",

                    prompt);

            parts.put(part);

            content.put(

                    "parts",

                    parts);

            contents.put(content);

            body.put(

                    "contents",

                    contents);


            OutputStream os =

                    conn.getOutputStream();

            os.write(

                    body.toString()

                            .getBytes());

            os.close();


            BufferedReader br =

                    new BufferedReader(

                            new InputStreamReader(

                                    conn.getInputStream()));


            StringBuilder sb =

                    new StringBuilder();

            String line;


            while ((line = br.readLine())

                    != null) {

                sb.append(line);

            }


            JSONObject root =

                    new JSONObject(

                            sb.toString());


            String text =

                    root

                            .getJSONArray(

                                    "candidates")

                            .getJSONObject(0)

                            .getJSONObject(

                                    "content")

                            .getJSONArray(

                                    "parts")

                            .getJSONObject(0)

                            .getString(

                                    "text");


            JSONObject result =

                    new JSONObject(

                            text);


            response.message =

                    result.optString(
                            "message");

            response.region =

                    result.optString(
                            "region");

            response.category =

                    result.optString(
                            "category");

            response.keyword =

                    result.optString(
                            "keyword");


        } catch (Exception e) {

            e.printStackTrace();

            response.message =

                    "죄송해요 😥\n"

                            +

                            "AI 응답을 가져오지 못했어요.";

            response.region = "";

            response.category = "";

            response.keyword = "";

        }

        return response;

    }
}
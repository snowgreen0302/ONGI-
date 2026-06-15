package com.example.ongi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ongi.model.Donation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadDonationData();   // ★ 앱 시작하자마자 API 호출


        // 첫 화면
        String page =
                getIntent()
                        .getStringExtra(
                                "page");

        Fragment firstFragment;

        if("browse".equals(page)){

            firstFragment =
                    new BrowseFragment();

            bottomNavigationView
                    .setSelectedItemId(
                            R.id.nav_browse);

        }

        else if("my".equals(page)){

            firstFragment =
                    new MyFragment();

            bottomNavigationView
                    .setSelectedItemId(
                            R.id.nav_my);

        }

        else{

            firstFragment =
                    new HomeFragment();

            bottomNavigationView
                    .setSelectedItemId(
                            R.id.nav_home);

        }

        getSupportFragmentManager()

                .beginTransaction()

                .replace(
                        R.id.frameLayout,

                        firstFragment)

                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            if(item.getItemId() == R.id.nav_home){

                selectedFragment = new HomeFragment();

            }
            else if(item.getItemId() == R.id.nav_browse){

                selectedFragment = new BrowseFragment();
            }
            else if(item.getItemId() == R.id.nav_ai){

                selectedFragment =
                        new AIFragment();

            }
            else if(item.getItemId() == R.id.nav_my){

                selectedFragment = new MyFragment();

            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, selectedFragment)
                    .commit();





            return true;
        });
    }
    private void loadDonationData(){

        new Thread(() -> {

            try{

                List<Donation> list =
                        new ArrayList<>();

                String apiUrl =

                        "https://apis.data.go.kr/1741000/contributionGroupService/getCntrCategoryGrpList"

                                + "?serviceKey="

                                + BuildConfig.PUBLIC_API_KEY

                                + "&pageNo=1"

                                + "&numOfRows=100";

                URL url =
                        new URL(apiUrl);

                HttpURLConnection conn =

                        (HttpURLConnection)

                                url.openConnection();

                conn.setRequestMethod("GET");

                InputStream is =
                        conn.getInputStream();

                XmlPullParserFactory factory =
                        XmlPullParserFactory.newInstance();

                XmlPullParser parser =
                        factory.newPullParser();

                parser.setInput(
                        is,
                        "UTF-8");

                String name="";

                String category="";

                String address="";

                String phone="";

                String homepage="";

                int eventType =
                        parser.getEventType();

                while(eventType
                        != XmlPullParser.END_DOCUMENT){

                    String tagName =
                            parser.getName();

                    if(eventType
                            == XmlPullParser.START_TAG){

                        if("nanmmbyNm".equals(tagName)){

                            parser.next();

                            name = parser.getText();

                        }

                        else if("rcritRealm".equals(tagName)){

                            parser.next();

                            category = parser.getText();

                        }

                        else if("adres".equals(tagName)){

                            parser.next();

                            address = parser.getText();

                        }

                        else if("dmstcTelno".equals(tagName)){

                            parser.next();

                            phone = parser.getText();

                        }

                        else if("hmpgAdres".equals(tagName)){

                            parser.next();

                            homepage = parser.getText();

                        }

                    }

                    else if(eventType
                            == XmlPullParser.END_TAG){

                        if("item"
                                .equals(tagName)){

                            list.add(

                                    new Donation(

                                            name,

                                            category,

                                            phone,

                                            address,

                                            homepage,

                                            R.drawable.ic_launcher_foreground

                                    ));

                        }

                    }

                    eventType =
                            parser.next();

                }

                DonationRepository
                        .donationList

                        = list;


            }

            catch(Exception e){

                e.printStackTrace();

            }


        }).start();


    }
}
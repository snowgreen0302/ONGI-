package com.example.ongi;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.content.ClipData;
import android.content.ClipboardManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageButton btnBack;
    TextView detailName;
    TextView detailCategory;
    TextView detailPhone;
    TextView detailAddress;
    Button btnHomepage;
    BottomNavigationView bottomNavigationView;

    Button btnCall;

    Button btnCopyAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.detailName);
        detailCategory = findViewById(R.id.detailCategory);
        detailPhone = findViewById(R.id.detailPhone);
        detailAddress = findViewById(R.id.detailAddress);
        btnHomepage = findViewById(R.id.btnHomepage);

        btnCall = findViewById(R.id.btnCall);

        btnCopyAddress = findViewById(R.id.btnCopyAddress);

        btnBack = findViewById(R.id.btnBack);

        String name = getIntent().getStringExtra("name");
        String category = getIntent().getStringExtra("category");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");
        String homepage = getIntent().getStringExtra("homepage");

        btnHomepage.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(homepage));

            startActivity(intent);
        });
        btnCall.setOnClickListener(v -> {

            Intent intent =
                    new Intent(

                            Intent.ACTION_DIAL,

                            Uri.parse(
                                    "tel:" + phone));

            startActivity(intent);

        });

        btnCopyAddress.setOnClickListener(v -> {

            ClipboardManager clipboard =

                    (ClipboardManager)

                            getSystemService(
                                    CLIPBOARD_SERVICE);

            ClipData clip =

                    ClipData.newPlainText(
                            "address",
                            address);

            clipboard.setPrimaryClip(clip);

            Toast.makeText(

                            this,

                            "주소가 복사되었습니다.",

                            Toast.LENGTH_SHORT)

                    .show();

        });
        bottomNavigationView =
                findViewById(
                        R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Intent intent =
                    new Intent(
                            DetailActivity.this,
                            MainActivity.class);

            if(item.getItemId() == R.id.nav_home){

                startActivity(intent);

            }

            else if(item.getItemId() == R.id.nav_browse){

                intent.putExtra(
                        "page",
                        "browse");

                startActivity(intent);

            }

            else if(item.getItemId() == R.id.nav_my){

                intent.putExtra(
                        "page",
                        "my");

                startActivity(intent);

            }

            finish();

            return true;

        });


        int image = getIntent().getIntExtra("image", 0);

        detailName.setText(name);
        detailCategory.setText("분야: " + category);
        detailPhone.setText("전화번호: " + phone);
        detailAddress.setText("주소: " + address);

        btnBack.setOnClickListener(v -> finish());
    }

}
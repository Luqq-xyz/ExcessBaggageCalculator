package com.example.excessbaggagecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.graphics.Color;
import android.view.Window;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#F3F6FB"));

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());

        controller.setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_about);

        Button websiteLink = findViewById(R.id.websiteLink);
        Button backButton = findViewById(R.id.backButton);

        String projectUrl = "https://github.com/Luqq-xyz/ExcessBaggageCalculator";

        websiteLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(projectUrl));
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());
    }
}

package com.example.excessbaggagecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView websiteLink = findViewById(R.id.websiteLink);
        Button backButton = findViewById(R.id.backButton);

        String projectUrl = "https://github.com/Luqq-xyz/ExcessBaggageCalculator";

        websiteLink.setOnClickListener(v -> {
            android.content.Intent intent =
                    new android.content.Intent(
                            android.content.Intent.ACTION_VIEW,
                            android.net.Uri.parse(projectUrl)
                    );
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());
    }
}

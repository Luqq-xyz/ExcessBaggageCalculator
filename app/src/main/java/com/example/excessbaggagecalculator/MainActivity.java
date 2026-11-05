package com.example.excessbaggagecalculator;

import android.content.Intent;
import android.graphics.Color;
import android.view.Window;
import android.os.Bundle;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TabStopSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput, rateInput;
    private Spinner classSpinner;
    private TextView resultText;
    private Button calculateButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use a light status bar with dark icons to match the screen background.
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#F3F6FB"));

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);

        setContentView(R.layout.activity_main);

        weightInput = findViewById(R.id.weightInput);
        rateInput = findViewById(R.id.rateInput);
        classSpinner = findViewById(R.id.classSpinner);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        Button shareButton = findViewById(R.id.shareButton);

        String[] ticketClasses = {"Economy", "Business"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ticketClasses
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        classSpinner.setAdapter(adapter);

        calculateButton.setOnClickListener(v -> calculateFee());
        resetButton.setOnClickListener(v -> resetForm());
        shareButton.setOnClickListener(v -> shareApplication());

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void calculateFee() {
        String weightText = weightInput.getText().toString().trim();
        String rateText = rateInput.getText().toString().trim();

        if (weightText.isEmpty()) {
            weightInput.setError("Please enter baggage weight.");
            weightInput.requestFocus();
            return;
        }

        if (rateText.isEmpty()) {
            rateInput.setError("Please enter the rate per kg.");
            rateInput.requestFocus();
            return;
        }

        double weight;
        double rate;

        try {
            weight = Double.parseDouble(weightText);
            rate = Double.parseDouble(rateText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Double.isFinite(weight) || weight < 0) {
            weightInput.setError("Weight cannot be negative.");
            weightInput.requestFocus();
            return;
        }

        if (!Double.isFinite(rate) || rate < 0) {
            rateInput.setError("Rate cannot be negative.");
            rateInput.requestFocus();
            return;
        }

        String ticketClass = classSpinner.getSelectedItem().toString();

        // Existing baggage allowance rules are unchanged.
        double allowance = ticketClass.equals("Economy") ? 20.0 : 40.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = excessCharge * 0.08;
        double totalPayable = excessCharge + serviceTax;

        String summary = String.format(
                Locale.US,
                "Baggage weight\t%.2f kg\n" +
                        "Ticket class\t%s\n" +
                        "Free allowance\t%.2f kg\n" +
                        "Excess weight\t%.2f kg\n" +
                        "Rate per kg\tRM %.2f\n\n" +
                        "Excess baggage fee\tRM %.2f\n" +
                        "Service tax (8%%)\tRM %.2f\n\n" +
                        "TOTAL PAYABLE\tRM %.2f",
                weight,
                ticketClass,
                allowance,
                excessWeight,
                rate,
                excessCharge,
                serviceTax,
                totalPayable
        );

        SpannableString styledSummary = new SpannableString(summary);

        // Place every value at the same horizontal position.
        int tabPosition = (int) (145 * getResources().getDisplayMetrics().density);
        int paragraphStart = 0;
        while (paragraphStart < summary.length()) {
            int newline = summary.indexOf('\n', paragraphStart);
            int paragraphEnd = newline >= 0 ? newline + 1 : summary.length();

            styledSummary.setSpan(
                    new TabStopSpan.Standard(tabPosition),
                    paragraphStart,
                    paragraphEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            if (newline < 0) {
                break;
            }
            paragraphStart = newline + 1;
        }

        // Emphasize the final payable row.
        int totalStart = summary.indexOf("TOTAL PAYABLE");
        if (totalStart >= 0) {
            styledSummary.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    totalStart,
                    summary.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            styledSummary.setSpan(
                    new RelativeSizeSpan(1.15f),
                    totalStart,
                    summary.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            styledSummary.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#183B68")),
                    totalStart,
                    summary.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        resultText.setText(styledSummary);
    }


    private void shareApplication() {
        String shareText = "Check out the Excess Baggage Calculator app! "
                + "View the project on GitHub: "
                + "https://github.com/Luqq-xyz/ExcessBaggageCalculator";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excess Baggage Calculator");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Share application via"));
    }

    private void resetForm() {
        weightInput.setText("");
        rateInput.setText("");
        classSpinner.setSelection(0);
        resultText.setText("Your calculation results will appear here.");
        weightInput.setError(null);
        rateInput.setError(null);
        weightInput.requestFocus();
    }
}

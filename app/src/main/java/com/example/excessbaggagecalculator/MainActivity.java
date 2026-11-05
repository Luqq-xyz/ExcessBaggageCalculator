package com.example.excessbaggagecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput, rateInput;
    private Spinner classSpinner;
    private TextView resultText;
    private Button calculateButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightInput = findViewById(R.id.weightInput);
        rateInput = findViewById(R.id.rateInput);
        classSpinner = findViewById(R.id.classSpinner);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);

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

        Button aboutButton = findViewById(R.id.aboutButton);

        aboutButton.setOnClickListener(v -> {
            android.content.Intent intent =
                    new android.content.Intent(
                            MainActivity.this,
                            AboutActivity.class
                    );
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
            Toast.makeText(this,
                    "Please enter valid numbers.",
                    Toast.LENGTH_SHORT).show();
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

        double allowance = ticketClass.equals("Economy") ? 20.0 : 40.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = excessCharge * 0.08;
        double totalPayable = excessCharge + serviceTax;

        resultText.setText(String.format(
                Locale.US,
                "CALCULATION RESULTS\n\n" +
                        "Baggage weight: %.2f kg\n" +
                        "Ticket class: %s\n" +
                        "Free baggage allowance: %.2f kg\n" +
                        "Excess baggage weight: %.2f kg\n" +
                        "Rate per kg: RM %.2f\n" +
                        "Excess baggage charge: RM %.2f\n" +
                        "Service tax (8%%): RM %.2f\n\n" +
                        "TOTAL PAYABLE: RM %.2f",
                weight,
                ticketClass,
                allowance,
                excessWeight,
                rate,
                excessCharge,
                serviceTax,
                totalPayable
        ));
    }

    private void resetForm() {
        weightInput.setText("");
        rateInput.setText("");
        classSpinner.setSelection(0);
        resultText.setText(
                "Your calculation results will appear here."
        );
        weightInput.setError(null);
        rateInput.setError(null);
        weightInput.requestFocus();
    }
}

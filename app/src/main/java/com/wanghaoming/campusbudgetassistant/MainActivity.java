package com.wanghaoming.campusbudgetassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_RECORD = 100;

    private EditText etBudget;
    private TextView tvBudgetSummary;
    private ListView listRecords;
    private double budget = 0;
    private double expenseTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBudget = findViewById(R.id.etBudget);
        tvBudgetSummary = findViewById(R.id.tvBudgetSummary);
        listRecords = findViewById(R.id.listRecords);
        TextView tvEmptyRecord = findViewById(R.id.tvEmptyRecord);
        Button btnSetBudget = findViewById(R.id.btnSetBudget);
        Button btnAddRecord = findViewById(R.id.btnAddRecord);

        listRecords.setEmptyView(tvEmptyRecord);
        btnSetBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMonthlyBudget();
            }
        });

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                intent.putExtra("currentBudget", budget);
                startActivityForResult(intent, REQUEST_ADD_RECORD);
            }
        });
    }

    private void setMonthlyBudget() {
        String input = etBudget.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, R.string.input_budget_first, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            budget = Double.parseDouble(input);
            if (budget <= 0) {
                Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
                return;
            }
            updateBudgetSummary();
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBudgetSummary() {
        double remaining = budget - expenseTotal;
        tvBudgetSummary.setText(getString(
                R.string.budget_format, budget, expenseTotal, remaining));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_RECORD && resultCode == RESULT_OK && data != null) {
            double amount = data.getDoubleExtra("amount", 0);
            String note = data.getStringExtra("note");
            Toast.makeText(this,
                    getString(R.string.record_received, note, amount),
                    Toast.LENGTH_SHORT).show();
        }
    }
}


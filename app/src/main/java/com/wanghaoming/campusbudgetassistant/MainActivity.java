package com.wanghaoming.campusbudgetassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_RECORD = 100;

    private EditText etBudget;
    private TextView tvBudgetSummary;
    private ListView listRecords;
    private ArrayList<HashMap<String, Object>> recordData;
    private SimpleAdapter recordAdapter;
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

        recordData = new ArrayList<>();
        String[] from = {"title", "detail", "amount"};
        int[] to = {R.id.tvRecordTitle, R.id.tvRecordDetail, R.id.tvRecordAmount};
        recordAdapter = new SimpleAdapter(
                this, recordData, R.layout.item_record, from, to);
        listRecords.setAdapter(recordAdapter);
        listRecords.setEmptyView(tvEmptyRecord);

        listRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, Object> record = recordData.get(position);
                Toast.makeText(MainActivity.this,
                        getString(R.string.record_click_format,
                                record.get("title"),
                                record.get("detail"),
                                record.get("amount")),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
            String category = data.getStringExtra("category");
            String type = data.getStringExtra("type");
            boolean necessary = data.getBooleanExtra("necessary", false);
            boolean includeBudget = data.getBooleanExtra("includeBudget", true);

            boolean isExpense = getString(R.string.expense).equals(type);
            String optionText;
            if (isExpense) {
                optionText = necessary
                        ? getString(R.string.necessary_tag)
                        : getString(R.string.normal_tag);
            } else {
                optionText = includeBudget
                        ? getString(R.string.in_budget_tag)
                        : getString(R.string.out_budget_tag);
            }

            HashMap<String, Object> record = new HashMap<>();
            String title = isExpense ? type + " · " + category : type;
            record.put("title", title);
            record.put("detail", note + " · " + optionText);
            String sign = isExpense ? "-" : "+";
            record.put("amount", sign + "¥" + String.format("%.2f", amount));
            recordData.add(record);
            recordAdapter.notifyDataSetChanged();

            if (isExpense) {
                expenseTotal += amount;
            } else if (includeBudget) {
                budget += amount;
                etBudget.setText(String.format("%.2f", budget));
            }

            if (budget > 0) {
                updateBudgetSummary();
            }
        }
    }
}


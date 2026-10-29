package com.wanghaoming.campusbudgetassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecordActivity extends AppCompatActivity {

    private EditText etAmount;
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        TextView tvCurrentBudget = findViewById(R.id.tvCurrentBudget);
        Button btnSaveRecord = findViewById(R.id.btnSaveRecord);
        Button btnCancel = findViewById(R.id.btnCancel);

        double currentBudget = getIntent().getDoubleExtra("currentBudget", 0);
        if (currentBudget > 0) {
            tvCurrentBudget.setText(getString(
                    R.string.current_budget_format, currentBudget));
        }

        btnSaveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveRecord() {
        String amountText = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (amountText.isEmpty() || note.isEmpty()) {
            Toast.makeText(this, R.string.complete_record, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent data = new Intent();
            data.putExtra("amount", amount);
            data.putExtra("note", note);
            setResult(RESULT_OK, data);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
        }
    }
}


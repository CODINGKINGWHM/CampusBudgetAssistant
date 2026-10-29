package com.wanghaoming.campusbudgetassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecordActivity extends AppCompatActivity {

    private EditText etAmount;
    private EditText etNote;
    private CheckBox cbNecessary;
    private CheckBox cbIncludeBudget;
    private TextView tvSelectionPreview;
    private String selectedCategory;
    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        cbNecessary = findViewById(R.id.cbNecessary);
        cbIncludeBudget = findViewById(R.id.cbIncludeBudget);
        tvSelectionPreview = findViewById(R.id.tvSelectionPreview);
        TextView tvCurrentBudget = findViewById(R.id.tvCurrentBudget);
        RadioGroup rgType = findViewById(R.id.rgType);
        Spinner spCategory = findViewById(R.id.spCategory);
        Button btnSaveRecord = findViewById(R.id.btnSaveRecord);
        Button btnCancel = findViewById(R.id.btnCancel);

        selectedType = getString(R.string.expense);
        String[] categories = getResources().getStringArray(R.array.record_categories);
        selectedCategory = categories[0];

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                updateSelectionPreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbIncome) {
                    selectedType = getString(R.string.income);
                } else {
                    selectedType = getString(R.string.expense);
                }
                updateSelectionPreview();
            }
        });

        CompoundButton.OnCheckedChangeListener checkedChangeListener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        updateSelectionPreview();
                    }
                };
        cbNecessary.setOnCheckedChangeListener(checkedChangeListener);
        cbIncludeBudget.setOnCheckedChangeListener(checkedChangeListener);
        updateSelectionPreview();

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
            data.putExtra("category", selectedCategory);
            data.putExtra("type", selectedType);
            data.putExtra("necessary", cbNecessary.isChecked());
            data.putExtra("includeBudget", cbIncludeBudget.isChecked());
            setResult(RESULT_OK, data);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSelectionPreview() {
        String type = selectedType == null ? getString(R.string.expense) : selectedType;
        String category = selectedCategory == null ? "" : selectedCategory;
        tvSelectionPreview.setText(getString(
                R.string.selection_preview, type, category));
    }
}


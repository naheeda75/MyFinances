package com.example.myfinances;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Toast;

public class MainActivity extends Activity {

    private RadioGroup radioGroup;
    private EditText accountNumberEditText;
    private EditText initialBalanceEditText;
    private EditText currentBalanceEditText;
    private EditText paymentAmountEditText;
    private EditText interestRateEditText;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        accountNumberEditText = findViewById(R.id.editTextAccountNumber);
        initialBalanceEditText = findViewById(R.id.editTextInitialBalance);
        currentBalanceEditText = findViewById(R.id.editTextCurrentBalance);
        paymentAmountEditText = findViewById(R.id.editTextPaymentAmount);
        interestRateEditText = findViewById(R.id.editTextInterestRate);

        db = openOrCreateDatabase("AccountData", MODE_PRIVATE, null);
        createTables();

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        // Set up the radio button change listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateFieldAccessibility(checkedId);
            }
        });
    }

    private void createTables() {
        db.execSQL("CREATE TABLE IF NOT EXISTS CD (AccountNumber TEXT, InitialBalance REAL, CurrentBalance REAL, InterestRate REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Loan (AccountNumber TEXT, InitialBalance REAL, CurrentBalance REAL, PaymentAmount REAL, InterestRate REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CheckingAccount (AccountNumber TEXT, CurrentBalance REAL)");
    }

    private void saveDataToDatabase() {
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        ContentValues values = new ContentValues();
        values.put("AccountNumber", accountNumberEditText.getText().toString());

        if (checkedRadioButtonId == R.id.radioCD) {
            values.put("InitialBalance", Double.parseDouble(initialBalanceEditText.getText().toString()));
            values.put("CurrentBalance", Double.parseDouble(currentBalanceEditText.getText().toString()));
            values.put("InterestRate", Double.parseDouble(interestRateEditText.getText().toString()));
            db.insert("CD", null, values);
        } else if (checkedRadioButtonId == R.id.radioLoan) {
            values.put("InitialBalance", Double.parseDouble(initialBalanceEditText.getText().toString()));
            values.put("CurrentBalance", Double.parseDouble(currentBalanceEditText.getText().toString()));
            values.put("PaymentAmount", Double.parseDouble(paymentAmountEditText.getText().toString()));
            values.put("InterestRate", Double.parseDouble(interestRateEditText.getText().toString()));
            db.insert("Loan", null, values);
        } else if (checkedRadioButtonId == R.id.radioChecking) {
            values.put("CurrentBalance", Double.parseDouble(currentBalanceEditText.getText().toString()));
            db.insert("CheckingAccount", null, values);
        }

        showToast("Data saved successfully!");
        clearFields();
    }

    private void clearFields() {
        accountNumberEditText.setText("");
        initialBalanceEditText.setText("");
        currentBalanceEditText.setText("");
        paymentAmountEditText.setText("");
        interestRateEditText.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateFieldAccessibility(int checkedId) {
        boolean isCDSelected = (checkedId == R.id.radioCD);
        boolean isLoanSelected = (checkedId == R.id.radioLoan);
        boolean isCheckingSelected = (checkedId == R.id.radioChecking);

        initialBalanceEditText.setEnabled(isCDSelected || isLoanSelected);
        paymentAmountEditText.setEnabled(isLoanSelected);
        interestRateEditText.setEnabled(isCDSelected || isLoanSelected);
        currentBalanceEditText.setEnabled(isCDSelected || isLoanSelected || isCheckingSelected);
    }
}

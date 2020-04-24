package com.boom.quickbooks.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boom.quickbooks.Data.DumCategoryDataServer;
import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Ui.CategoryAdapter;
import com.boom.quickbooks.Util.ExpenseValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddExpenseActivity";
    private List<CategoryItem> mCategoryList, nCategoryList;
    private CategoryAdapter mCAdapter;
    private Spinner mCSpinner;
    private EditText dateEditText, subjectEditText, amountEditText, desEditText;
    private Button submitButton;
    private String tempDStr;
    private DatePickerDialog dpDialog;
    private TextView dumTextView;
    private Expense mExpense;
    private String tempCName;
    private ExpenseDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        db = new ExpenseDbHandler(this);
        mExpense = new Expense();
        dumTextView = (TextView) findViewById(R.id.dumTextViewId);
        subjectEditText = (EditText) findViewById(R.id.subEditTextId);
        amountEditText = (EditText) findViewById(R.id.amountEditTextId);
        desEditText = (EditText) findViewById(R.id.desEditTextId);
        submitButton = (Button) findViewById(R.id.saveButtonId);
        submitButton.setOnClickListener(this);
        dateEditText = (EditText) findViewById(R.id.dateEditTextId);
        dateEditText.setOnClickListener(this);

        mCSpinner = (Spinner) findViewById(R.id.categorySpinnerId);
        mCategoryList = new ArrayList<CategoryItem>();
        mCategoryList = DumCategoryDataServer.getCategoryList();
//        for (CategoryItem c : mCategoryList) {
//            Log.d(TAG, c.getmCategoryName());
//        }
        mCAdapter = new CategoryAdapter(this, mCategoryList);
        mCSpinner.setAdapter(mCAdapter);
        mCSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem currentCategoryItem = (CategoryItem) parent.getItemAtPosition(position);
                Log.d(TAG, "selected category item : " + currentCategoryItem.toString());
                tempCName = currentCategoryItem.getmCategoryName();
                Toast.makeText(AddExpenseActivity.this, tempCName + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateEditTextId: {
                Calendar clder = Calendar.getInstance();
                int day_c = clder.get(Calendar.DAY_OF_MONTH);
                int month_c = clder.get(Calendar.MONTH);
                int year_c = clder.get(Calendar.YEAR);
                Log.d(TAG, "clicked");
                dpDialog = new DatePickerDialog(AddExpenseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tempDStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                                dateEditText.setText(tempDStr);
                                Log.d(TAG, tempDStr);
                            }
                        }, year_c, month_c, day_c);
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpDialog.show();
            }
            break;
            case R.id.saveButtonId: {
                //setup expense
                setUpmExpense();
                //validate expense
                String status = new ExpenseValidator(mExpense).validateExpenseWithDate();
                Log.d(TAG, "validation status" + status);
                if (status.equals("OK")) {
                    saveExpenseToDb();
                    //goto next activity
                    gotoNextActivity();
                } else if (!status.equals("OK")) {
                    Toast.makeText(this, status, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void gotoNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //start a new activity
                startActivity(new Intent(AddExpenseActivity.this, ExpenseListActivity.class));
                finish();
            }
        }, 500);
    }

    private void saveExpenseToDb() {
        //save expense to db
        Log.d(TAG, "Before Adding: " + String.valueOf(db.getCount()));
        db.addExpense(mExpense);
        Toast.makeText(this, "Saved !!", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Total item: " + String.valueOf(db.getCount()));

    }

    private void setUpmExpense() {
        mExpense.setSubName(subjectEditText.getText().toString());
        mExpense.setCategoryName(tempCName);
        mExpense.setAddDate(dateEditText.getText().toString());
        mExpense.setAmount(amountEditText.getText().toString());
        mExpense.setDescription(desEditText.getText().toString());
        Log.d(TAG, "Expense to save " + mExpense.toString());
    }
}
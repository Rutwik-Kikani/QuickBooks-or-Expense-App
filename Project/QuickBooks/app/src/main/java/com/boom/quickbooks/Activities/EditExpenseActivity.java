package com.boom.quickbooks.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.boom.quickbooks.Util.CategoryItemManager;
import com.boom.quickbooks.Util.ExpenseValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditExpenseActivity";
    private List<CategoryItem> CategoryList;
    private CategoryAdapter adapter;
    private Spinner categorySpinner;
    private EditText subject, date, amount, description;
    private Button submitButton;
    private DatePickerDialog dpDialog;
    private TextView dumTextView;
    private Expense expense, iexpense;
    private String tempCName, tempDStr;
    private int cIndex = 0;
    private ExpenseDbHandler db;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        db = new ExpenseDbHandler(this);

        //reference ui component
        getReferencesOfUpUiComp();

        //get intent from list activity
        Intent i = getIntent();
        extras = i.getExtras();
        if (extras.getString("uToken").equals("TO2")) {
            iexpense = (Expense) i.getSerializableExtra("uExpenseObj");
        }
        if (extras.getString("uToken").equals("TO1")) {
            iexpense = (Expense) i.getSerializableExtra("uExpenseObj");
        }
        if (iexpense != null) {
            setUpUi();
        } else if (iexpense == null) {
            //goto HomeActivity
            startActivity(new Intent(EditExpenseActivity.this, HomeActivity.class));
        }


    }

    private void setUpUi() {
        Log.d(TAG, "expense from intent " + iexpense.toString());
        amount.setText(iexpense.getAmount());
        subject.setText(iexpense.getSubName());
        description.setText(iexpense.getDescription());

        //setup for date
        tempDStr = iexpense.getAddDate();
        date.setText(iexpense.getAddDate());
        date.setOnClickListener(this);

        //setup for category selection
        String categoryName = iexpense.getCategoryName();
        cIndex = new CategoryItemManager().findCategoryIndex(categoryName);
        Log.d(TAG, "category's index in iexpense" + String.valueOf(cIndex));
        categorySpinner.setSelection(cIndex);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem currentCategoryItem = (CategoryItem) parent.getItemAtPosition(position);
                Log.d(TAG, "selected category item : " + currentCategoryItem.toString());
                tempCName = currentCategoryItem.getmCategoryName();
                Toast.makeText(EditExpenseActivity.this, tempCName + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitButton.setOnClickListener(this);
    }

    public void getReferencesOfUpUiComp() {

        dumTextView = (TextView) findViewById(R.id.edumTextViewId);
        subject = (EditText) findViewById(R.id.esubEditTextId);
        amount = (EditText) findViewById(R.id.eamountEditTextId);
        description = (EditText) findViewById(R.id.edesEditTextId);
        date = (EditText) findViewById(R.id.edateEditTextId);
        submitButton = findViewById(R.id.esaveButtonId);
        categorySpinner = (Spinner) findViewById(R.id.ecategorySpinnerId);
        CategoryList = new ArrayList<CategoryItem>();
        CategoryList = DumCategoryDataServer.getCategoryList();
        //          for(CategoryItem c : mCategoryList){
        //              Log.d(TAG,c.getmCategoryName());
        //          }
        adapter = new CategoryAdapter(this, CategoryList);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edateEditTextId: {

                Calendar clder = Calendar.getInstance();
                int day_c = clder.get(Calendar.DAY_OF_MONTH);
                int month_c = clder.get(Calendar.MONTH);
                int year_c = clder.get(Calendar.YEAR);
                dpDialog = new DatePickerDialog(EditExpenseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tempDStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                                date.setText(tempDStr);
                            }
                        }, year_c, month_c, day_c);
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpDialog.show();
            }
            break;
            case R.id.esaveButtonId: {
                //update expense in db
                //next Screen or listAvctivity
                expenseUpdate();
                //Toast.makeText(this,"expenseUpdate()",Toast.LENGTH_LONG).show();
            }
            break;
            default: {
                Toast.makeText(this, "Do Nothing", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setUpexpenseforUpdate() {
        expense = new Expense();
        expense.setId(iexpense.getId());
        expense.setSubName(subject.getText().toString());
        expense.setAddDate(date.getText().toString());
        expense.setCategoryName(tempCName);
        expense.setAmount(amount.getText().toString());
        expense.setDescription(description.getText().toString());
        Log.d(TAG, "Here the final onject " + expense.toString());

    }

    private void expenseUpdate() {
        //set up expense object for upgrading
        setUpexpenseforUpdate();

        //validate expense object
        String status = new ExpenseValidator(expense).validateExpenseWithDate();

        if (status.equals("OK")) {
            //update expense using db
            int i = db.updateExpense(expense);
            Log.d(TAG, "return id " + String.valueOf(i));
            Toast.makeText(EditExpenseActivity.this, "Updated !!", Toast.LENGTH_LONG).show();

            //goto listActivity
            startActivity(new Intent(EditExpenseActivity.this, ExpenseListActivity.class));
            finish();
        }
        else if (!status.equals("OK")){
            Toast.makeText(EditExpenseActivity.this, status, Toast.LENGTH_LONG).show();
        }

    }


}

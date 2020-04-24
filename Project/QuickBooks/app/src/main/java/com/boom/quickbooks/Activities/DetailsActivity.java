package com.boom.quickbooks.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Util.DateConvert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailsActivity";
    private TextView dCategoryName, dSubjectName, dAmount, dDate, dDescription;
    private Button dEditButton, dDeleteButton;
    private Expense expense;
    private ExpenseDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //set up db handler
        db = new ExpenseDbHandler(this);

        //set up all component
        setUpUi();

        Intent i = getIntent();
        expense = new Expense();
        expense = (Expense) i.getSerializableExtra("aExpenseObj");
        Log.d(TAG, "Recevied item form intent : " + expense.toString());

        if (expense != null) {
            //Log.d(TAG,"expense come from intent "+expense.toString());
            dAmount.setText(expense.getAmount());
            dSubjectName.setText(expense.getSubName());
            dDate.setText(expense.getAddDate());
            dCategoryName.setText(expense.getCategoryName());
            dDescription.setText(expense.getDescription());
        } else {
            Toast.makeText(DetailsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            //after 1 second start new activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //start a new activity
                    startActivity(new Intent(DetailsActivity.this, HomeActivity.class));
                    finish();
                }
            }, 1000);
        }
        //click listeners
        dEditButton.setOnClickListener(this);
        dDeleteButton.setOnClickListener(this);

    }

    private void setUpUi() {
        dCategoryName = (TextView) findViewById(R.id.dCatNameId);
        dSubjectName = (TextView) findViewById(R.id.dSubNameId);
        dDate = (TextView) findViewById(R.id.dDateAddedId);
        dAmount = (TextView) findViewById(R.id.dAmountId);
        dDescription = (TextView) findViewById(R.id.dDescriptionId);
        dDeleteButton = (Button) findViewById(R.id.dDeleteButton);
        dEditButton = (Button) findViewById(R.id.dEditButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dDeleteButton: {
                //delete item from database and go to list activity
                expenseDelete();
            }
            break;
            case R.id.dEditButton: {
                //get item details and set it in editExpenseActivity
                expenseEdit();
            }
            break;
            default:{
                Log.d(TAG,"Nothing to do");
            }
        }
    }

    private void expenseEdit() {
        Log.d(TAG,"item to edit : "+expense.toString());

        //set date in proper format
        String bDate = expense.getAddDate();
        long milliSec = DateConvert.BritishDateToMillSecond(bDate);
        DateFormat indianformater = new SimpleDateFormat("dd/MM/yyyy");
        Date iDate = new Date(milliSec);
        String inDate = indianformater.format(iDate);
        expense.setAddDate(inDate);
        Log.d(TAG, "proper expense go to edit : " + expense.toString());

        gotoEditExpenseActivity();

    }

    private void expenseDelete() {

        Toast.makeText(DetailsActivity.this,"Deleted !!",Toast.LENGTH_SHORT).show();


        Log.d(TAG,"expense to delete :" + expense.toString());
        Log.d(TAG,"Before deletion count : "+ db.getCount());
        db.deleteExpense(expense.getId());
        Log.d(TAG,"After deletion count : "+db.getCount());

        gotoListActivity();

    }

    private void gotoEditExpenseActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //start a new activity
                Intent intent = new Intent(DetailsActivity.this, EditExpenseActivity.class);
                intent.putExtra("uToken", String.valueOf("TO1"));
                intent.putExtra("uExpenseObj", expense);
                startActivity(intent);
                finish();
            }
        }, 500);
    }

    private void gotoListActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //start a new activity
                startActivity(new Intent(DetailsActivity.this, ExpenseListActivity.class));
                finish();
            }
        }, 500);
    }
}

package com.boom.quickbooks.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boom.quickbooks.Data.DumCategoryDataServer;
import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.Model.SummaryItem;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Ui.SummaryRecyclerViewAdapter;
import com.boom.quickbooks.Util.CategoryItemManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private ExpenseDbHandler db;
    private List<Expense> listBycategory, expenseList;
    private List<CategoryItem> categoryItemList;
    private static final String TAG = "SummaryActivity";
    private int TOTAL_EXPENSE_AMOUNT = 0;
    private List<SummaryItem> summaryItemList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        db = new ExpenseDbHandler(this);

        //get all expense list
        expenseList = new ArrayList<>();
        expenseList = db.getAllExpense();
        Log.d(TAG,"total expense item count "+ db.getCount());

        //calculate total expense amount by iterating over list
        calculateTotalAmount();

        //initialize summaryItemList
        summaryItemList = new ArrayList<>();

        //get all categoryItems for calculateSummary()
        categoryItemList = new ArrayList<>();
        categoryItemList = DumCategoryDataServer.getCategoryList();

        //calculate percentage Summary Of each category
        calculateSummary();

//        print summaryItemList in Log
        Log.d(TAG,"summaryItemList is which go to adapter "
        +summaryItemList.toString());

        //*******recyclerview stuff********
        recyclerView = (RecyclerView) findViewById(R.id.summaryRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SummaryRecyclerViewAdapter(this,summaryItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //********set Total expense amount*******
        amount = (TextView) findViewById(R.id.totalAmountTextViewId);
        amount.setText(String.valueOf(TOTAL_EXPENSE_AMOUNT)+" Rs.");

    }

    private void calculateSummary() {
        for (CategoryItem c : categoryItemList) {
            double percentageC;
            String tempCategoryName = c.getmCategoryName();

            //initialize summaryItem object for add to summaryItemList
            SummaryItem currentSummaryItem = new SummaryItem();
            //set current categoryName in summaryItem object
            currentSummaryItem.setsCategoryName(tempCategoryName);

            //get expense list with categoryName is equals to tempCategoryName
            listBycategory = new ArrayList<>();
            listBycategory = db.getByCategoryName(tempCategoryName);

            if (!listBycategory.isEmpty()) {

                Log.d(TAG,c.getmCategoryName()+" 's list "+listBycategory.toString());

                percentageC = new CategoryItemManager()
                        .calculateCategoryPercentage(TOTAL_EXPENSE_AMOUNT, listBycategory);

                //set percentageC in summaryItem obj
                currentSummaryItem.setsPercentage(percentageC);
            }
            summaryItemList.add(currentSummaryItem);
        }

    }

    private void calculateTotalAmount() {
        Iterator<Expense> iterator = expenseList.iterator();
        while (iterator.hasNext()) {
            Expense expense = iterator.next();
            TOTAL_EXPENSE_AMOUNT = TOTAL_EXPENSE_AMOUNT + Integer.parseInt(expense.getAmount());
            Log.d(TAG, "now total is " + String.valueOf(TOTAL_EXPENSE_AMOUNT));
        }
        Log.d(TAG, "final now total is " + String.valueOf(TOTAL_EXPENSE_AMOUNT));
    }


}

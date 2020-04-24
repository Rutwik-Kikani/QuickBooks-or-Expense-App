package com.boom.quickbooks.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.boom.quickbooks.Data.DumCategoryDataServer;
import com.boom.quickbooks.Data.DumExpenseDataServer;
import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Util.CategoryItemManager;
import com.boom.quickbooks.Util.DateConvert;

import java.util.ArrayList;
import java.util.List;

public class DbTesterActivity extends AppCompatActivity {

    private static final String TAG = "DbTesterActivity";
    private List<Expense> testExlist,testExlist2;
    private List<CategoryItem> myCatItems,aMyCatItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_tester);

        //NOTE: EACH OPERATION IS INDEPENDENT FOR TESTING UNCOMMENT THAT OPERATION ONLY

        testExlist = new ArrayList<>();
        testExlist2 = new ArrayList<>();
        //get count
        ExpenseDbHandler db = new ExpenseDbHandler(this);
        Log.d(TAG,"DB count "+db.getCount());

        //NOTE: UNCOMMENT IN FIRST RUN AND THEN AFTER ALWAYS COMMENTED INSERT
        //IF YOU DO NOT COMMENT IT THEN IT WILL ADD DUPLICATED DATA IN EACH RUN

        //inserting to db
//        testExlist = DumExpenseDataServer.getExpenseList();
//        for(Expense e : testExlist){
//            db.addExpense(e);
//        }

        //reading all expense from db
        testExlist = db.getAllExpense();
        for(Expense e : testExlist){
            Log.d(TAG,"Reading "+ e.toString());
        }

        //reading all expense by category name
//        testExlist2 = db.getByCategoryName("Bills");
//        for(Expense f : testExlist2){
//            Log.d(TAG,"Reading "+ f.toString());
//        }

        //get particular Expense
//        Expense expense = db.getExpense(1);
//        Log.d(TAG,expense.toString());
//        long mills = DateConvert.BritishDateToMillSecond(expense.getAddDate());
//        Log.d(TAG,String.valueOf(mills));


        //update particular Expense
        //let update Expense with id 1
//            //..first get it
//            Expense expense = db.getExpense(1);
//            Log.d(TAG,"Before updation "+expense.toString());
//            expense.setAddDate("01/04/2000");//applied date should be in form "dd/MM/yyyy"
//            int updId = db.updateExpense(expense);
//            //...again get it and print it
//            expense = db.getExpense(1);
//            Log.d(TAG,"update id "+updId);
//            Log.d(TAG,"after updation "+expense.toString());

        //delete a Expense
//        Log.d(TAG,"before deletion");
//            //read all data
//            testExlist = db.getAllExpense();
//            for(Expense e : testExlist){
//                Log.d(TAG,"Reading "+ e.toString());
//            }
//            //let delete data with id 4
//            Log.d(TAG,"after deletion");
//            db.deleteExpense(4);
//            //then see what happen
//            testExlist = db.getAllExpense();
//            for(Expense e : testExlist){
//            Log.d(TAG,"Reading "+ e.toString());
//            }

//        myCatItems = DumCategoryDataServer.getCategoryList();
//        Log.d(TAG,"Before Adding reading all CategoryItems");
//        for (CategoryItem c : myCatItems){
//            Log.d(TAG,c.toString());
//        }
//        int lIndex = new CategoryItemManager().calculateLastIndex();
//        CategoryItem newCat = new CategoryItem("RoomMate",
//                R.drawable.ic_launcher_background,lIndex);
//        aMyCatItems = DumCategoryDataServer.getCategoryList();
//        DumCategoryDataServer.addCategoryToList(newCat);
//        Log.d(TAG,"After Adding reading all CategoryItems");
//        Log.d(TAG,aMyCatItems.toString());


    }
}

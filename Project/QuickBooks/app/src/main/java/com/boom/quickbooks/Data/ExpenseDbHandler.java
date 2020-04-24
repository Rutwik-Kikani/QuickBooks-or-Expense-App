package com.boom.quickbooks.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.Util.Constants;
import com.boom.quickbooks.Util.DateConvert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDbHandler extends SQLiteOpenHelper {

    private static String TAG = "ExpenseDbHandler";
    private Context ctx;

    public ExpenseDbHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create expense table
        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_SUBJECT_NAME + " TEXT,"
                + Constants.KEY_CATEGORY_NAME + " TEXT,"
                + Constants.KEY_AMOUNT + " TEXT,"
                + Constants.KEY_DESCRIPTION + " TEXT,"
                + Constants.KEY_DATE + " LONG);";
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);

    }

    /**
     * CRUD operation for expenseTBL
     **/

    //add expense
    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addExpense -> inserting.....");
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_SUBJECT_NAME, expense.getSubName());
        values.put(Constants.KEY_CATEGORY_NAME, expense.getCategoryName());
        values.put(Constants.KEY_AMOUNT, expense.getAmount());
        values.put(Constants.KEY_DESCRIPTION, expense.getDescription());
        //convert string date to millis
        String date = expense.getAddDate();
        long millis = DateConvert.IndianDateToMillSecond(date);
        //Log.d(TAG,"addExpense -> date "+date+" millis"+String.valueOf(millis));
        values.put(Constants.KEY_DATE, millis);

        //Insert row
        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addExpense -> saved!! save to db..");


    }

    //get expense
    public Expense getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_SUBJECT_NAME,
                        Constants.KEY_CATEGORY_NAME, Constants.KEY_AMOUNT,
                        Constants.KEY_DESCRIPTION, Constants.KEY_DATE},
                Constants.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Log.d(TAG, "getExpense -> getting.....");
        }
        Expense expense = new Expense();
        expense.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        expense.setSubName(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBJECT_NAME)));
        expense.setCategoryName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORY_NAME)));
        expense.setAmount(cursor.getString(cursor.getColumnIndex(Constants.KEY_AMOUNT)));
        expense.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_DESCRIPTION)));

        //convert timestamp to readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                .getTime());
        //Log.d(TAG,"getExpense -> date"+formattedDate);
        expense.setAddDate(formattedDate);

        Log.d(TAG, "getExpense -> returned..");
        return expense;
    }


    //get all expense
    public List<Expense> getAllExpense() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Expense> expenseList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_SUBJECT_NAME,
                        Constants.KEY_CATEGORY_NAME, Constants.KEY_AMOUNT,
                        Constants.KEY_DESCRIPTION, Constants.KEY_DATE},
                null, null,
                null, null, Constants.KEY_DATE + " DESC");
        if (cursor.moveToFirst()) {
            Log.d(TAG, "getAllExpense -> getting.....");
            do {

                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                expense.setSubName(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBJECT_NAME)));
                expense.setCategoryName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORY_NAME)));
                expense.setAmount(cursor.getString(cursor.getColumnIndex(Constants.KEY_AMOUNT)));
                expense.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_DESCRIPTION)));

                //convert timestamp to readable and then set to expense
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                        .getTime());
                //Log.d(TAG,"getAllExpense-> date"+formattedDate);
                expense.setAddDate(formattedDate);

                expenseList.add(expense);

            } while (cursor.moveToNext());
        }
        Log.d(TAG, "getAllExpense -> list returned...");
        return expenseList;
    }

    //get expense by category name
    public List<Expense> getByCategoryName(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Expense> expenseList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                        Constants.KEY_ID, Constants.KEY_SUBJECT_NAME,
                        Constants.KEY_CATEGORY_NAME, Constants.KEY_AMOUNT,
                        Constants.KEY_DESCRIPTION, Constants.KEY_DATE},
                Constants.KEY_CATEGORY_NAME + "=?",
                new String[]{String.valueOf(categoryName)},
                null, null, Constants.KEY_DATE + " DESC");
        if (cursor.moveToFirst()) {
            Log.d(TAG, "getByCategoryName -> getting.....");
            do {
                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                expense.setSubName(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBJECT_NAME)));
                expense.setCategoryName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORY_NAME)));
                expense.setAmount(cursor.getString(cursor.getColumnIndex(Constants.KEY_AMOUNT)));
                expense.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_DESCRIPTION)));

                //convert timestamp to readable and then set to expense
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                        .getTime());
                //Log.d(TAG,"getAllExpense-> date"+formattedDate);
                expense.setAddDate(formattedDate);

                expenseList.add(expense);

            } while (cursor.moveToNext());
        }
        Log.d(TAG, "getByCategoryName -> list returned...");
        return expenseList;


    }


    //update expense
    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "updateExpense -> updating.....");
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_SUBJECT_NAME, expense.getSubName());
        values.put(Constants.KEY_CATEGORY_NAME, expense.getCategoryName());
        values.put(Constants.KEY_AMOUNT, expense.getAmount());
        values.put(Constants.KEY_DESCRIPTION, expense.getDescription());
        //convert string date to millis
        String date = expense.getAddDate();
        long millis = DateConvert.IndianDateToMillSecond(date);
        //Log.d(TAG,"updateExpense -> date "+date+" millis"+millis);
        values.put(Constants.KEY_DATE, millis);

        Log.d(TAG, "updateExpense -> update confirm returned..");
        return db.update(Constants.TABLE_NAME, values,
                Constants.KEY_ID + "=?", new String[]{
                        String.valueOf(expense.getId())});
    }

    //delete expense
    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteExpense -> deleting.....");
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        Log.d(TAG, "deleteExpense -> deleted..");

    }

    //get count
    public int getCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}

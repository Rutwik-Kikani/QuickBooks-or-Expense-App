package com.boom.quickbooks.Util;

import android.util.Log;

import com.boom.quickbooks.Data.DumCategoryDataServer;
import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.Model.Expense;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryItemManager {
    private List<CategoryItem> categoryItemList;
    private static final String TAG = "CategoryItemManager";
    public CategoryItemManager() {
    }

    public int findCategoryIndex(String categoryName){
        int Index = 0;
        Log.d(TAG,"incoming.."+ categoryName);
        categoryItemList = DumCategoryDataServer.getCategoryList();
        for(CategoryItem c : categoryItemList){
            if(c.getmCategoryName().equals(categoryName)){
                Index = c.getmCatIndex();
            }
        }
        Log.d(TAG,"outgoing .."+ Index);
        return Index;
    }

    public double calculateCategoryPercentage(int total,List<Expense> expenseList){
        double totalAmountByCategory = 0, percentageOfCategory;
        String categoryName = null;
        Iterator<Expense> iterator = expenseList.iterator();
        while (iterator.hasNext()){
            Expense expense = iterator.next();
            totalAmountByCategory =  totalAmountByCategory + Integer.parseInt(expense.getAmount());
            categoryName = expense.getCategoryName();
            
            Log.d(TAG,"Amount of Category :" +expense.getCategoryName()+"->"
                    + expense.getAmount());
        }
        Log.d(TAG,"final total : " + categoryName+"->"+totalAmountByCategory);

        //now calculate percentage;
        percentageOfCategory = ( totalAmountByCategory * 100 )/total;
        Log.d(TAG,categoryName+" percentage -> "+String.valueOf(percentageOfCategory));

        return percentageOfCategory;
    }

    public int calculateLastIndex(){
        int Index = 0;
        categoryItemList = DumCategoryDataServer.getCategoryList();
        for(CategoryItem c : categoryItemList){
            Index++;
        }
        Log.d(TAG,"calculateLastIndex: "+String.valueOf(Index));
        return Index;
    }
}

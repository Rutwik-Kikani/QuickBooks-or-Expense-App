package com.boom.quickbooks.Data;

import com.boom.quickbooks.Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class DumExpenseDataServer {
    public static final List<Expense> expList = new ArrayList<Expense>(){{
        add(new Expense(1, "Dosa","Food","20/10/2021","200","google pay"));
        add(new Expense(2, "google","Bills","5/4/2020","500",""));
        add(new Expense(3, "yahoo","Bills","020/004/02020","2045",""));
        add(new Expense(4, "meal","Restaurant","29/2/2019","209","never mind"));
        add(new Expense(5, "go","Milk","25/01/2018","2080","add to my account"));
        add(new Expense(6, "duck","NewsPaper","20/04/1998","2900","paid_by_Samira"));
        add(new Expense(7, "breakfast","Friend","5/3/1991","2500","paytm_please"));
        add(new Expense(8, "supper","Family","25/4/1971","2040",""));
    }};

    public static List<Expense> getExpenseList(){
        return expList;
    }
}

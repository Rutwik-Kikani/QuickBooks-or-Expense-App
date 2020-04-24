package com.boom.quickbooks.Util;

import com.boom.quickbooks.Model.Expense;

public class ExpenseValidator {
    private Expense gExpense;
    private String status;

    public ExpenseValidator(Expense mExpense) {
        this.gExpense = mExpense;
    }

    public String basicValidateExpense() {
        status = "something Wrong";
        if (gExpense != null) {
            if (!gExpense.getSubName().equals("") &&
                    !gExpense.getAmount().equals("") &&
                    !gExpense.getAddDate().equals("")) {
                status = "OK";
            } else {
                status = "Please Enter Require Field";
            }
        }
        return status;
    }

    public String validateExpenseWithDate() {
        status = "something Wrong";
        if (gExpense != null) {
            if (!gExpense.getSubName().equals("") &&
                    !gExpense.getAmount().equals("") &&
                    !gExpense.getAddDate().equals("")) {

                String Date = gExpense.getAddDate();
                boolean DateValid = DateConvert.validateJavaDate(Date);
                if (DateValid && DateConvert.IndianDateToMillSecond(Date) <= System.currentTimeMillis()) {
                    status = "OK";
                }
                else{
                    status = "Date Invalid";
                }
            }else {
                status = "please enter required field";
            }
        }
        return status;
    }

}

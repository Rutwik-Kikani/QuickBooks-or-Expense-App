package com.boom.quickbooks.Model;

import com.boom.quickbooks.Util.DateConvert;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Expense implements Serializable {
    private int id;
    private String subName;
    private String categoryName;
    private String addDate;
    private String amount;
    private String description;

    public Expense() {
    }

    public Expense(String subName, String categoryName, String addDate, String amount, String description) {
        this.subName = subName;
        this.categoryName = categoryName;
        this.addDate = addDate;
        this.amount = amount;
        this.description = description;
    }

    public Expense(int id, String subName, String categoryName, String addDate, String amount, String description) {
        this.id = id;
        this.subName = subName;
        this.categoryName = categoryName;
        this.addDate = addDate;
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", subName='" + subName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", addDate='" + addDate + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

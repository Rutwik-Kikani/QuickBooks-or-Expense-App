package com.boom.quickbooks.Model;

public class SummaryItem {
    private double sPercentage;
    private String sCategoryName;

    public SummaryItem() {
    }

    public double getsPercentage() {
        return sPercentage;
    }

    public void setsPercentage(double sPercentage) {
        this.sPercentage = sPercentage;
    }

    public String getsCategoryName() {
        return sCategoryName;
    }

    public void setsCategoryName(String sCategoryName) {
        this.sCategoryName = sCategoryName;
    }

    @Override
    public String toString() {
        return "SummaryItem{" +
                "sPercentage=" + sPercentage +
                ", sCategoryName='" + sCategoryName + '\'' +
                '}';
    }
}

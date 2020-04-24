package com.boom.quickbooks.Model;

public class CategoryItem {
    private String mCategoryName;
    private int mCategoryImageResourceId;
    private int mCatIndex;



    public CategoryItem(String mCategoryName, int mCategoryImageResourceId, int mCatIndex) {
        this.mCategoryName = mCategoryName;
        this.mCategoryImageResourceId = mCategoryImageResourceId;
        this.mCatIndex = mCatIndex;

    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public int getmCategoryImageResourceId() {
        return mCategoryImageResourceId;
    }

    public int getmCatIndex() {
        return mCatIndex;
    }

    public void setmCatIndex(int mCatIndex) {
        this.mCatIndex = mCatIndex;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "mCategoryName='" + mCategoryName + '\'' +
                ", mCategoryImageResourceId=" + mCategoryImageResourceId +
                ", mCatIndex=" + mCatIndex +
                '}';
    }
}


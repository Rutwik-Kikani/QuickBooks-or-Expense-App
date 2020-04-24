package com.boom.quickbooks.Data;

import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.R;
import java.util.ArrayList;
import java.util.List;

public class DumCategoryDataServer {
    public static List<CategoryItem> catList = new ArrayList<CategoryItem>(){{
        add(new CategoryItem("Food", R.drawable.ic_food1,0));
        add(new CategoryItem("Drinks",R.drawable.ic_drinks1,1));
        add(new CategoryItem("Restaurants",R.drawable.ic_restaurant1,2));
        add(new CategoryItem("Entertainment",R.drawable.ic_entertainment1,3));
        add(new CategoryItem("Shopping",R.drawable.ic_shopping1,4));
        add(new CategoryItem("Bills",R.drawable.ic_bills1,5));
        add(new CategoryItem("Transport",R.drawable.ic_transport1,6));
        add(new CategoryItem("NewsPaper",R.drawable.ic_newspaper1,7));
        add(new CategoryItem("Fuel",R.drawable.ic_fuel1,8));
        add(new CategoryItem("Education",R.drawable.ic_education1,9));
        add(new CategoryItem("Friend",R.drawable.ic_friends1,10));
        add(new CategoryItem("Family",R.drawable.ic_family1,11));
        add(new CategoryItem("Hospital",R.drawable.ic_hospital1,12));
        add(new CategoryItem("Other",R.drawable.ic_other1,13));
    }};
    public static List<CategoryItem> getCategoryList(){
        return catList;
    }
    public static List<CategoryItem> addCategoryToList(CategoryItem categoryItem){
        catList.add(categoryItem);
        return catList;
    }

}

package com.boom.quickbooks.Ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boom.quickbooks.Model.CategoryItem;
import com.boom.quickbooks.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryItem> {
    private List<CategoryItem> aCList;
    private Context ctx;
    public CategoryAdapter(@NonNull Context context, @NonNull List<CategoryItem> objects) {
        super(context, 0, objects);
        this.aCList = objects;
        this.ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull  ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position,  @Nullable View convertView, @NonNull  ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.spinner_category_row, parent, false
            );
        }

        ImageView catImageViewFlag = (ImageView) convertView.findViewById(R.id.categoryImageId);
        TextView catTextViewName = (TextView) convertView.findViewById(R.id.categoryTextViewId);
        RelativeLayout cardRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.cardRelativeLayout);

        CategoryItem currentItem = aCList.get(position);
        currentItem.setmCatIndex(position);

        if (currentItem != null) {
            catImageViewFlag.setImageResource(currentItem.getmCategoryImageResourceId());
            catTextViewName.setText(currentItem.getmCategoryName());
            if (position % 2 == 0) {
                cardRelativeLayout.setBackgroundResource(R.color.colorPrimary);
            } else {
                cardRelativeLayout.setBackgroundResource(R.color.colorPrimaryDark);
            }
        }

        return convertView;
    }
}

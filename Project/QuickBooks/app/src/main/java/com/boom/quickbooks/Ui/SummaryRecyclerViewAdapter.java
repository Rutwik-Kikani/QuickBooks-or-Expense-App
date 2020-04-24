package com.boom.quickbooks.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.boom.quickbooks.Model.SummaryItem;
import com.boom.quickbooks.R;

import java.util.List;

public class SummaryRecyclerViewAdapter extends RecyclerView.Adapter<SummaryRecyclerViewAdapter.MySViewHolder> {
    private static final String TAG = "SummaryRecyclerViewAdapter";
    private Context ctx;
    private List<SummaryItem> summaryItems;
    private static int count = 0 ;


    public SummaryRecyclerViewAdapter(Context context, List<SummaryItem> summaryItems) {
        this.ctx = context;
        this.summaryItems = summaryItems;
    }

    @NonNull
    @Override
    public SummaryRecyclerViewAdapter.MySViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_row,parent,false);
        return new MySViewHolder(view,ctx);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull SummaryRecyclerViewAdapter.MySViewHolder holder, int position) {
        SummaryItem currentSummary = summaryItems.get(position);
        holder.sCategoryPercent.setText(String.format("%.1f",currentSummary.getsPercentage())+" %");
        holder.sCategoryName.setText(currentSummary.getsCategoryName());
//        if(position % 2 == 0){
//            holder.sCardRelativeLayout.setBackgroundResource(R.color.colorSummaryCardShade1);
//            holder.sCategoryName.setTextColor(ctx.getResources().getColor(R.color.colorSummaryCategoryShade1));
//            holder.sCategoryPercent.setTextColor(ctx.getResources().getColor(R.color.colorSummaryPercentageShade1));
//        }
//        Log.d(TAG,"position "+position);
    }

    @Override
    public int getItemCount() {
        return summaryItems.size();
    }

    public class MySViewHolder extends RecyclerView.ViewHolder{
        private TextView sCategoryName;
        private TextView sCategoryPercent;
        private RelativeLayout sCardRelativeLayout;

        public MySViewHolder(@NonNull View view,Context context) {
            super(view);
            ctx = context;

            sCategoryName = (TextView) view.findViewById(R.id.sCategoryNameId);
            sCategoryPercent = (TextView) view.findViewById(R.id.sPercentageId);
            sCardRelativeLayout = (RelativeLayout) view.findViewById(R.id.sCardRelativeLayoutId);

        }
    }
}

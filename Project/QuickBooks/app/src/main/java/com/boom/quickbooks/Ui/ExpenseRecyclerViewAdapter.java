package com.boom.quickbooks.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boom.quickbooks.Activities.DetailsActivity;
import com.boom.quickbooks.Activities.EditExpenseActivity;
import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Util.DateConvert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Expense> expenseList;
    private LayoutInflater inflater;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private static final String TAG = "ExpenseRecyclerViewAdepter";

    public ExpenseRecyclerViewAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_row, parent, false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRecyclerViewAdapter.MyViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.vCatName.setText(expense.getCategoryName());
        holder.vDate.setText("Date: " + expense.getAddDate());
        holder.vAmount.setText("Amount: " + expense.getAmount());
        holder.cardRelativeLayout.setBackgroundResource(R.color.colorExpenseListCardShade1);
        if(position%2==0){
            holder.cardRelativeLayout.setBackgroundResource(R.color.colorExpenseListCardShade2);
        }

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int id;
        public TextView vCatName, vSubName, vAmount, vDescription, vDate;
        public Button editButton, deleteButton;
        public RelativeLayout cardRelativeLayout;

        public MyViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            vCatName = (TextView) view.findViewById(R.id.lCatNameId);
            vAmount = (TextView) view.findViewById(R.id.lAmountId);
            vDate = (TextView) view.findViewById(R.id.lDateAddedId);
            editButton = (Button) view.findViewById(R.id.leditButton);
            deleteButton = (Button) view.findViewById(R.id.ldeleteButton);
            cardRelativeLayout = (RelativeLayout) view.findViewById(R.id.cardRelativeLayoutId);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Expense expense = expenseList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
//                    intent.putExtra("id",expense.getId());
//                    intent.putExtra("Category",expense.getCategoryName());
//                    intent.putExtra("Subject",expense.getSubName());
//                    intent.putExtra("Amount",expense.getAmount());
//                    intent.putExtra("Date",expense.getAddDate());
//                    intent.putExtra("Description",expense.getDescription());
                    intent.putExtra("aExpenseObj", expense);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ldeleteButton: {
                    //Delete item
                    int position = getAdapterPosition();
                    Expense expense = expenseList.get(position);
                    deleteItem(expense.getId());

                }
                break;
                case R.id.leditButton: {
                    //Edit item screen
                    int position = getAdapterPosition();
                    Expense expense = expenseList.get(position);
                    editItem(expense);

                }
                default: {
                    Toast.makeText(context, "Do Nothing", Toast.LENGTH_SHORT);
                }
            }

        }


        @SuppressLint("LongLogTag")
        public void editItem(final Expense expense) {

            /************expenseDate is in British format convert it to Indian format
             ************And set it to expense **********************************************/
            String bDate = expense.getAddDate();
            long milliSec = DateConvert.BritishDateToMillSecond(bDate);
            final DateFormat indianformater = new SimpleDateFormat("dd/MM/yyyy");
            Date iDate = new Date(milliSec);
            String inDate = indianformater.format(iDate);
            expense.setAddDate(inDate);
            //Log.d(TAG, "bDate " + bDate + " millis " + milliSec + " iDate " + expense.getAddDate());
            Log.d(TAG,"this expense go for updation: " + expense.toString());

            //goto EditExpenseActivity pass expense object as in intent and delay 1s;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //start a new activity
                    Intent intent = new Intent(context, EditExpenseActivity.class);
                    intent.putExtra("uToken",String.valueOf("TO2"));
                    intent.putExtra("uExpenseObj", expense);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }, 1000); //  1 second.


        }

        public void deleteItem(final int id) {

            //create alertDialog box and set up dialog box
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button yesButton = (Button) view.findViewById(R.id.cyesButton);
            Button noButton = (Button) view.findViewById(R.id.cnoButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View v) {
                    //delete item from db
                    ExpenseDbHandler db = new ExpenseDbHandler(context);
                    Log.d(TAG, "Before deletion item count:" + String.valueOf(db.getCount()));
                    //delete expense
                    db.deleteExpense(id);
                    Log.d(TAG, "After deletion item count:" + String.valueOf(db.getCount()));
                    //Change in adepter
                    expenseList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

        }
    }
}

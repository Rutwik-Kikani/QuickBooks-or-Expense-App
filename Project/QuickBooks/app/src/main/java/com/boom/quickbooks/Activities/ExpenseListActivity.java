package com.boom.quickbooks.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.boom.quickbooks.Data.ExpenseDbHandler;
import com.boom.quickbooks.Model.Expense;
import com.boom.quickbooks.R;
import com.boom.quickbooks.Ui.ExpenseRecyclerViewAdapter;
import com.boom.quickbooks.Util.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private static final String TAG = "ExpenseListActivity";
    private static String PERMISSION_STATUS;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Expense> mExList, listitems;
    private ExpenseDbHandler db;
    private static final int RW_STORAGE_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        //get all expenses
        db = new ExpenseDbHandler(this);
        mExList = new ArrayList<>();
        mExList = db.getAllExpense();
//        for(Expense e : mExList)
//        {
//            Log.d(TAG,e.toString());
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
//        FloatingActionButton fab_csv = findViewById(R.id.fab_csv);
        /*********************recyclerView stuff**********************/
//      primary setting
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //        get list to feed it is static list
        //        mExList= new ArrayList<>();
        //        mExList = DumExpenseDataServer.getExpenseList();

//       feed it to recycler view by adapter
        adapter = new ExpenseRecyclerViewAdapter(this, mExList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();

                startActivity(new Intent(ExpenseListActivity.this, AddExpenseActivity.class));
                finish();
            }
        });

        //TODO : Write logic for dynamic csv creating as list increase length of pdf increase;
//        fab_csv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //setup for permission
//                CheckAndRequestForPermission(new String[]{
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                Manifest.permission.READ_EXTERNAL_STORAGE
//                        },
//                        RW_STORAGE_PERMISSION_CODE);
//
//                if (PERMISSION_STATUS.equals("G")) {
//                    //create csv file
//                    try {
//                        createCSVFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

    }

//    private void createCSVFile() throws IOException {
//        //create file Name
//        String timeStamp = new SimpleDateFormat("dd-MMM-yyyy_ss").format(Calendar.getInstance().getTime());
//        String CsvFileName = "CSVFile" + timeStamp;
//        //prepare path
//        String filePath = Environment.getExternalStorageDirectory().getPath() +"/ExpenseTracker/"+ CsvFileName + ".csv";
//        FileWriter filewriter = new FileWriter(filePath);
//        //write header
//        CSVUtils.writeLine(filewriter, Arrays.asList("ID", "SubjectName", "Date", "Description", "Amount"));
//        for (Expense e : mExList) {
//            List<String> mlist = new ArrayList<>();
//            mlist.add(String.valueOf(e.getId()));
//            mlist.add(String.valueOf(e.getAmount()));
//            mlist.add(e.getSubName());
//            mlist.add(e.getDescription());
//            mlist.add(String.valueOf(e.getAddDate()));
//
//            CSVUtils.writeLine(filewriter, mlist);
//        }
//        filewriter.flush();
//        filewriter.close();
//
//    }

//    private boolean createFolder(String directoryName) {
//        File directory = new File(Environment.getExternalStorageDirectory(), directoryName);
//        if (!directory.exists()) {
//            boolean b = directory.mkdirs();
//                Log.d(TAG, String.valueOf(b));
//                return false;
//
//        }
//        Log.d(TAG, "Directory already exists");
//        return true;
//    }

//    private void CheckAndRequestForPermission(String[] permissions, int pRequestCode) {
//        //check for permission
//        if (ContextCompat.checkSelfPermission(ExpenseListActivity.this, permissions[0])
//                == PackageManager.PERMISSION_DENIED &&
//                ContextCompat.checkSelfPermission(ExpenseListActivity.this, permissions[0])
//                        == PackageManager.PERMISSION_DENIED) {
//            //request for permission
//            ActivityCompat.requestPermissions(ExpenseListActivity.this,
//                    permissions, pRequestCode);
//        } else {
//            Toast.makeText(ExpenseListActivity.this,
//                    "Permission Granted Already", Toast.LENGTH_SHORT).show();
//            PERMISSION_STATUS = "G";
//        }
//    }

//   @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == RW_STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Read And Write Permission Granted", Toast.LENGTH_SHORT).show();
//                PERMISSION_STATUS = "G";
//            } else {
//                Toast.makeText(this, "Read And Write Permission Denied", Toast.LENGTH_SHORT).show();
//                PERMISSION_STATUS = "D";
//            }
//        }
//    }


}


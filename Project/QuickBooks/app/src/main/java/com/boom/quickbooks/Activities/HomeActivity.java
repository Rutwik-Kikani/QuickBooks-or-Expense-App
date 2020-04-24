package com.boom.quickbooks.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.boom.quickbooks.R;

import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HomeActivity";
    private View windowView;
    private Button summaryButton;
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        colors = new int[]{Color.RED, Color.BLUE, Color.CYAN, Color.DKGRAY,
                Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.TRANSPARENT,
                Color.WHITE, Color.YELLOW};
        windowView = findViewById(R.id.windowViewId);
        summaryButton = (Button) findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:{
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                go to add expense activity
                Intent intent = new Intent(HomeActivity.this,AddExpenseActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.fab1:{
                Intent intent = new Intent(HomeActivity.this,ExpenseListActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.summaryButton:{
                int colorsArraylenth = colors.length;
                Random randomobj = new Random();
                int randomNum = randomobj.nextInt(colorsArraylenth);
                windowView.setBackgroundColor(colors[randomNum]);
                //goto summary activity with delay of 1sec;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //start a new activity
                        startActivity(new Intent(HomeActivity.this, SummaryActivity.class));
                        //finish();
                    }
                }, 500);
            }
            break;
            default:{
                Log.d(TAG,"Nothing to do");
            }
        }
    }
}

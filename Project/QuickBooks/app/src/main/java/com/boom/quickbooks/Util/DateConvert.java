package com.boom.quickbooks.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvert {
    private static Calendar currentCalender = Calendar.getInstance();
    private static final String TAG = "DataConvert";

    public static long IndianDateToMillSecond(String IndianDate) {
        long millis = 0;
        String mTAG = new StringBuilder(TAG).append(".IndianDateToMillSecond").toString();

        StringBuilder DateBuilder = new StringBuilder(IndianDate);
        //Log.d(mTAG,"given date is : "+IndianDate);
        DateBuilder.append(" " + String.valueOf(currentCalender.get(Calendar.HOUR_OF_DAY)) + ":" + currentCalender.get(Calendar.MINUTE));
        String builtDate = DateBuilder.toString();
        //Log.d(mTAG,"method append HH:mm so.. "+builtDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date date_s = sdf.parse(builtDate);
            millis = date_s.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(mTAG, "Date "+ builtDate +" -> Millisecond " + String.valueOf(millis));
        return millis;
        /** below code will convert millis date in to String like "Mar 1,2020"//pattern MMM d,yyyy **/
//        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
//        String formattedDate = dateFormat.format(new Date(mills).getTime());
//        Log.d("tag1","date in original form"+formattedDate);

    }

    public static long BritishDateToMillSecond(String BritishDate) {
        long millis = 0;
        String mTAG = new StringBuilder(TAG).append(".BritishToMillSecond").toString();

        StringBuilder DateBuilder = new StringBuilder(BritishDate);
        //Log.d(mTAG,"given date is : "+BritishDate);
        DateBuilder.append(" " + String.valueOf(currentCalender.get(Calendar.HOUR_OF_DAY)) + ":" + currentCalender.get(Calendar.MINUTE));
        String builtDate = DateBuilder.toString();
        //Log.d(mTAG,"method append HH:mm so.. "+builtDate);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy HH:mm");

        try {
            Date date_s = sdf.parse(builtDate);
            millis = date_s.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(mTAG, "Date "+builtDate+ " -> Millisecond " + String.valueOf(millis));
        return millis;

        /** below code will convert millis date in to String like "Mar 1,2020"//pattern MMM d,yyyy **/
//        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
//        String formattedDate = dateFormat.format(new Date(mills).getTime());
//        Log.d("tag1","date in original form"+formattedDate);
    }

    public static boolean validateJavaDate(String strDate){
        String mTAG = new StringBuilder(TAG).append(".validateJavaDate").toString();
        if(strDate.trim().equals("")){
            return false;
        }
        else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
                if(javaDate.getTime() < IndianDateToMillSecond("01/01/1900")){
                    return false;
                }
                Log.d(mTAG,strDate+" is valid date format"+javaDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d(mTAG,strDate+" is invalid date format");
                return false;
            }
            return true;
        }
    }
}

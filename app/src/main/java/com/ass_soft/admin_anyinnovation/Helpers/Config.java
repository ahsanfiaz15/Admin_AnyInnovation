package com.ass_soft.admin_anyinnovation.Helpers;

import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
 //   final public static String url="http://192.168.43.164///toop/";
 final public static String url="https://innovatorstower.com//apis1/Toop/";
    public static int chker=0;
    public static int reorder=0;
    public static String getAge(String d){
        String [] array= d.split("/");

        int year=Integer.parseInt(array[2]);
        int month=Integer.parseInt(array[1]);
        int day=Integer.parseInt(array[0]);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

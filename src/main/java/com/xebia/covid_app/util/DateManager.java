package com.xebia.covid_app.util;

import java.util.Calendar;
import java.util.Date;

public class DateManager {
    public static Date setTimeVariable(int hour, int dateInput){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        int day = calendar.get(Calendar.DAY_OF_MONTH)+dateInput;
        calendar.set(Calendar.DAY_OF_MONTH,day);
        Date date = calendar.getTime();
        return date;
    }

    public static int getDay(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            return 5;
        }
        else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
            return 4;
        }
        else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
            return 3;
        }
        else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
            return 2;
        }
        else {
            return 1;
        }
    }
}

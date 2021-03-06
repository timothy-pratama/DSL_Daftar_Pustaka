package Model;

import java.util.Arrays;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Date {
    private int day;
    private int month;
    private int year;
    private String[] MonthName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Des"};
    public Date(){
        day = 0;
        month = 0;
        year = 0;
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSet(){
        if(day == 0 || month == 0 || year == 0){
            return false;
        } else {
            return true;
        }
    }

    public String getMonthString(){
        if(month > 0 && month <=12){
            return MonthName[month-1];
        }
        return null;
    }

    @Override
    public String toString() {
        return "" +year + "-" + month + "-" + day;
    }
}

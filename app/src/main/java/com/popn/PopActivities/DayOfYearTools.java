package com.popn.PopActivities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.*;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Vs1 on 3/14/2018.
 */

public class DayOfYearTools extends junit.framework.TestCase{


    public static void printMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("*** Date / Time Menu ***");
        System.out.println("========================");
        System.out.println("1. Date report");
        System.out.println("2. Days between");
        System.out.println("3. Day of year");
        System.out.println("4. Days remaining");
        System.out.println("5. Days in month");
        System.out.println("6. Month name to number");
        System.out.println("7. Check date validity");
        System.out.println("8. Milliseconds left");
        System.out.println("9. Quit");
      //  System.out.println("Press 11 to postive number");
        int i = scan.nextInt();
        System.out.println("Enter a positive number up to 9: " + i);
        switch (i) {
            case 1:
                System.out.println("Enter the month as a number: ");
                int month4 = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day4 = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year6 = scan.nextInt();
                if(isDateValid(month4,day4,year6)){
                    dateReport(month4, day4, year6);
                }else {

                }
                break;
            case 2:
                Date date1 = null, date2 = null;
                System.out.println("Enter the month as a number: ");
                int month5 = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day5 = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year8 = scan.nextInt();
                String dateFirst = String.valueOf(day5 + "/" +month5+"/"+ year8);

                System.out.println("\n \nEnter the month as a number: ");
                int month7 = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day7 = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year9 = scan.nextInt();
                String dateSecond = String.valueOf(day7+ "/" + month7+ "/" + year9);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date1 = dateFormat.parse(dateFirst);
                    date2 = dateFormat.parse(dateSecond);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean dateFirstValid =isDateValid(month5,day5,year8);
                boolean datesecondValid =isDateValid(month7,day7,year9);
                if(dateFirstValid&&datesecondValid)
                {
                    int numberOfDays = daysBetween(date1, date2);
                    //There are 365 days between 12/31 and 01/01 in the year 2000
                    System.out.println("There are " + numberOfDays + " days between " +month5+"/"+day5  + " and " + +month7+"/"+day7 +" in the year "+year9 );

                }else if(isLeapYear(year8) &&isLeapYear(year9)) {
                    System.out.println("Days must be in the same calendar year.");
                }else
                {
                    System.out.println("One or more of your dates are invalid");
                }

                break;

            case 3:
                System.out.println("Enter the month as a number: ");
                int month = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year1 = scan.nextInt();
                int totalDays = daysToDate(month, day, year1);
                //04/01/2002 is day 091.
                System.out.println(month + "/" + day + "/" + year1 +" is day "+totalDays);
                break;

            case 4:
                System.out.println("Enter the month as a number: ");
                int month1 = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day1 = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year2 = scan.nextInt();
                daysRemaining(month1, day1, year2);
                break;
            case 5:
                System.out.println("Enter the month");
                int month2 = scan.nextInt();
                System.out.println("Enter the Year");
                int year3 = scan.nextInt();
                if(month2>12)
                {
                    // 23 is not a month.
                    System.out.println(month2+" is not a month. ");
                }else{
                    daysInMonth(month2,year3);
                }
                scan.close();
                break;
            case 6:
                System.out.println("Enter the month name:");
                String monthName = scan.next();
                System.out.println(monthName + " is month " + monthNameToNumber(monthName));
                break;
            case 7:
                System.out.println("Enter the month as a number: ");
                int mon = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int da = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int yea = scan.nextInt();
               boolean isValid= isDateValid(mon, da, yea);
                if(isValid){
                    System.out.println(mon+"/"+da+"/"+yea+ " is valid date ");
                }else{

            }
                scan.close();
                break;

            case 8:
                System.out.println("Enter the month as a number: ");
                int month3 = scan.nextInt();
                System.out.println("Enter the day as a number: ");
                int day3 = scan.nextInt();
                System.out.println("Enter the year as a number: ");
                int year4 = scan.nextInt();
                millisecLeftInYear(month3, day3, year4);
                break;
            case 9:
                printMenu();
                break;
            default:
                System.out.println("Invalid number");
                break;
        }

    }

    public static short monthNameToNumber(String month){
        short i=0;
        switch(month){
            case "January":
            case "Jan":
            case "january":
            case "jan":
                i=1;
                break;
            case "February":
            case "Feb":
            case "february":
            case "feb":
                i=2;
                break;
            case "March":
            case "Mar":
            case "march":
            case "mar":
                i=3;
                break;
            case "April":
            case "Apr":
            case "april":
            case "apr":
                i=4;
                break;
            case "May":
            case "may":
                i=5;
                break;
            case "June":
            case "Jun":
            case "june":
            case "jun":
                i=6;
                break;
            case "July":
            case "Jul":
            case "july":
            case "jul":
                i=7;
                break;
            case "August":
            case "Aug":
            case "august":
            case "aug":
                i=8;
                break;
            case "September":
            case "Sep":
            case "september":
            case "sep":
                i=9;
                break;
            case "October":
            case "Oct":
            case "october":
            case "oct":
                i=10;
                break;
            case "November":
            case "Nov":
            case "november":
            case "nov":
                i=11;
                break;

            case "December":
            case "Dec":
            case "december":
            case "dec":
                i=12;
                break;
            default:
                System.out.println(month+" is not a month");
                i=-1;
                break;
        }
        return i;
    }
    public static void dateReport(int month, int day, int year)
    {

            String monthName = monthNumberToName(month);
            System.out.println("Your DATE REPORT for " + monthName + " " + day + ", " + year);

            //It is Day 001 and there are 365 Days Remaining.
            int dayRemain = daysRemaining(month, day, year);
            System.out.println("\n-It is the Day " + day + " and there are " + dayRemain + " Days Remaning");

            // - There are 31 days in January, 2000.
            short monthDay = daysInMonth(month, year);
            System.out.println("\n-There are " + monthDay + " days in January, " + year);

            //The year 2000 is a leap year.
            if (isLeapYear(year)) {
                System.out.println("\n-The year " + year + " is a leap year");
            } else {
                System.out.println("\n-The year " + year + " is not a leap year");
            }
            // There are 31536000000 milliseconds remaining.
            long miliSecond = millisecLeftInYear(month, day, year);
            System.out.println("\n-There are " + miliSecond + " milliseconds remaining.");


    }

    public static long millisecLeftInYear(int month, int day, int year)
    {
        int totalDays = daysRemaining(month, day, year);
        // 1 day = 86400000 ms  private static final long MILLISECONDS_IN_YEAR =

        long milisecondLeft =(1000*60*60*24L*totalDays);
        //There are 15897600000 milliseconds remaining after 06/30/2000. 86400000
        System.out.println("There are " +milisecondLeft+"milliseconds remaining after "+ month+"/"+day+"/"+year);
        return milisecondLeft;
    }

    public static short daysInMonth(int month, int year) {
        // 1 (months begin with 0)
        if (month <=12) {
            int iDay = 1;

            // Create a calendar object and set year and month
            Calendar mycal = new GregorianCalendar(year, month, iDay);

            // Get the number of days in that month
            int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println("There are "+daysInMonth+" in month " + month);
            return (short) month;
        } else {
            System.out.println("Invalid month number");
            return 0;
        }
    }

    public static int daysRemaining(int month, int day, int year) {
        Date dateIst = null, dateRemain = null;
        String dateSecnd=null;
        int total = 0;
        int preDay=01;
        int preMonth=01;
        //  strDate = String.valueOf(month + "/" + day + "/" + year);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String dateFirst =String.valueOf(preDay +"/"+preMonth+"/"+year);
            dateSecnd = String.valueOf(day +"/"+ month+ "/" + year);
            dateIst = dateFormat.parse(dateFirst);
            dateRemain = dateFormat.parse(dateSecnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int totalDays = daysBetween(dateIst, dateRemain);
        if (isLeapYear(year)) {
            total = 366 - totalDays;
            System.out.println("There are "+total+" days " +"remaining after "+ dateSecnd);
        } else {
            total = 365 - totalDays;
          //  There are 274 days remaining after 04/01/2002.
            System.out.println("There are "+total+" days " +"remaining after "+ dateSecnd);
        }
        return total;
    }
    public static int daysToDate(int month, int day, int year) {
        Date dateIst = null, dateRemain = null;
        String dateSecnd=null;
        int total = 0;
        int preDay=01;
        int preMonth=01;
        //  strDate = String.valueOf(month + "/" + day + "/" + year);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String dateFirst =String.valueOf(preDay +"/"+preMonth+"/"+year);
            dateSecnd = String.valueOf(day +"/"+ month+ "/" + year);
            dateIst = dateFormat.parse(dateFirst);
            dateRemain = dateFormat.parse(dateSecnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int totalDays = daysBetween(dateIst, dateRemain);

        return totalDays;
    }

    public static boolean isDateValid(int month, int day, int year) {
        String strDate = null;
        strDate = String.valueOf(month + "/" + day + "/" + year);
        if (month == 0 && day == 0 && year == 0) {
         //   Your date 13/05/2000 is invalid.
            System.out.println("Your date "+strDate+" is invalid.");
            return false;

        } else if (month == 0 || day == 0 || year == 0) {
            System.out.println("Your date "+strDate+" is invalid.");
            return false;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
            sdfrmt.setLenient(false);

            try {
              //  strDate = String.valueOf(month + "/" + day + "/" + year);
               // System.out.println(strDate);
                Date javaDate = sdfrmt.parse(strDate);

            }
            // Date format is invalid
            catch (ParseException e) {
                System.out.println("Your date "+strDate+" is invalid.");
                return false;
            }
            // Return true if date format is valid /
            return true;
        }

    }

    public static int daysBetween(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        int noofdays = (int) (diff / (1000 * 24 * 60 * 60));
        return noofdays;
    }

    public static String monthNumberToName(int month) {
        String name = null;
        switch (month) {
            case 1:
                name = "January";
                System.out.println(name);
                break;
            case 2:
                name = "February";
                System.out.println(name);
                break;
            case 3:
                name = "March";
                System.out.println(name);
                break;
            case 4:
                name = "April";
                System.out.println(name);
                break;
            case 5:
                name = "May";
                System.out.println(name);
                break;
            case 6:
                name = "June";
                System.out.println(name);
                break;
            case 7:
                name = "July";
                System.out.println(name);
                break;
            case 8:
                name = "August";
                System.out.println(name);
                break;
            case 9:
                name = "September";
                System.out.println(name);
                break;
            case 10:
                name = "October";
                System.out.println(name);
                break;
            case 11:
                name = "November";
                System.out.println(name);
                break;
            case 12:
                name = "December";
                System.out.println(name);
                break;
            default:
                System.out.println("Invalid month number");
                break;
        }
        return name;
    }

    public static boolean isLeapYear(int year) {

        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws java.lang.Exception {
        printMenu();
    }
}

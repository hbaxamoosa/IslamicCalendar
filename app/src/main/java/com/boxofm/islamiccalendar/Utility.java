package com.boxofm.islamiccalendar;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by hbaxamoosa on 5/12/17.
 */

public class Utility {

    private static final String TAG = "Utility";
    double J0000 = 1721424.5; // Julian date of Gregorian epoch: 0000-01-01
    double J1970 = 2440587.5; // Julian date at Unix epoch: 1970-01-01
    double JMJD  = 2400000.5; // Epoch of Modified Julian Date system

    public static double dateToJulian(Calendar calendarDate) {
        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH)+1;
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        int hour = calendarDate.get(Calendar.HOUR_OF_DAY);
        int minute = calendarDate.get(Calendar.MINUTE);
        int second = calendarDate.get(Calendar.SECOND);

        double extra = (100.0 * year) + month - 190002.5;
        return (367.0 * year) -
                (Math.floor(7.0 * (year + Math.floor((month + 9.0) / 12.0)) / 4.0)) +
                Math.floor((275.0 * month) / 9.0) +
                day + ((hour + ((minute + (second / 60.0)) / 60.0)) / 24.0) +
                1721013.5 - ((0.5 * extra) / Math.abs(extra)) + 0.5;
    }

    public static double GetJulianDate(Calendar calendarDate){

        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH) + 1;
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        double hour = calendarDate.get(Calendar.HOUR_OF_DAY);
        double minute = calendarDate.get(Calendar.MINUTE);
        double second = calendarDate.get(Calendar.SECOND);
        int isGregorianCal = 1;
        int A;
        int B;
        int C;
        int D;
        double fraction = day + ((hour + (minute / 60) + (second / 60 / 60)) / 24);

        if (year < 1582)
        {
            isGregorianCal = 0;
        }

        if (month < 3)
        {
            year = year - 1;
            month = month + 12;
        }

        A = year / 100;
        B = (2 - A + (A / 4)) * isGregorianCal;

        if (year < 0)
        {
            C = (int)((365.25 * year) - 0.75);
        }
        else
        {
            C = (int)(365.25 * year);
        }

        D = (int)(30.6001 * (month + 1));

        return B + C + D + 1720994.5 + fraction;
    }

    /*
     * GREGORIAN_TO_JD  --  Determine Julian day number from Gregorian calendar date
     */

    public static double GREGORIAN_EPOCH = 1721425.5;

    /*
     * LEAP_GREGORIAN  --  Is a given year in the Gregorian calendar a leap year ?
     */

    public static boolean leap_gregorian(double year) {
        return ((year % 4) == 0) &&
                (!(((year % 100) == 0) && ((year % 400) != 0)));
    }

    public static double gregorian_to_jd(double year, double month, double day) {
        return (GREGORIAN_EPOCH - 1) +
                (365 * (year - 1)) +
                Math.floor((year - 1) / 4) +
                (-Math.floor((year - 1) / 100)) +
                Math.floor((year - 1) / 400) +
                Math.floor((((367 * month) - 362) / 12) +
                        ((month <= 2) ? 0 :
                                (leap_gregorian(year) ? -1 : -2)
                        ) +
                        day);
    }

    /*
     * JD_TO_GREGORIAN  --  Calculate Gregorian calendar date from Julian day
     */

    public static double[] jd_to_gregorian(double jd) {
        double wjd, depoch, quadricent, dqc, cent, dcent, quad, dquad,
                yindex, dyindex, year, yearday, leapadj;

        wjd = Math.floor(jd - 0.5) + 0.5;
        depoch = wjd - GREGORIAN_EPOCH;
        quadricent = Math.floor(depoch / 146097);
        dqc = depoch % 146097;
        cent = Math.floor(dqc / 36524);
        dcent = dqc % 36524;
        quad = Math.floor(dcent / 1461);
        dquad = dcent % 1461;
        yindex = Math.floor(dquad / 365);
        year = (quadricent * 400) + (cent * 100) + (quad * 4) + yindex;
        if (!((cent == 4) || (yindex == 4))) {
            year++;
        }
        yearday = wjd - gregorian_to_jd(year, 1, 1);
        leapadj = ((wjd < gregorian_to_jd(year, 3, 1)) ? 0
                :
                (leap_gregorian(year) ? 1 : 2)
        );
        double month = Math.floor((((yearday + leapadj) * 12) + 373) / 367);
        double day = (wjd - gregorian_to_jd(year, month, 1)) + 1;

        Log.v(TAG, "jd_to_gregorian: " + "year : " + year + " month: " + month + " day: " + day);
        return new double[] {year, month, day};
    }

    /*
     * LEAP_ISLAMIC  --  Is a given year a leap year in the Islamic calendar ?
     */

    public boolean leap_islamic(double year)
    {
        return (((year * 11) + 14) % 30) < 11;
    }

    /*
     * ISLAMIC_TO_JD  --  Determine Julian day from Islamic date
     */

    public static double ISLAMIC_EPOCH = 1948439.5;
    String[] ISLAMIC_WEEKDAYS = new String[] {"al-'ahad", "al-'ithnayn",
            "ath-thalatha'", "al-'arb`a'",
            "al-khamis", "al-jum`a", "as-sabt"};
    String[] ISLAMIC_MONTHS = new String[] {"Muharram", "Safar",
            "Rabi`al-Awwal", "Rabi`al-Akhar",
            "Jumada l-Ula", "Jumada t-Tania",
            "Rajab", "Sha`ban",
            "Ramadan", "Shawwal",
            "Dhu l-Qa`da", "Dhu l-Hijja"
    };

    public static double islamic_to_jd(double year, double month, double day)
    {
        return (day +
                Math.ceil(29.5 * (month - 1)) +
                (year - 1) * 354 +
                Math.floor((3 + (11 * year)) / 30) +
                ISLAMIC_EPOCH) - 1;
    }

    /*
     * JD_TO_ISLAMIC  --  Calculate Islamic date from Julian day
     */

    public static double[] jd_to_islamic(double jd)
    {
        double year, month, day;

        jd = Math.floor(jd) + 0.5;
        year = Math.floor(((30 * (jd - ISLAMIC_EPOCH)) + 10646) / 10631);
        month = Math.min(12,
                Math.ceil((jd - (29 + islamic_to_jd(year, 1, 1))) / 29.5) + 1);
        day = (jd - islamic_to_jd(year, month, 1)) + 1;

        Log.v(TAG, "jd_to_islamic: " + "year : " + year + " month: " + month + " day: " + day);
        return new double[]{year, month, day};
    }
}

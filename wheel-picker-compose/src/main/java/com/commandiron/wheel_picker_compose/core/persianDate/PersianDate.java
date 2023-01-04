package com.commandiron.wheel_picker_compose.core.persianDate;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Saman Zamani(saman.zamani1@gmail.com) on 3/31/2017 AD.
 * <p>
 * Last update on Friday, July 15, 2022
 */
public class PersianDate {

    /*----- Define Variable ---*/
    private Long timeInMilliSecond;
    public static final int FARVARDIN = 1;
    public static final int ORDIBEHEST = 2;
    public static final int KHORDAD = 3;
    public static final int TIR = 4;
    public static final int MORDAD = 5;
    public static final int SHAHRIVAR = 6;
    public static final int MEHR = 7;
    public static final int ABAN = 8;
    public static final int AZAR = 9;
    public static final int DAY = 10;
    public static final int BAHMAN = 11;
    public static final int ESFAND = 12;
    public static final int AM = 1;
    public static final int PM = 2;
    public static final String AM_SHORT_NAME = "ق.ظ";
    public static final String PM_SHORT_NAME = "ب.ظ";
    public static final String AM_NAME = "قبل از ظهر";
    public static final String PM_NAME = "بعد از ظهر";
    private int shYear;
    private int shMonth;
    private int shDay;
    private int grgYear;
    private int grgMonth;
    private int grgDay;
    private int hour;
    private int minute;
    private int second;
    private Locale locale = Locale.getDefault();

    public enum Dialect {
        FINGLISH, AFGHAN, IRANIAN, KURDISH, PASHTO
    }

    /**
     * Contractor
     */
    public PersianDate() {
        this.timeInMilliSecond = new Date().getTime();
        this.init();
    }

    /**
     * Contractor
     */
    public PersianDate(Long timeInMilliSecond) {
        this.timeInMilliSecond = timeInMilliSecond;
        this.init();
    }

    /**
     * Contractor
     */
    public PersianDate(Date date) {
        this.timeInMilliSecond = date.getTime();
        this.init();
    }

    @Override
    public String toString() {
        return PersianDateFormat.format(this, null);
    }

    //region CONST
    private final String[] dayNames = {"شنبه", "یک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه"};
    private final String[] dayFinglishNames = {"Shanbe", "Yekshanbe", "Doshanbe", "Seshanbe", "Chaharshanbe", "Panjshanbe", "Jom'e"};
    private final String[] dayEnglishNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] monthNames = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    private final String[] FinglishMonthNames = {"Farvardin", "Ordibehesht", "Khordad", "Tir", "Mordad", "Shahrivar", "Mehr", "Aban", "Azar", "Day", "Bahman", "Esfand"};
    private final String[] AfghanMonthNames = {"حمل", "ثور", "جوزا", "سرطان", "اسد", "سنبله", "میزان", "عقرب", "قوس", "جدی", "دلو", "حوت"};
    private final String[] KurdishMonthNames = {"جیژنان", "گولان", "زه ردان", "په رپه ر", "گه لاویژ", "نوخشان", "به ران", "خه زان", "ساران", "بفران", "به ندان", "رمشان"};
    private final String[] PashtoMonthNames = {"وری", "غويی", "غبرګولی", "چنګاښ", "زمری", "وږی", "تله", "لړم", "ليندۍ", "مرغومی", "سلواغه", "كب"};

    //endregion
    //region Setter & Getters
    /*---- Setter And getter ----*/
    public Locale getLocale() {
        return locale;
    }

    public PersianDate setLocal(Locale locale) {
        this.locale = locale;
        return this;
    }

    public int getShYear() {
        return shYear;
    }

    public PersianDate setShYear(int shYear) {
        this.shYear = shYear;
        this.changeTime(true);
        return this;
    }

    public int getShMonth() {
        return shMonth;
    }

    public PersianDate setShMonth(int shMonth) {
        this.shMonth = shMonth;
        this.changeTime(true);
        return this;
    }

    public int getShDay() {
        return shDay;
    }

    public PersianDate setShDay(int shDay) {
        this.shDay = shDay;
        this.changeTime(true);
        return this;
    }

    public int getGrgYear() {
        return grgYear;
    }

    public PersianDate setGrgYear(int grgYear) {
        this.grgYear = grgYear;
        changeTime(false);
        return this;
    }

    public int getGrgMonth() {
        return grgMonth;
    }

    public PersianDate setGrgMonth(int grgMonth) {
        this.grgMonth = grgMonth;
        changeTime(false);
        return this;
    }

    public int getGrgDay() {
        return grgDay;
    }

    public PersianDate setGrgDay(int grgDay) {
        this.grgDay = grgDay;
        changeTime(false);
        return this;
    }

    public int getHour() {
        return hour;
    }

    public int get12FormatHour(int hour) {
        return hour <= 12 ? hour : (hour - 12);
    }

    public int get12FormatHour() {
        return this.get12FormatHour(this.hour);
    }

    public PersianDate setHour(int hour) {
        this.hour = hour;
        changeTime(false);
        return this;
    }

    public int getMinute() {
        return minute;
    }

    public PersianDate setMinute(int minute) {
        this.minute = minute;
        changeTime(false);
        return this;
    }

    public int getSecond() {
        return second;
    }

    public PersianDate setSecond(int second) {
        this.second = second;
        changeTime(false);
        return this;
    }

    /**
     * init without time
     *
     * @param year  Year in Grg
     * @param month Month in Grg
     * @param day   Day in Grg
     * @return persianDate
     */
    public PersianDate initGrgDate(int year, int month, int day) {
        return this.initGrgDate(year, month, day, 0, 0, 0);
    }

    /**
     * init with Grg data
     *
     * @param year   Year in Grg
     * @param month  Month in Grg
     * @param day    day in Grg
     * @param hour   hour
     * @param minute min
     * @param second second
     * @return PersianDate
     */
    public PersianDate initGrgDate(int year, int month, int day, int hour, int minute, int second) {
        this.grgYear = year;
        this.grgMonth = month;
        this.grgDay = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        changeTime(false);
        return this;
    }

    /**
     * initialize date from Jallali date
     *
     * @param year  Year in Jallali date
     * @param month Month in Jallali date
     * @param day   day in Jallali date
     * @return PersianDate
     */
    public PersianDate initJalaliDate(int year, int month, int day) {
        return this.initJalaliDate(year, month, day, 0, 0, 0);
    }

    /**
     * initialize date from Jallali date
     *
     * @param year   Year in jallali date
     * @param month  Month in Jallali date
     * @param day    day in Jallali date
     * @param hour   Hour
     * @param minute Minute
     * @param second Second
     * @return PersianDate
     */
    public PersianDate initJalaliDate(int year, int month, int day, int hour, int minute, int second) {
        this.shYear = year;
        this.shMonth = month;
        this.shDay = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.changeTime(true);
        return this;
    }


    /**
     * return time in long value
     *
     * @return Value of time in mile
     */
    public Long getTime() {
        return this.timeInMilliSecond;
    }

    /**
     * Check Grg year is leap
     *
     * @param Year Year
     * @return boolean
     */
    public boolean grgIsLeap(int Year) {
        if (Year % 4 == 0) {
            if (Year % 100 == 0) {
                return Year % 400 == 0;
            }
            return true;
        }
        return false;
    }

    public boolean grgIsLeap() {
        return this.grgIsLeap(this.grgYear);
    }

    /**
     * Check year in Leap
     *
     * @return true or false
     */
    public boolean isLeap() {
        return this.isLeap(this.shYear);
    }

    /**
     * Check custom year is leap
     *
     * @param year int year
     * @return true or false
     */
    public boolean isLeap(int year) {
        double referenceYear = 1375;
        double startYear = 1375;
        double yearRes = year - referenceYear;
        //first of all make sure year is not multiplier of 1375
        if (yearRes == 0 || yearRes % 33 == 0) {
            return true;//year is 1375 or 1375+-(i)*33
        }

        if (yearRes > 0) {
            if (yearRes > 33) {
                double numb = yearRes / 33;
                startYear = referenceYear + (Math.floor(numb) * 33);
            }
        } else {
            if (yearRes > -33) {
                startYear = referenceYear - 33;
            } else {
                double numb = Math.abs(yearRes / 33);
                startYear = referenceYear - (Math.ceil(numb) * 33);
            }
        }
        double[] leapYears = {startYear, startYear + 4, startYear + 8, startYear + 12, startYear + 16, startYear + 20, startYear + 24, startYear + 28, startYear + 33};
        return (Arrays.binarySearch(leapYears, year)) >= 0;
    }

    /**
     * Check static is leap year for Jalali Date
     *
     * @param year Jalali year
     * @return true if year is leap
     */
    public static boolean isJalaliLeap(int year) {
        return (new PersianDate().isLeap(year));
    }

    /**
     * Check static is leap year for Grg Date
     *
     * @param year Year
     * @return boolean
     */
    public static boolean isGrgLeap(int year) {
        return (new PersianDate().grgIsLeap(year));
    }

    /**
     * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
     * Source & Free :: Version: 2.80 : [2020=1399]
     */
    public int[] gregorian_to_jalali(int gy, int gm, int gd) {
        int[] out = {(gm > 2) ? (gy + 1) : gy, 0, 0};
        {
            int[] g_d_m = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
            out[2] = 355666 + (365 * gy) + ((int) ((out[0] + 3) / 4)) - ((int) ((out[0] + 99) / 100)) + ((int) ((out[0] + 399) / 400)) + gd + g_d_m[gm - 1];
        }
        out[0] = -1595 + (33 * ((int) (out[2] / 12053)));
        out[2] %= 12053;
        out[0] += 4 * ((int) (out[2] / 1461));
        out[2] %= 1461;
        if (out[2] > 365) {
            out[0] += (int) ((out[2] - 1) / 365);
            out[2] = (out[2] - 1) % 365;
        }
        if (out[2] < 186) {
            out[1] = 1 + (int) (out[2] / 31);
            out[2] = 1 + (out[2] % 31);
        } else {
            out[1] = 7 + (int) ((out[2] - 186) / 30);
            out[2] = 1 + ((out[2] - 186) % 30);
        }
        return out;
    }

    /**
     * Author: JDF.SCR.IR =>> Download Full Version :  http://jdf.scr.ir/jdf License: GNU/LGPL _ Open
     * Source & Free :: Version: 2.80 : [2020=1399]
     */
    public int[] jalali_to_gregorian(int jy, int jm, int jd) {
        jy += 1595;
        int[] out = {0, 0, -355668 + (365 * jy) + (((int) (jy / 33)) * 8) + ((int) (((jy % 33) + 3) / 4)) + jd + ((jm < 7) ? (jm - 1) * 31 : ((jm - 7) * 30) + 186)};
        out[0] = 400 * ((int) (out[2] / 146097));
        out[2] %= 146097;
        if (out[2] > 36524) {
            out[0] += 100 * ((int) (--out[2] / 36524));
            out[2] %= 36524;
            if (out[2] >= 365) {
                out[2]++;
            }
        }
        out[0] += 4 * ((int) (out[2] / 1461));
        out[2] %= 1461;
        if (out[2] > 365) {
            out[0] += (int) ((out[2] - 1) / 365);
            out[2] = (out[2] - 1) % 365;
        }
        int[] sal_a = {0, 31, ((out[0] % 4 == 0 && out[0] % 100 != 0) || (out[0] % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (out[2]++; out[1] < 13 && out[2] > sal_a[out[1]]; out[1]++) {
            out[2] -= sal_a[out[1]];
        }
        return out;
    }

    /**
     * calc day of week
     *
     * @return int
     */
    public int dayOfWeek() {
        return this.dayOfWeek(this);
    }

    /**
     * Get day of week from PersianDate object
     *
     * @param date persianDate
     * @return int
     */
    public int dayOfWeek(PersianDate date) {
        return this.dayOfWeek(date.toDate());
    }

    /**
     * Get day of week from Date object
     *
     * @param date Date
     * @return int
     */
    public int dayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return 0;
        }
        return (cal.get(Calendar.DAY_OF_WEEK));
    }

    public ArrayList<PersianDate> getWeek(PersianDate date) {
        ArrayList<PersianDate> currentWeek = new ArrayList<>();
        for (int i = 0; i < date.dayOfWeek(); i++) {
            PersianDate dateTmp = new PersianDate(date.timeInMilliSecond);
            currentWeek.add(dateTmp.subDays((date.dayOfWeek() - i)));
        }
        currentWeek.add(date);
        int threshold = (7 - currentWeek.size());
        for (int j = 1; j <= threshold; j++) {
            PersianDate dateTmp = new PersianDate(date.timeInMilliSecond);
            currentWeek.add(dateTmp.addDay(j));
        }
        return currentWeek;
    }

    public PersianDate[] getWeek() {
        return getWeek(this).toArray(new PersianDate[0]);
    }

    /**
     * Return list of month
     *
     * @param dialect dialect
     * @return month names
     */
    public String[] monthList(Dialect dialect) {
        switch (dialect) {
            case FINGLISH:
                return this.FinglishMonthNames;
            case AFGHAN:
                return this.AfghanMonthNames;
            case KURDISH:
                return this.KurdishMonthNames;
            case PASHTO:
                return this.PashtoMonthNames;
            default:
                return this.monthNames;
        }
    }

    /**
     * Return list of month
     *
     * @return month names
     */
    public String[] monthList() {
        return monthList(Dialect.IRANIAN);
    }

    /**
     * return month name
     *
     * @return string
     */
    public String monthName(Dialect dialect) {
        return monthName(this.getShMonth(), dialect);
    }

    /**
     * Return month name
     *
     * @param month Month
     */
    public String monthName(int month, Dialect dialect) {
        switch (dialect) {
            case FINGLISH:
                return this.FinglishMonthNames[month - 1];
            case AFGHAN:
                return this.AfghanMonthNames[month - 1];
            case KURDISH:
                return this.KurdishMonthNames[month - 1];
            case PASHTO:
                return this.PashtoMonthNames[month - 1];
            default:
                return this.monthNames[month - 1];
        }
    }

    /**
     * Get current month name in Persian
     */
    public String monthName() {
        return monthName(Dialect.IRANIAN);
    }

    /**
     * Get month name in Finglish
     */
    public String FinglishMonthName(int month) {
        return this.FinglishMonthNames[month - 1];
    }

    /**
     * Get current date Finglish month name
     */
    public String FinglishMonthName() {
        return this.FinglishMonthName(this.getShMonth());
    }

    /**
     * Get month name in Afghan
     */
    public String AfghanMonthName(int month) {
        return this.AfghanMonthNames[month - 1];
    }

    /**
     * Get current date Afghan month name
     */
    public String AfghanMonthName() {
        return this.AfghanMonthName(this.getShMonth());
    }

    /**
     * Get month name in Kurdish
     */
    public String KurdishMonthName(int month) {
        return this.KurdishMonthNames[month - 1];
    }

    /**
     * Get current date Kurdish month name
     */
    public String KurdishMonthName() {
        return this.KurdishMonthName(this.getShMonth());
    }

    /**
     * Get month name in Pashto
     */
    public String PashtoMonthName(int month) {
        return this.PashtoMonthNames[month - 1];
    }

    /**
     * Get current date Pashto month name
     */
    public String PashtoMonthName() {
        return this.PashtoMonthName(this.getShMonth());
    }

    /**
     * get day name
     */
    public String dayName() {
        return this.dayName(this);
    }

    /**
     * Get Day Name
     */
    public String dayName(PersianDate date) {
        return this.dayNames[this.dayOfWeek(date)];
    }

    /**
     * Get Finglish Day Name
     */
    public String dayFinglishName() {
        return this.dayFinglishName(this);
    }

    /**
     * Get Finglish Day Name
     */
    public String dayFinglishName(PersianDate date) {
        return this.dayFinglishNames[this.dayOfWeek(date)];
    }

    /**
     * Get English Day Name
     */
    public String dayEnglishName() {
        return this.dayEnglishName(this);
    }

    /**
     * Get English Day Name
     */
    public String dayEnglishName(PersianDate date) {
        return this.dayEnglishNames[this.dayOfWeek(date)];
    }

    /**
     * Number days of month
     *
     * @return return days
     */
    public int getMonthDays() {
        return this.getMonthDays(this.getShYear(), this.getShMonth());
    }

    /**
     * calc count of day in month
     */
    public int getMonthDays(int Year, int month) {
        if (month == 12 && !this.isLeap(Year)) {
            return 29;
        }
        if (month <= 6) {
            return 31;
        } else {
            return 30;
        }
    }

    /**
     * calculate day in year
     */
    public int getDayInYear() {
        return this.getDayInYear(this.getShMonth(), getShDay());
    }

    /**
     * Calc day of the year
     *
     * @param month Month
     * @param day   Day
     */
    public int getDayInYear(int month, int day) {
        for (int i = 1; i < month; i++) {
            if (i <= 6) {
                day += 31;
            } else {
                day += 30;
            }
        }
        return day;
    }

    /**
     * Subtract date
     *
     * @param SubYear   Number of subtraction years
     * @param SubMonth  Number of subtraction months
     * @param SubDay    Number of subtraction year days
     * @param SubHour   Number of subtraction year Hours
     * @param SubMinute Number of subtraction year minutes
     * @param SubSecond Number of subtraction year seconds
     * @return this
     */
    public PersianDate subDate(long SubYear, long SubMonth, long SubDay, long SubHour, long SubMinute, long SubSecond) {
        //sub Days
        long dayMustDecrease = SubDay;
        //check if month bigger than a year
        if (SubMonth >= 12) {
            SubYear += (int) Math.floor(SubMonth / 12.0);
            SubMonth = (SubMonth % 12);
        }
        //sub year
        for (int i = 1; i <= SubYear; i++) {
            if (this.isLeap((this.shYear - i))) {
                dayMustDecrease += 366;
            } else {
                dayMustDecrease += 365;
            }
        }

        //sub month
        int tmpYear = this.shYear - (int) SubYear;
        int tmpMonth = this.shMonth;
        for (int i = 0; i < SubMonth; i++) {
            tmpMonth -= 1;
            if (tmpMonth <= 0) {
                tmpYear -= 1;
                tmpMonth = 12;
            }
            dayMustDecrease += this.getMonthLength(tmpYear, tmpMonth);
        }

        this.timeInMilliSecond -= ((dayMustDecrease * 86_400_000L) + (SubHour * 3_600_000) + (SubMinute * 60_000) + (SubSecond * 1_000));
        this.init();
        return this;
    }

    /**
     * Subtract date
     *
     * @param SubYear  Number of subtraction years
     * @param SubMonth Number of subtraction months
     * @param SubDay   Number of subtraction year days
     * @return this
     */
    public PersianDate subDate(long SubYear, long SubMonth, long SubDay) {
        return this.subDate(SubYear, SubMonth, SubDay, 0, 0, 0);
    }

    /**
     * Subtract more than one year
     *
     * @param years: Number of subtraction years
     * @return this
     */
    public PersianDate subYears(int years) {
        return this.subDate(years, 0, 0);
    }

    /**
     * Subtract a year
     *
     * @return this
     */
    public PersianDate subYear() {
        return this.subYears(1);
    }

    /**
     * Subtract months
     *
     * @param months: Number of subtraction months
     * @return this
     */
    public PersianDate subMonths(int months) {
        return this.subDate(0, months, 0);
    }

    /**
     * Subtract a month
     *
     * @return this
     */
    public PersianDate subMonth() {
        return this.subMonths(1);
    }

    /**
     * Subtract days
     *
     * @param days: Number of subtraction days
     * @return this
     */
    public PersianDate subDays(int days) {
        return this.subDate(0, 0, days);
    }

    /**
     * Subtract a day
     *
     * @return this
     */
    public PersianDate subDay() {
        return this.subDays(1);
    }

    /**
     * Subtract hours
     *
     * @param hours: Number of subtraction hours
     * @return this
     */
    public PersianDate subHours(int hours) {
        return this.subDate(0, 0, 0, hours, 0, 0);
    }

    /**
     * Subtract an hour
     *
     * @return this
     */
    public PersianDate subHour() {
        return this.subHours(1);
    }

    /**
     * Subtract minutes
     *
     * @param minutes: Number of subtraction minutes
     * @return this
     */
    public PersianDate subMinutes(int minutes) {
        return this.subDate(0, 0, 0, 0, minutes, 0);
    }

    /**
     * Subtract a minute
     *
     * @return this
     */
    public PersianDate subMinute() {
        return this.subMinutes(1);
    }

    /**
     * Subtract Seconds
     *
     * @param seconds: Number of subtraction seconds
     * @return this
     */
    public PersianDate subSeconds(int seconds) {
        return this.subDate(0, 0, 0, 0, 0, seconds);
    }

    /**
     * Subtract a Second
     *
     * @return this
     */
    public PersianDate subSecond() {
        return this.subSeconds(1);
    }

    /**
     * add date
     *
     * @param AddYear   Number of Year you want add
     * @param AddMonth  Number of month you want add
     * @param AddDay    Number of day you want add
     * @param AddHour   Number of hour you want add
     * @param AddMinute Number of minute you want add
     * @param AddSecond Number of second you want add
     * @return new date
     */
    public PersianDate addDate(long AddYear, long AddMonth, long AddDay, long AddHour, long AddMinute, long AddSecond) {
        //add Days
        long dayMustIncrease = AddDay;
        //check if month bigger than a year
        if (AddMonth >= 12) {
            AddYear += (int) Math.floor(AddMonth / 12.0);
            AddMonth = (AddMonth % 12);
        }
        //add year
        for (int i = 0; i < AddYear; i++) {
            if (this.isLeap((this.shYear + i))) {
                dayMustIncrease += 366;
            } else {
                dayMustIncrease += 365;
            }
        }
        //add month
        int tmpYear = this.shYear - (int) AddYear;
        int tmpMonth = this.shMonth;
        for (int i = 0; i < AddMonth; i++) {
            dayMustIncrease += this.getMonthLength(tmpYear, tmpMonth);
            tmpMonth += 1;
            if (tmpMonth >= 13) {
                tmpYear += 1;
                tmpMonth = 1;
            }
        }
        this.timeInMilliSecond += ((dayMustIncrease * 86_400_000L) + (AddHour * 3_600_000) + (AddMinute * 60_000) + (AddSecond * 1_000));
        this.init();
        return this;
    }

    /**
     * add to date
     *
     * @param year  Number of Year you want add
     * @param month Number of month you want add
     * @param day   Number of day you want add
     */
    public PersianDate addDate(long year, long month, long day) {
        return this.addDate(year, month, day, 0, 0, 0);
    }

    public PersianDate addYear(int year) {
        return this.addDate(year, 0, 0);
    }

    public PersianDate addMonth(int month) {
        return this.addDate(0, month, 0);
    }

    public PersianDate addWeek(int week) {
        return this.addDate(0, 0, (week * 7L));
    }

    public PersianDate addDay(int day) {
        return this.addDate(0, 0, day);
    }

    /**
     * Compare 2 date
     *
     * @param dateInput PersianDate type
     */
    public Boolean after(PersianDate dateInput) {
        return (this.timeInMilliSecond < dateInput.getTime());
    }

    /**
     * compare to data
     *
     * @param dateInput Input
     */
    public Boolean before(PersianDate dateInput) {
        return (!this.after(dateInput));
    }

    /**
     * Check date equals
     */
    public Boolean equals(PersianDate dateInput) {
        return (this.timeInMilliSecond.equals(dateInput.getTime()));
    }

    /**
     * compare two data
     *
     * @return 0 = equal,1=data1 > anotherDate,-1=data1 > anotherDate
     */
    public int compareTo(PersianDate anotherDate) {
        return (this.timeInMilliSecond.compareTo(anotherDate.getTime()));
    }

    /**
     * Return Day in different date
     */
    public long getDayUntilToday() {
        return this.getDayUntilToday(new PersianDate());
    }

    /**
     * Return different just day in compare 2 date
     *
     * @param date date for compare
     */
    public long getDayUntilToday(PersianDate date) {
        long[] ret = this.untilToday(date);
        return ret[0];
    }

    /**
     * Calc different date until now
     */
    public long[] untilToday() {
        return this.untilToday(new PersianDate());
    }

    /**
     * calculate different between 2 date
     *
     * @param date Date 1
     */
    public long[] untilToday(PersianDate date) {
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long different = Math.abs(this.timeInMilliSecond - date.getTime());

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        return new long[]{elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds};
    }

    /**
     * convert PersianDate class to date
     */
    public Date toDate() {
        return new Date(this.timeInMilliSecond);
    }


    /**
     * Return today
     */
    public static PersianDate today() {
        PersianDate persianDate = new PersianDate();
        persianDate.setHour(0).setMinute(0).setSecond(0);
        return persianDate;
    }

    /**
     * Get tomorrow
     */
    public static PersianDate tomorrow() {
        PersianDate persianDate = new PersianDate();
        persianDate.addDay(1);
        persianDate.setHour(0).setMinute(0).setSecond(0);
        return persianDate;
    }

    /**
     * Get start of day
     */
    public PersianDate startOfDay(PersianDate persianDate) {
        persianDate.setHour(0).setMinute(0).setSecond(0);
        return persianDate;
    }

    /**
     * Get Start of day
     */
    public PersianDate startOfDay() {
        return this.startOfDay(this);
    }

    /**
     * Get end of day
     */
    public PersianDate endOfDay(PersianDate persianDate) {
        persianDate.setHour(23).setMinute(59).setSecond(59);
        return persianDate;
    }

    /**
     * Get end of day
     */
    public PersianDate endOfDay() {
        return this.endOfDay(this);
    }

    /**
     * Check midnight
     */
    public Boolean isMidNight(PersianDate persianDate) {
        return persianDate.isMidNight();
    }

    /**
     * Check is midNight
     */
    public Boolean isMidNight() {
        return (this.hour < 12);
    }

    /**
     * Get short name time of the day
     */
    public String getShortTimeOfTheDay() {
        return (this.isMidNight()) ? AM_SHORT_NAME : PM_SHORT_NAME;
    }

    /**
     * Get short name time of the day
     */
    public String getShortTimeOfTheDay(PersianDate persianDate) {
        return (persianDate.isMidNight()) ? AM_SHORT_NAME : PM_SHORT_NAME;
    }

    /**
     * Get time of the day
     */
    public String getTimeOfTheDay() {
        return (this.isMidNight()) ? AM_NAME : PM_NAME;
    }

    /**
     * Get time of the day
     */
    public String getTimeOfTheDay(PersianDate persianDate) {
        return (persianDate.isMidNight()) ? AM_NAME : PM_NAME;
    }

    /**
     * Get number of days in month
     *
     * @param year  Jalali year
     * @param month Jalali month
     * @return number of days in month
     */
    public Integer getMonthLength(Integer year, Integer month) {
        if (month <= 6) {
            return 31;
        } else if (month <= 11) {
            return 30;
        } else {
            if (this.isLeap(year)) {
                return 30;
            } else {
                return 29;
            }
        }
    }

    /**
     * Get number of days in month
     *
     * @param persianDate persianDate object
     * @return number of days in month
     */
    public Integer getMonthLength(PersianDate persianDate) {
        return this.getMonthLength(persianDate.getShYear(), persianDate.getShMonth());
    }

    /**
     * Get number of days in month
     *
     * @return number of days in month
     */
    public Integer getMonthLength() {
        return this.getMonthLength(this);
    }

    //endregion

    /*----- Helper Function-----*/
    //region Private functions

    /**
     * Helper function
     */
    private String textNumberFilter(String date) {
        if (date.length() < 2) {
            return "0" + date;
        }
        return date;
    }

    private void init() {
        this.grgYear = Integer.parseInt(new SimpleDateFormat("yyyy", this.locale).format(this.timeInMilliSecond));
        this.grgMonth = Integer.parseInt(new SimpleDateFormat("MM", this.locale).format(this.timeInMilliSecond));
        this.grgDay = Integer.parseInt(new SimpleDateFormat("dd", this.locale).format(this.timeInMilliSecond));
        this.hour = Integer.parseInt(new SimpleDateFormat("HH", this.locale).format(this.timeInMilliSecond));
        this.minute = Integer.parseInt(new SimpleDateFormat("mm", this.locale).format(this.timeInMilliSecond));
        this.second = Integer.parseInt(new SimpleDateFormat("ss", this.locale).format(this.timeInMilliSecond));

        changeTime(false);
    }

    private void changeTime(boolean isJalaliChanged) {
        if (isJalaliChanged) {
            this.TimeCalcFromJalali(this.shYear, this.shMonth, this.shDay, this.hour, this.minute, this.second);
        } else {
            this.TimeCalcFromGrg(this.grgYear, this.grgMonth, this.grgDay, this.hour, this.minute, this.second);
        }
    }

    private void TimeCalcFromJalali(int year, int month, int day, int hr, int min, int sec) {
        int[] grgTimes = {0/*YEAR*/, 0/*MONTH*/, 0/*DAY*/, 0/*HOUR*/, 0/*MINUTE*/, 0/*SECOND*/};
        int[] jalaliTimes = {year/*YEAR*/, month/*MONTH*/, day/*DAY*/, hr/*HOUR*/, min/*MINUTE*/, sec
/*SECOND*/};
        //convert timestamp to grg date
        int[] convertedTime = this.jalali_to_gregorian(year, month, day);
        grgTimes[0] = convertedTime[0];
        grgTimes[1] = convertedTime[1];
        grgTimes[2] = convertedTime[2];
        grgTimes[3] = hr;
        grgTimes[4] = min;
        grgTimes[5] = sec;
        notify(grgTimes, jalaliTimes);
    }

    private void TimeCalcFromGrg(int year, int month, int day, int hr, int min, int sec) {
        int[] grgTimes = {year/*YEAR*/, month/*MONTH*/, day/*DAY*/, hr/*HOUR*/, min/*MINUTE*/, sec
/*SECOND*/};
        int[] jalaliTimes = {0/*YEAR*/, 0/*MONTH*/, 0/*DAY*/, 0/*HOUR*/, 0/*MINUTE*/, 0/*SECOND*/};
        int[] convertedTime = this.gregorian_to_jalali(year, month, day);
        jalaliTimes[0] = convertedTime[0];
        jalaliTimes[1] = convertedTime[1];
        jalaliTimes[2] = convertedTime[2];
        jalaliTimes[3] = hr;
        jalaliTimes[4] = min;
        jalaliTimes[5] = sec;
        notify(grgTimes, jalaliTimes);
    }

    private void updateTimeStamp() {
        try {
            this.timeInMilliSecond = Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", this.locale).parse("" + this.grgDay + "/" + this.grgMonth + "/" + this.getGrgYear() + " " + this.hour + ":" + this.minute + ":" + this.second)).getTime();//reported in #https://github.com/samanzamani/PersianDate/issues/54
        } catch (ParseException e) {
            this.timeInMilliSecond = new Date().getTime();
        }
    }

    private void notify(int[] grg, int[] jalali) {
        this.grgYear = grg[0];
        this.grgMonth = grg[1];
        this.grgDay = grg[2];
        this.shYear = jalali[0];
        this.shMonth = jalali[1];
        this.shDay = jalali[2];
        this.hour = jalali[3];
        this.minute = jalali[4];
        this.second = jalali[5];
        updateTimeStamp();
    }
    //endregion
}

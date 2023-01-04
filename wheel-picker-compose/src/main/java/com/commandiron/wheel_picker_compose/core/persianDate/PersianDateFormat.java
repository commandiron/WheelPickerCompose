package com.commandiron.wheel_picker_compose.core.persianDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Saman Zamani(saman.zamani1@gmail.com) on 3/31/2017 AD.
 *
 * Last update on Friday, July 15, 2022
 */

public class PersianDateFormat {
  //number Format
  public enum PersianDateNumberCharacter {
    ENGLISH,
    FARSI
  }
  //variable
  /**
   * Key for convert Date to String
   */
  private final String[] key = {"a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y",
      "z", "A",
      "L","X","C","E","P","Q","R"};
  //from version 1.3.3 pattern is public and has de
  private String pattern = "l j F Y H:i:s";
  private PersianDateNumberCharacter numberCharacter = PersianDateNumberCharacter.ENGLISH;

  /**
   * key_parse for convert String to PersianDate
   *
   * yyyy = Year (1396) MM = month (02-12-...) dd = day (13-02-15-...) HH = Hour (13-02-15-...) mm =
   * minutes (13-02-15-...) ss = second (13-02-15-...)
   */
  private final String[] key_parse = {"yyyy", "MM", "dd", "HH", "mm", "ss"};

  /**
   * Constructor for create formatter with just pattern
   */
  public PersianDateFormat(String pattern) {
    this.pattern = pattern;
  }

  /**
   * Constructor for create pattern with number character format
   *
   * @param pattern pattern use for format
   * @param numberCharacter character type can be PersianDateNumberCharacter.FARSI | PersianDateNumberCharacter.English
   */
  public PersianDateFormat(String pattern,PersianDateNumberCharacter numberCharacter) {
    this.pattern = pattern;
    this.numberCharacter = numberCharacter;
  }
  /**
   * Change pattern
   *
   * @param pattern change format pattern
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * Change number character
   *
   * @param numberCharacter number character
   */
  public void setNumberCharacter(
      PersianDateNumberCharacter numberCharacter) {
    this.numberCharacter = numberCharacter;
  }

  /**
   * Constructor without pattern
   */
  public PersianDateFormat() {}

  /**
   * Format date
   *
   *
   * @param date PersianDate object of date
   * @param pattern Pattern you want to show
   * @return date in pattern
   */
  public static String format(PersianDate date, String pattern){
    return format(date,pattern, PersianDateNumberCharacter.ENGLISH);
  }

  /**
   * Convert with charter type
   *
   * @param date date in PersianDate object
   * @param pattern pattern
   * @param numberFormatCharacter number charter
   * @return return date
   */
  public static String format(PersianDate date, String pattern, PersianDateNumberCharacter numberFormatCharacter) {
    if(pattern == null) pattern="l j F Y H:i:s";
    String[] key = {"a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z",
        "A", "L","X","C","E","P","Q","R"};
    String year2;
    if (("" + date.getShYear()).length() == 2) {
      year2 = "" + date.getShYear();
    } else if (("" + date.getShYear()).length() == 3) {
      year2 = ("" + date.getShYear()).substring(2, 3);
    } else {
      year2 = ("" + date.getShYear()).substring(2, 4);
    }
    String[] values = {
        date.getShortTimeOfTheDay(),
        date.dayName(),
        "" + date.getShDay(),
        date.monthName(),
        "" + date.getShYear(),
        textNumberFilterStatic("" + date.getHour()),
        textNumberFilterStatic("" + date.getMinute()),
        textNumberFilterStatic("" + date.getSecond()),
        textNumberFilterStatic("" + date.getShDay()),
        "" + date.get12FormatHour(),
        "" + date.getShMonth(),
        textNumberFilterStatic("" + date.getShMonth()),
        "" + date.getMonthDays(),
        "" + date.dayOfWeek(),
        year2,
        "" + date.getDayInYear(),
        date.getTimeOfTheDay(),
        (date.isLeap() ? "1" : "0"),
        date.AfghanMonthName(),
        date.KurdishMonthName(),
        date.PashtoMonthName(),
        date.FinglishMonthName(),
        date.dayFinglishName(),
        date.dayEnglishName()
    };
    if(numberFormatCharacter == PersianDateNumberCharacter.FARSI){
      PersianDateFormat.farsiCharacter(values);
    }
    for (int i = 0; i < key.length; i++) {
      pattern = pattern.replace(key[i], values[i]);
    }
    return pattern;
  }

  public String format(PersianDate date) {
    String year2;
    if (("" + date.getShYear()).length() == 2) {
      year2 = "" + date.getShYear();
    } else if (("" + date.getShYear()).length() == 3) {
      year2 = ("" + date.getShYear()).substring(2, 3);
    } else {
      year2 = ("" + date.getShYear()).substring(2, 4);
    }
    String[] values = {
        date.getShortTimeOfTheDay(),
        date.dayName(),
        "" + date.getShDay(),
        date.monthName(),
        "" + date.getShYear(),
        this.textNumberFilter("" + date.getHour()),
        this.textNumberFilter("" + date.getMinute()),
        this.textNumberFilter("" + date.getSecond()),
        this.textNumberFilter("" + date.getShDay()),
        "" + date.get12FormatHour(),
        "" + date.getShMonth(),
        this.textNumberFilter("" + date.getShMonth()),
        "" + date.getMonthDays(),
        "" + date.dayOfWeek(),
        year2, "" + date.getDayInYear(),
        date.getTimeOfTheDay(),
        (date.isLeap() ? "1" : "0"),
        date.AfghanMonthName(),
        date.KurdishMonthName(),
        date.PashtoMonthName(),
        date.FinglishMonthName(),
        date.dayFinglishName(),
        date.dayEnglishName()
    };
    if(this.numberCharacter == PersianDateNumberCharacter.FARSI){
      farsiCharacter(values);
    }
    return this.stringUtils(this.pattern, this.key, values);
  }

  /**
   * Parse Jallali date from String
   *
   * @param date date in string
   */
  public PersianDate parse(String date) throws ParseException {
    return this.parse(date, this.pattern);
  }

  /**
   * Parse Jallali date from String
   *
   * @param date date in string
   * @param pattern pattern
   */
  public PersianDate parse(String date, String pattern) throws ParseException {
    ArrayList<Integer> JalaliDate = new ArrayList<Integer>() {{
      add(0);
      add(0);
      add(0);
      add(0);
      add(0);
      add(0);
    }};
    for (int i = 0; i < key_parse.length; i++) {
      if ((pattern.contains(key_parse[i]))) {
        int start_temp = pattern.indexOf(key_parse[i]);
        int end_temp = start_temp + key_parse[i].length();
        String dateReplace = date.substring(start_temp, end_temp);
        if (dateReplace.matches("[-+]?\\d*\\.?\\d+")) {
          JalaliDate.set(i, Integer.parseInt(dateReplace));
        } else {
          throw new ParseException("Parse Exception", 10);
        }
      }
    }
    return new PersianDate()
        .initJalaliDate(JalaliDate.get(0), JalaliDate.get(1), JalaliDate.get(2), JalaliDate.get(3),
            JalaliDate.get(4), JalaliDate.get(5));
  }

  /**
   * Convert String Grg date to persian date object
   *
   * @param date date in String
   * @return PersianDate object
   */
  public PersianDate parseGrg(String date) throws ParseException {
    return this.parseGrg(date, this.pattern);
  }

  /**
   * Convert String Grg date to persian date object
   *
   * @param date date String
   * @param pattern pattern
   * @return PersianDate object
   */
  public PersianDate parseGrg(String date, String pattern) throws ParseException {
    Date dateInGrg = new SimpleDateFormat(pattern).parse(date);
    return new PersianDate(dateInGrg.getTime());
  }

  /**
   * Replace String
   *
   * @param text String
   * @param key Looking for
   * @param values Replace with
   */
  private String stringUtils(String text, String[] key, String[] values) {
    for (int i = 0; i < key.length; i++) {
      text = text.replace(key[i], values[i]);
    }
    return text;
  }

  /**
   * add zero to start
   *
   * @param date data
   * @return return string with 0 in start
   */
  private String textNumberFilter(String date) {
    if (date.length() < 2) {
      return "0" + date;
    }
    return date;
  }

  public static String textNumberFilterStatic(String date) {
    if (date.length() < 2) {
      return "0" + date;
    }
    return date;
  }

  /**
   * Convert English characters to Farsi characters
   *
   * @param values a string array of values
   * @return a converted string array
   */
  public static String[] farsiCharacter(String[] values){
    String[] persianChars = {"۰", "۱", "۲", "٣", "۴", "۵", "۶", "۷", "۸", "٩"};
    String[] englishChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    for (int i = 0; i < values.length; i++) {
      String tmpValue = values[i];
      for (int j = 0; j< persianChars.length;j++){
        tmpValue = tmpValue.replaceAll(englishChars[j],persianChars[j]);
      }
      values[i] = tmpValue;
    }

    return values;
  }
}
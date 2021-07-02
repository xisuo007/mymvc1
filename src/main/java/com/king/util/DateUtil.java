package com.king.util;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类DateUtil.java的实现描述：时间日期处理工具。
 * @author 沈水良 2015年1月27日 上午9:35:30
 */
public class DateUtil extends DateUtils {
 private static Logger logger              = LoggerFactory.getLogger(DateUtil.class);
    
    public static String PATTERNSTR_YMD    ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"; //"yyyy-MM-dd" 验证
    public static String PATTERNSTR_YMD_H  ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29 (0\\d{1}|1\\d{1}|2[0-3]))"; //"yyyy-MM-dd hh24" 验证
    public static String PATTERNSTR_YMD_HM ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$)|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29  (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$)"; //"yyyy-MM-dd hh24:mm" 验证
    public static String PATTERNSTR_YMD_HMS="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$)|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29 (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$)"; //"yyyy-MM-dd hh24:mm:ss"
    
    
    public static String PATTERNSTR_YMD2     ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29)"; //"yyyy-MM-dd" 验证
    public static String PATTERNSTR_YMD_H2   ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29 (0\\d{1}|1\\d{1}|2[0-3]))"; //"yyyy-MM-dd hh24" 验证
    public static String PATTERNSTR_YMD_HM2  ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$)|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29  (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$)"; //"yyyy-MM-dd hh24:mm" 验证
    public static String PATTERNSTR_YMD_HMS2 ="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))) (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$)|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29  (0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$)"; //"yyyy-MM-dd hh24:mm:ss"
    
    public static String PATTERNSTR_YM="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-((0[1-9])|(1[0-2])))";
    public static String PATTERNSTR_Y="([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})";
    
    public static String PATTERNSTR_HMS="((0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$)"; //"hh24:mm:ss"
    public static String PATTERNSTR_HM="((0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$)"; //"hh24:mm"
    public static String PATTERNSTR_H="((0\\d{1}|1\\d{1}|2[0-3])$)"; //"hh24:mm"
    public static String DateMode_1 = "yyyy-MM-dd";
    public static String DateMode_2 = "yyyy-MM-dd HH:mm";
    public static String DateMode_3 = "yyyyMMddHHmmssSSS";
    public static String DateMode_4 = "yyyy-MM-dd HH:mm:ss";
    public static String DateMode_5 = "HHmmssSS";
    public static String DateMode_6 = "yyyyMMdd";
    public static String DateMode_7 = "HHmmss";
    public static String DateMode_8 = "yyyy";
    public static String DateMode_9 = "yyyyMMddHHmmss";
    public static String DateMode_10 = "HH:mm:ss";
    public static String DateMode_11 = "yyyy年MM月dd日HH:mm:ss";
    public static String DateMode_12 = "MM-dd";
    public static SimpleDateFormat date_format         = new SimpleDateFormat(DateMode_1);
    public static SimpleDateFormat date_format_str     = new SimpleDateFormat(DateMode_6);
    public static SimpleDateFormat date_format_allstr  = new SimpleDateFormat(DateMode_9);
    public static SimpleDateFormat datetime_format     = new SimpleDateFormat(DateMode_4);
    public static SimpleDateFormat now_time            = new SimpleDateFormat(DateMode_10);
    public static  String          DATE_REGEX          = "\\d{4}-\\d{1,2}-\\d{1,2}";
    public static  String          DEFAULT_DATE_FORMAT = DateMode_1;
    
    /**
     * 格式化制定的date
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static String formateDate(Date date,SimpleDateFormat simpleDateFormat) {
        if (date == null) {
            return null;
        }
        if(simpleDateFormat==null){
            return null;
        }
        return simpleDateFormat.format(date);
    }
    /**
     * 格式化制定的date
     * @param date
     * @param simpleDateFormatStr
     * @return
     */
    public static String formateDate(Date date,String simpleDateFormatStr) {
        if (date == null) {
            return null;
        }
        if(simpleDateFormatStr==null){
            simpleDateFormatStr =DEFAULT_DATE_FORMAT;
        }
        return formateDate(date,new SimpleDateFormat(simpleDateFormatStr));
    }
    
    /**
     * 字符串转时间 
     * @param dateStr
     * @return
     */
    public static Date parseStdDate(String dateStr,String simpleDateFormatStr) {
        
        if (StringTools.isEmpty(dateStr)){
            return null;
        }
        if (StringTools.isEmpty(simpleDateFormatStr)){
            return null;
        }
        try {
            return new SimpleDateFormat(simpleDateFormatStr).parse(dateStr);
        } catch (Exception ex) {
            logger.error("parseDate error ", ex);
        }
        return null;
    }
    /**
     *  字符串转时间
     * @param dateStr
     * @param dateFormat
     * @return
     */
    
    public static Date parseStdDate(String dateStr,DateFormat dateFormat) {
        
        if (StringTools.isEmpty(dateStr)){
            return null;
        }
        if (dateFormat==null){
            return null;
        }
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception ex) {
            logger.error("parseDate error ", ex);
        }
        return null;
    }
    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String formateDate(Date date) {
        if (date == null) {
            return StringTools.EMPTY_STRING;
        }
        return date_format.format(date);
    }
    /**
     * 全时间格式化
     * @param date
     * @return
     */
    public static String formateDatetime(Date date) {
        if (date == null) {
            return StringTools.EMPTY_STRING;
        }

        return datetime_format.format(date);
    }
    /**
     * 日期加
     * @param date
     * @param days
     * @return
     */
    public static Date addDayFroDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, days);
        }
        return c.getTime();
    }
    /**
     * 日期减
     * @param date
     * @param days
     * @return
     */
    public static Date minusDayForDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -days);
        }
        return c.getTime();
    }
    
    /**
     * 小时加
     * @param date
     * @param hours
     * @return
     */
    public static Date addHourFroDate(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(Calendar.HOUR_OF_DAY, hours);
        }
        return c.getTime();
    }
    
    public static Date addMinuteFroDate(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(Calendar.MINUTE, minutes);
        }
        return c.getTime();
    }
    
    /**
     * 添加月
     * 
     * @param date 日期
     * @param month 月
     * @return
     */
    public static Date addMonthFroDate(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 添加年
     * 
     * @param date 日期
     * @param year 年
     * @return
     */
    public static Date addYearFroDate(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * 添加年,月
     * 
     * @param date 日期
     * @param year 年
     * @param month 月
     * @return
     */
    public static Date addYearAndMonthFroDate(Date date, int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
    /**
     * 获取某天的最小时间
     * @param date
     * @return
     */
    public static Date getDateMin(Date date) {
        if(date==null) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    /**
     * 获取某天的最大时间
     * @param date
     * @return
     */
    public static Date getDateMax(Date date) {
        if(date==null) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
    
    /**
     * startDate加上days是否小于endate，超过返回true，否则返回false
     * 
     * @param startDate
     * @param endDate
     * @param days
     * @return
     */
    public static boolean compareDate(Date startDate, Date endDate, int days) {
        if (startDate == null || endDate == null) {
            return false;
        }
        return addDays(startDate, days).before(endDate);
    }
    
    /**
     * startDate加上days是否小于等于endDate，满足返回true，否则返回false
     * 
     * @param startDate
     * @param endDate
     * @param days
     * @return
     */
    public static boolean compareDateWithEqual(Date startDate, Date endDate, int days) {
        if (startDate == null || endDate == null) {
            return false;
        }
        Date d = addDays(startDate, days);
        int r = d.compareTo(endDate);
        if (r <= 0) {
            return true;
        }
        return false;
    }
    /**
     * 获取一周中的第几天
     * @param date
     * @return
     */
    public static int getWeekByDate(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 获取相差几天
     * @param sd
     * @param ed
     * @return
     */
    public static int getIntervalDayCount(Date sd, Date ed) {
        return (int) ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000) + 1);
    } 
    /**
     * 两个时间戳相差多少天
     * @param sd
     * @param ed
     * @return
     */
    public static int getIntervalDayCount(Long sd, Long ed) {
        return (int) ((ed - sd) / (3600 * 24 * 1000) + 1);
    }
    
    /**
     * 
     * 
     * @param sd
     * @param isIncludeTime Long型日期是否包含时分秒
     * @return
     */
    
    /**
     * long型日期转String型
     * @param sd
     * @param isIncludeTime Long型日期是否包含时分秒
     * @param dateFormat
     * @return
     */
    public static String long2Date(Long sd, boolean isIncludeTime,DateFormat dateFormat) {
        if (isIncludeTime == true) {
            return dateFormat.format(new Date(sd));
        } else {
            return dateFormat.format(new Date(sd * 1000));
        }
    }
    
    /**
     * 获取明日的字符串
     * 
     * @param formater
     * @return
     */
    public static String getNextDaystr(SimpleDateFormat formater) {
        if (formater == null) {
            formater = new SimpleDateFormat();
        }
        Date nextDate = addDayFroDate(new Date(), 1);
        return formater.format(nextDate);
    }

    /**
     * 获取后一天的字符串
     * 
     * @param dateStr
     * @param formater
     * @return
     */
    public static String getNextDaystr(String dateStr, SimpleDateFormat formater) {
        if (StringTools.isEmpty(dateStr)) {
            return null;
        }
        if (formater == null) {
            formater = new SimpleDateFormat();
        }
        Date date = null;
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nextDate = addDayFroDate(date, 1);
        return formater.format(nextDate);
    }
    /**
     * 获取昨日的字符串
     * 
     * @param formater
     * @return
     */
    public static String getAheadDaystr(SimpleDateFormat formater) {
        if (formater == null) {
            formater = new SimpleDateFormat();
        }
        Date nextDate = addDayFroDate(new Date(), -1);
        return formater.format(nextDate);
    }

    /**
     * 获取前一天的字符串
     * 
     * @param dateStr
     * @return
     */
    public static String getAheadDaystr(String dateStr, SimpleDateFormat formater) {
        if (StringTools.isEmpty(dateStr)) {
            return null;
        }
        if (formater == null) {
            formater =new SimpleDateFormat("yyyyMMdd");
        }
        Date date = null;
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nextDate = addDayFroDate(date, -1);
        return formater.format(nextDate);
    }
    
    
    /**
     * 将字符串src按format指定的格式转化为日期对象，本地中文
     * 
     * @param src String
     * @param format String 格式，如yy/MM/dd hh:mm:ss
     * @return Date对象
     * @throws ParseException
     */
    public static Date toDate(String src, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format, java.util.Locale.CHINESE);
        Date d = null;
        d = sdf.parse(src);
        return d;
    }
    
    
    /**
    * 判断一个字符串是否为符合yyyy-MM-dd格式的时间字符串
    * 
    * @param str String
    * @return boolean
    */
   public static boolean isDate(String str) {
       if (StringTools.isEmpty(str)) {
           return false;
       }

       Pattern p = Pattern.compile(DATE_REGEX);
       if (!p.matcher(str).matches()) {
           return false;
       }

       try {
           toDate(str, DEFAULT_DATE_FORMAT);
           return true;
       } catch (Exception e) {
           return false;
       }
   }
   
   /**
    * 获取年
    * 
    * @param data
    * @return
    * @throws ParseException
    */
   public static int getYear(String data) throws ParseException {
       if (StringTools.isEmpty(data)) {
           return 0;
       }
       Date startDate = date_format.parse(data);
       Calendar start = Calendar.getInstance();
       start.setTime(startDate);
       return start.get(Calendar.YEAR);
   }

   /**
    * 获取月
    * 
    * @param data
    * @return
    * @throws ParseException
    */
   public static int getMonth(String data) throws ParseException {
       if (StringTools.isEmpty(data)) {
           return 0;
       }
       Date endDate = date_format.parse(data);
       Calendar end = Calendar.getInstance();
       end.setTime(endDate);
       return end.get(Calendar.MONTH) + 1;
   }
   /**
    * 返回两个时间之间的天数
    * 
    * @param startDay 开始时间
    * @param endDay 结束时间
    * @return
    */
   public static Long getDaysBetweenTwoDays(Date startDay, Date endDay) {

       return (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24);
   }

   /**
    * 返回两个时间之间的天数，endDate - startDate +1
    * 
    * @param startDay
    * @param endDay
    * @return
    */
   public static Long getDaysMaxBetweenTwoDays(Date startDay, Date endDay) {

       return (endDay.getTime() - startDay.getTime()) / (1000 * 60 * 60 * 24) + 1;
   }
   
   /**
    * 获取某个时间段的时间信息
    * @param dBegin
    * @param dEnd
    * @return
    */
   public static List<Date> findDates(Date dBegin, Date dEnd) {  
       List lDate = new ArrayList();  
       Calendar calBegin = Calendar.getInstance();  
       // 使用给定的 Date 设置此 Calendar 的时间    
       calBegin.setTime(dBegin);  
       Calendar calEnd = Calendar.getInstance();  
       // 使用给定的 Date 设置此 Calendar 的时间    
       calEnd.setTime(dEnd);  
       // 测试此日期是否在指定日期之后   
       
       while (dEnd.after(calBegin.getTime())) {  
           // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
           lDate.add(calBegin.getTime()); 
           calBegin.add(Calendar.DAY_OF_MONTH, 1);
       } 
       return lDate;  
   }  
   /**
    * 获取某个时间段的时间信息
    * @param dBegin
    * @param dEnd
    * @return
    */
   public static List<Date> findDateTimes(Date dBegin, Date dEnd) {
       List lDate = new ArrayList();
       Calendar calBegin = Calendar.getInstance();
       // 使用给定的 Date 设置此 Calendar 的时间
       calBegin.setTime(dBegin);
       Calendar calEnd = Calendar.getInstance();
       // 使用给定的 Date 设置此 Calendar 的时间
       calEnd.setTime(dEnd);
       // 测试此日期是否在指定日期之后

       while (dEnd.after(calBegin.getTime())) {
           // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
           lDate.add(calBegin.getTime());
           calBegin.add(Calendar.HOUR_OF_DAY, 1);
       }
       return lDate;
   }
   /**
    * 字符串转时间
    * @param dateStr
    * @return
    */
   public static Date getDate(String dateStr){
       Date date =new Date();
       try{
           if(Pattern.matches(DateUtil.PATTERNSTR_YMD, dateStr)){//yyyy-MM-dd
               SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
               date=sf1.parse(dateStr);
           }else if(Pattern.matches(DateUtil.PATTERNSTR_YM, dateStr)){//yyyy-MM
               SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
               date=sf1.parse(dateStr);
           }else if(Pattern.matches(DateUtil.PATTERNSTR_Y, dateStr)){//yyyy
               SimpleDateFormat sf1 = new SimpleDateFormat("yyyy");
               date=sf1.parse(dateStr);
           }
           return date;
       }catch(ParseException e){
           logger.error("转换时间失败", e);
       }
       return null;
   }
   
   
   public static Date getDate2(String dateStr) throws ParseException{
       Date date =null;
       if(Pattern.matches(DateUtil.PATTERNSTR_YMD, dateStr)){//yyyy-MM-dd
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD2, dateStr)){//yyyy-MM
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy/MM/dd");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_H, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_H2, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy/MM/dd HH");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_HM, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_HM2, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_HMS, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           date=sf1.parse(dateStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_YMD_HMS2, dateStr)){//yyyy
           SimpleDateFormat sf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           date=sf1.parse(dateStr);
       }
       return date;
   }
   /**
    * 判断日期格式:yyyy-mm-dd
    * 
    * @param sDate
    * @return
    */
   public static boolean isValidDate(String sDate) {
       String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
       String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
               + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
               + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
               + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
               + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
               + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
       if ((sDate != null)) {
           Pattern pattern = Pattern.compile(datePattern1);
           Matcher match = pattern.matcher(sDate);
           if (match.matches()) {
               pattern = Pattern.compile(datePattern2);
               match = pattern.matcher(sDate);
               return match.matches();
           } else {
               return false;
           }
       }
       return false;
   }
   /**
    * 是否年月
    * @param sDate
    * @return
    */
   public static boolean isValidYMDate(String sDate) {
       String datePattern1 = "\\d{4}-\\d{2}";
       String datePattern2 = PATTERNSTR_YM;
       if ((sDate != null)) {
           Pattern pattern = Pattern.compile(datePattern1);
           Matcher match = pattern.matcher(sDate);
           if (match.matches()) {
               pattern = Pattern.compile(datePattern2);
               match = pattern.matcher(sDate);
               return match.matches();
           } else {
               return false;
           }
       }
       return false;
   }
   /**
    * 是否年份
    * @param sDate
    * @return
    */
   public static boolean isValidYDate(String sDate) {
       String datePattern1 = "\\d{4}";
       String datePattern2 = PATTERNSTR_Y;
       if ((sDate != null)) {
           Pattern pattern = Pattern.compile(datePattern1);
           Matcher match = pattern.matcher(sDate);
           if (match.matches()) {
               pattern = Pattern.compile(datePattern2);
               match = pattern.matcher(sDate);
               return match.matches();
           } else {
               return false;
           }
       }
       return false;
   }
   
   
   /**
    * 获取某个时间所在月的最后一天
    * @param date
    * @return
    */
   public static Date getLastDayOfMonth(Date date){
       Date lastDayOfMonth = null;
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       //获取某月最大天数
       int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
       //设置日历中月份的最大天数
       cal.set(Calendar.DAY_OF_MONTH, lastDay);
       lastDayOfMonth = cal.getTime();
       return lastDayOfMonth;
   }
   /**
    * 获取某个时间所在月的第一天
    * @param date
    * @return
    */
   public static Date getFirstDayOfMonth(Date date){
       Date firstDayOfMonth = null;
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       //设置日历中月份的第一天
       cal.set(Calendar.DAY_OF_MONTH, 1);
       firstDayOfMonth = cal.getTime();
       return firstDayOfMonth;
   }
   /**
    * 获取某年的最后一天
    * @param date
    * @return
    */
   public static Date getLastDayOfYear(Date date){
       Date lastDayOfMonth = null;
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       //获取某年最大天数
       int lastDay = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
       //设置日历中年份的最大天数
       cal.set(Calendar.DAY_OF_YEAR, lastDay);
       lastDayOfMonth = cal.getTime();
       return lastDayOfMonth;
   }
   /**
    * 获取某年的第一天
    * @param date
    * @return
    */
   public static Date getFirstDayOfYear(Date date){
       Date lastDayOfMonth = null;
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       //设置日历中年份的最大天数
       cal.set(Calendar.DAY_OF_YEAR, 1);
       lastDayOfMonth = cal.getTime();
       return lastDayOfMonth;
   }
   /** 
    * 两个时间相差距离多少天多少小时
    * @param one 时间参数 1 格式：1990-01-01 12:00:00 
    * @param two 时间参数 2 格式：2009-01-01 12:00:00 
    * @return String 返回值为：xx天xx小时
    */  
   public static String getDistanceTime2(Date one, Date two) throws ParseException {  
       long day = 0;  
       long hour = 0;  
       long time1 = one.getTime();  
        long time2 = two.getTime();  
        long diff ;  
        if(time1<time2) {  
            diff = time2 - time1;  
        } else {  
            diff = time1 - time2;  
        }  
        day = diff / (24 * 60 * 60 * 1000);  
        hour = (diff / (60 * 60 * 1000) - day * 24);  
       return day + "天" + hour + "小时" ;  
   } 
   /**
    * 往后推几天的日期
    * 
    * @param dateStr
    * @param
    * @return
    */
   public static Date getNextDay(Date dateStr, int day) {
     
       Date nextDate = addDayFroDate(dateStr, day);
       return nextDate;
   }
   
   public static Date getTime(String timeStr) throws ParseException{
       Date date =null;
       if(Pattern.matches(DateUtil.PATTERNSTR_HM, timeStr)){//yyyy-MM-dd
           SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm");
           date=sf1.parse(timeStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_HMS, timeStr)){//yyyy-MM
           SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm:ss");
           date=sf1.parse(timeStr);
       }else if(Pattern.matches(DateUtil.PATTERNSTR_H, timeStr)){//yyyy-MM
           SimpleDateFormat sf1 = new SimpleDateFormat("HH");
           date=sf1.parse(timeStr);
       }
       return date;
   }
   
}

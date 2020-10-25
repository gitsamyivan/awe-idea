package awe.idea.com.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
    private static Logger log = LoggerFactory.getLogger(DateUtils.class);
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date parseDateFromDatetimeStr(String dateStr) throws ParseException {
        if(StringUtils.isNotBlank(dateStr)){
            SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
            return df.parse(dateStr);
        }
        return null;
    }

    public static Date parseDateFromDateStr(String dateStr) throws ParseException {
        if(StringUtils.isNotBlank(dateStr)){
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            return df.parse(dateStr);
        }
        return null;
    }

    public static Date addDays(Date date,int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,days);
        return calendar.getTime();
    }

    /**
     * 根据指定格式要求格式化日期
     *
     * @throws ParseException
     */
    public static Date formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String str = dateFormat.format(date);
        Date ret = null;

        try {
            ret = dateFormat.parse(str);
        } catch (ParseException e) {
            log.error("字符串转日期异常!", e);
        }
        return ret;
    }

    /**
     * 根据指定日期与天数(可为负数),返回当前日期加上此天数侯的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 根据指定格式 把字符串转成日期格式
     *
     * @param str
     * @param format
     * @return
     */
    public static Date strToDate(String str, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date ret = null;
        try {
            ret = dateFormat.parse(str);
        } catch (ParseException e) {
            log.error("字符串转日期异常!", e);
        }
        return ret;
    }

    /**
     * 根据指定格式把日期转成字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToStr(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


}

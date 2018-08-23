package com.okman.simple.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 *
 * @auth waxuan
 * @since 2018年8月6日下午5:51:24
 */
public class DateUtil {
	
	public static void main(String[] args) {
		System.out.println(dateToStr(new Date(1534901943701l)));
	}
	
	
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		String format = "yyyy-MM-dd HH:mm:ss";
		return strToDate(strDate, format);
	}

	/**
	 * 将时间格式字符串转换为时间
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 日期类型转字符串yyyy-MM-dd HH:mm:ss
	 *
	 * @auth waxuan
	 * @since 2018年8月7日上午10:01:42
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		return dateToStr(date, format);
	}
	
	/**
	 * 日期类型转字符串
	 *
	 * @auth waxuan
	 * @since 2018年8月7日上午10:01:42
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
	}

	/**
	 * 获取相差天数，有正负
	 *
	 * @auth waxuan
	 * @since 2018年8月6日下午5:59:11
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int differDay(Date d1, Date d2) {
		long t1 = d1.getTime();
		long t2 = d2.getTime();
		return (int) ((t1 - t2) / 86400000);
	}

}

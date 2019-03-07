package com.elasticsearch.root.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

/**
 * 时间处理工具
 * 
 * @author Administrator
 *
 */
public class DateDealUtils {
	/**
	 * 将0时区时间，格式化为东八区时间字符串
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date timeZoneToEight(String date, String format) throws ParseException {
		if (!StringUtils.isEmpty(date)) {
			date = date.replace("Z", " UTC");
			SimpleDateFormat formatZero = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			formatZero.setTimeZone(TimeZone.getTimeZone("GMT"));
			return formatZero.parse(date);
		} else {
			return null;
		}
	}

	/**
	 * 将0时区时间，格式化为东八区时间字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date timeZoneToEight(Date date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String dateString = sdf.format(date);
		Date returnDate = sdf.parse(dateString);
		return returnDate;
	}

	/**
	 * date转String
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String dateString = sdf.format(date);
		return dateString;
	}

	/**
	 * 时间字符串转date
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

//	public static void main(String[] args) throws ParseException {
//		System.out.println(DateDealUtils.timeZoneToEight("2000-06-03T11:11:11.000Z", "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateDealUtils.timeZoneToEight(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateDealUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//	}
}

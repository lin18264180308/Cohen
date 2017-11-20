package com.mrong.lineview.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式处理
 * 
 * @author 林金成 2017年10月18日
 */
@SuppressWarnings("all")
public class TimeUtils {

	/**
	 * 将时间字符串转换为Date类型
	 * 
	 * @param timeStr
	 *            : 时间字符串
	 * @return : 日期
	 */
	public static Date toDate(String timeStr) {
		Calendar time = Calendar.getInstance();
		String[] strs = timeStr.split("-");
		time.set(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]) - 1, Integer.parseInt(strs[2]),
				Integer.parseInt(strs[3]), Integer.parseInt(strs[4]));
		return time.getTime();
	}

	/**
	 * 获取两个Date日期相差的分钟数
	 * 
	 * @param begin
	 *            : 开始日期
	 * @param end
	 *            : 结束日期
	 * @return : 相差分钟数
	 */
	@SuppressWarnings("deprecation")
	public static int getDisparityOfMinuts(Date begin, Date end) {
		int b = begin.getDate() * 24 * 60 + begin.getHours() * 60 + begin.getMinutes();
		int e = end.getDate() * 24 * 60 + end.getHours() * 60 + end.getMinutes();
		return e - b;
	}

	/**
	 * 获取两个Date日期相差的分钟数
	 * 
	 * @param begin
	 *            : 开始日期
	 * @param end
	 *            : 结束日期
	 * @return : 相差分钟数
	 */
	public static long getDisparityOfSecond(Date begin, Date end) {
		long b = begin.getTime();
		long e = end.getTime();
		long result = (e - b) / 1000;
		return result;
	}

	public static long calculate(Date d1, Date d2) {
		long l, x = 0;
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			l = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			x = l / 1000;
			// System.out.println("秒++" + x);
			long days = l / (1000 * 60 * 60 * 24);
			long hours = (l - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (l - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
			// System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
		} catch (Exception e) {
		}
		return x;
	}

}
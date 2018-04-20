package com.zlin.property.tools;

import android.annotation.SuppressLint;
import android.content.Context;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SuppressLint("SimpleDateFormat")
public class Tool {
	/**
	 * 使用MD5算法进行加密
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	public static String encryption2(String plainText) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte tmp[] = md.digest();

			char str[] = new char[16 * 2];
			int k = 0;
			byte b;
			for (int i = 0; i < 16; i++) {
				b = tmp[i];
				str[k++] = hexDigits[b >>> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			plainText = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;
	}

	/**
	 * 获取一个首位为字母后加随机数字的指定长度的随机字符串
	 * 
	 * @param length
	 *            指定的长度
	 * @return 长度最小为1的随机字符串
	 */
	public static String GetRandomNumber(int length) {

		String[] first = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		String result = "";
		// 随机数
		Random random = new Random();
		result += first[random.nextInt(26)];

		for (int i = 1; i < length; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 描 述：密度转换成像素<br/>
	 * 作 者：ruji<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param dipValue
	 * @param context
	 * @return
	 */
	public static int dipTopx(float dipValue, Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 描 述：像素转换成密度<br/>
	 * 作 者：ruji<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param pxValue
	 * @param context
	 * @return
	 */
	public static int psTopx(float pxValue, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int dipValue = (int) (pxValue / scale + 0.5f);
		return (int) (dipValue * scale + 0.5f);
	}

	// 格式到天
	public static String getDay(long time) {

		return new SimpleDateFormat("yyyy-MM-dd").format(time);

	}

	// 格式到分钟
	public static String getDateToString(long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日    HH:mm");
		return sf.format(d);
	}

	/**
	 * @param str 2016-09-01
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public static String getWeekOfDate(String str) {
		String[] ymd = str.split("-");
		Date date = new Date(Integer.parseInt(ymd[0]),
				Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]) - 1);
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
}

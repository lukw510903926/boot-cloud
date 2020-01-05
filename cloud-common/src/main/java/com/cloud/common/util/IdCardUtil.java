package com.cloud.common.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardUtil {

    /**
     * 中国公民身份证号码最小长度。
     */
    public final int CHINA_ID_MIN_LENGTH = 15;

    /**
     * 中国公民身份证号码最大长度。
     */
    public final int CHINA_ID_MAX_LENGTH = 18;

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard 身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard 身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(1 - 男 ， 2 - 女)
     */
    public static String getGenderByIdCard(String idCard) {
        String sex = "";
        if (idCard.matches("^\\d{15}$|^\\d{17}[\\dxX]$")) {
            if (idCard.length() == 18) {
                Pattern pattern = Pattern.compile("\\d{6}(\\d{4})\\d{6}(\\d{1})[\\dxX]{1}");
                Matcher matcher = pattern.matcher(idCard);
                if (matcher.matches()) {
                    sex = "" + Integer.parseInt(matcher.group(2)) % 2;
                }
            } else if (idCard.length() == 15) {
                Pattern p = Pattern.compile("\\d{6}(\\d{2})\\d{5}(\\d{1})\\d{1}");
                Matcher m = p.matcher(idCard);
                if (m.matches()) {
                    sex = "" + Integer.parseInt(m.group(2)) % 2;
                }
            }
        }
        if ("0".equals(sex)) {
			sex = "2";
		}
        return sex;
    }
}

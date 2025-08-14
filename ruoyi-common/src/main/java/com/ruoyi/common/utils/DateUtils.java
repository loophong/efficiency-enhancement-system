package com.ruoyi.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            String dateStr = str.toString().trim();

            // 处理日期范围格式（如：2024.4.19-2024.12.30）
            if (dateStr.contains("-") && !dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                // 可能是日期范围，提取起始日期
                String[] parts = dateStr.split("-");
                if (parts.length >= 2) {
                    // 取第一个日期作为起始日期
                    dateStr = parts[0].trim();
                }
            }

            // 检查是否是无效的年份（年份超过9999或小于1900）
            if (dateStr.matches("^\\d{5,}[./-].*") || dateStr.matches("^\\d{1,3}[./-].*")) {
                // 如果年份异常，返回null而不是抛出异常
                return null;
            }

            // 检查年份是否在合理范围内（1900-2100）
            if (dateStr.matches("^\\d{4}[./-].*")) {
                String yearStr = dateStr.substring(0, 4);
                int year = Integer.parseInt(yearStr);
                if (year < 1900 || year > 2100) {
                    return null;
                }
            }

            return parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算时间差
     *
     * @param endDate   最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(Date endDate, Date startTime) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * @description: 获取指定日期前的日期
     * @author: hong
     * @date: 2025/1/20 10:26
     * @version: 1.0
     */
    public static Date getDateBefore(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        return calendar.getTime();
    }

    /**
     * @description: 获取指定日期后的日期
     * @author: hong
     * @date: 2025/1/20 10:26
     * @version: 1.0
     */
    public static Date getDateAfter(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +days);
        return calendar.getTime();
    }

    /**
     * @description: 格式化日期输出格式为yyyy-MM-dd格式字符串
     * @author: hong
     * @date: 2025/1/20 10:28
     * @version: 1.0
     */
    public static String formatOutput(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * @description: 获取当前月份的下一个月
     * @author: hong
     * @date: 2025/2/20 16:06
     * @version: 1.0
     */
    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }


    /**
     * @description: 获取当前时间的上一年
     * @author: hong
     * @date: 2025/3/4 16:43
     * @version: 1.0
     */
    public static Date getPreviousYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     *  获取本月第一天
     * @param happenTime
     * @return
     */
    public static Date getMonthFirstDay(Date happenTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(happenTime);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     *  获取上个月的最后一天
     * @param endTime
     * @return
     */
    public static Date getLastMonthEndDay(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        // 设置为当前月份的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 将月份减去1，表示上个月
        calendar.add(Calendar.MONTH, -1);
        // 设置为最后一秒（23:59:59.999）
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        // 设置为上个月的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getMonthEndDay(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);

        // 设置为当前月份的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 设置为当前月份的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 设置为最后一秒（23:59:59.999）
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
}

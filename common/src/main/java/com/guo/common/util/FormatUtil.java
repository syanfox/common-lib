package com.guo.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;



public class FormatUtil {
    public static String DF_PATTERN = "###0.00";
    public static DecimalFormat DF;
    public static DecimalFormat format;

    public FormatUtil() {
    }

    public static String formatValue(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        return decimalFormat.format(value);
    }

    public static String formatNum(Double value, String pattern) {
        if(pattern == null || "".equals(pattern.trim())) {
            pattern = "#,###.##";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    public static String formatNum(Double value) {
        return formatNum(value, (String)null);
    }

    public static String formatSum(Double value) {
        String pattern = "#,##0.00";
        return formatNum(value, pattern);
    }

    public static String formatNum(Double value, int length) {
        int intLength = length - 2;
        return (double)value.intValue() >= Math.pow(10.0D, (double)intLength)?value.intValue() + "":formatNum(value, DF_PATTERN);
    }

    public static String formatFore(Double value) {
        String pattern = "####.####";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setGroupingSize(0);
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(value);
    }

    public static String formatInt(Integer value) {
        return formatInt(value, (String)null);
    }

    public static String formatInt(Integer value, String pattern) {
        if(pattern == null || "".equals(pattern.trim())) {
            pattern = "#,##0";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    public static String formatDoubleValueUselessZero(double value) {
        String str = format.format(value);
        if(str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");
            str = str.replaceAll("[.]$", "");
        }

        return str;
    }

    public static double formatHalfUp(double value, int scale) {
        String str = format.format(value);
        if(scale < 0) {
            throw new IllegalArgumentException("小数位数不能小于0!");
        } else {
            BigDecimal decimal = (new BigDecimal(str)).setScale(scale, 4);
            return decimal.doubleValue();
        }
    }

    static {
        DF = new DecimalFormat(DF_PATTERN);
        format = new DecimalFormat("###0.0000");
    }
}

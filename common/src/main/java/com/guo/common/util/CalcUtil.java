package com.guo.common.util;

import java.math.BigDecimal;

/**
 * 类说明:计算工具类
 */
public class CalcUtil {

    private static final int DEF_DIV_SCALE = 2;

    /**
     * 两个Double数相加
     * @param v1
     * @param v2
     * @return Double
     */
    public static double plus(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    /**
     * 两数相减
     * @param v1
     * @param v2
     * @return
     */
    public static double minus(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两数相乘
     * @param v1
     * @param v2
     * @return
     */
    public static double multiply(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 两个Double数相除, 保留2位小数
     * @param v1
     * @param v2
     * @return Double
     */
    public static double divider(Double v1, Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个Double数相除，并保留scale位小数
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static double divider(Double v1, Double v2, int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化
     * @param d
     * @param digit
     * @return
     */
    public static double formatting(Double d, int digit){
        BigDecimal bigDec = new BigDecimal(d);
        return bigDec.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}

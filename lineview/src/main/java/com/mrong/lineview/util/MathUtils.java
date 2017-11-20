package com.mrong.lineview.util;

import java.text.DecimalFormat;

/**
 * 数据类型工具类
 * 
 * @author 林金成
 *         2017年11月15日
 */
public class MathUtils {

    /**
     * 保留两位小数
     */
    public static Double formatTwoBit(Double data) {
        Double result = null;
        DecimalFormat df = new DecimalFormat("######0.00");
        result = Double.parseDouble(df.format(Double.parseDouble(data.toString().split("E")[0])));
        return result;
    }
}

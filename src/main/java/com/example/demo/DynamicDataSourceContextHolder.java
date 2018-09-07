package com.example.demo;

import com.example.demo.datasoure1.pojo.TestInfo;
import com.example.demo.enumDemo.DataSoureKey;
import com.example.demo.util.RandomUtils;

public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<DataSoureKey> currentDatesource=new ThreadLocal<>();
    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDatesource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSoureKey get() {
        return currentDatesource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSoureKey value) {
        currentDatesource.set(value);
    }
      /**
     * 设置从从库读取数据
     * 采用简单生成随机数的方式切换不同的从库
     */
    public static void setSlave() {
        if (RandomUtils.nextInt(2)  > 0) {
            DynamicDataSourceContextHolder.set(DataSoureKey.datasource2);
        } else {
            DynamicDataSourceContextHolder.set(DataSoureKey.datasource3);
        }
    }


}

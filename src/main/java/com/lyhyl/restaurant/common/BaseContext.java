package com.lyhyl.restaurant.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存登录用户的Id
 */
public class BaseContext {
    public static  ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
      return  threadLocal.get();
    }
}

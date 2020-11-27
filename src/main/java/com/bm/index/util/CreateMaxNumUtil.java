package com.bm.index.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CreateMaxNumUtil {
    private static AtomicInteger atomicNum = new AtomicInteger();
    public static String createMaxNumUtil(){
        //序列号初始值为当前年份+四位自增的流水号
        int intValue = Integer.parseInt(DateUtil.getYear()+"0000");
        //获取当前最大值
        int curValue = atomicNum.get();
        System.out.println("当前最大号为"+curValue);
        if(0==atomicNum.get()){
            atomicNum.set(intValue);
        }
        //最新的加一后的最大号
        int newValue = atomicNum.incrementAndGet();
        String newValueStr = String.valueOf(newValue);
        return newValueStr;
    }
}

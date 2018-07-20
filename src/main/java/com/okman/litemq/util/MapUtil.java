package com.okman.litemq.util;

import java.util.Map;
import java.util.Random;

public class MapUtil {

	/**
     * 从map中随机取得一个key
     * @param map
     * @return
     */
    public static <K,V> K getRandomKeyFromMap(Map<K,V> map) {
        int rn = getRandomInt(map.size());
        int i = 0;
        for (K key : map.keySet()) {
            if(i==rn){
                return key;
            }
            i++;
        }
        return null;
    }
    
    /**
     * 从map中随机取得一个value
     * @param map
     * @return
     */
    public static <K,V> V getRandomValueFromMap(Map<K,V> map) {
        int rn = getRandomInt(map.size());
        int i = 0;
        for (V value : map.values()) {
            if(i==rn){
                return value;
            }
            i++;
        }
        return null;
    }
    
    /**
     * 获得一个[0,max)之间的整数。
     * @param max
     * @return
     */
    public static int getRandomInt(int max) {
        return Math.abs(new Random().nextInt()) % max;
    }
	
}

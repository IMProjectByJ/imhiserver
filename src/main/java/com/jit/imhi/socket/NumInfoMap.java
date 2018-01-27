package com.jit.imhi.socket;

import com.jit.imhi.model.Numinfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NumInfoMap {

    static Map<String, Map<String, Numinfo>> mapNuminfo = new HashMap<String, Map<String, Numinfo>>();

    public NumInfoMap() {
    }

    public static Numinfo getMap(String to, String key) {
        return mapNuminfo.get(to).get(key);
    }


    //set
    public static void setMapCache(String userid, Map<String, Numinfo> numinfo) {
        mapNuminfo.put(userid, numinfo);
    }
    public static void ceshi(){
        java.util.Iterator it1 = mapNuminfo.entrySet().iterator();
        while(it1.hasNext()){
            System.out.println("测试是否有值");
            java.util.Map.Entry entry = (java.util.Map.Entry)it1.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().toString());
        }
    }
}

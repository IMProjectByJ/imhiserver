package com.jit.imhi.utils;

import java.util.Random;

public class GetRandom {

    public static String getRandomString(int length)
    {
        String base = "abcdefghijklmnopqrstuvwxyz1234567890_@";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i=0; i < length ; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomNumber(int length)
    {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int number = 1+ random.nextInt(base.length()-1);
        sb.append(base.charAt(number));
        for (int i=1; i < length; i++) {
            number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

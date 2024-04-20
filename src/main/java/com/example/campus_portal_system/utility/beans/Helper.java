package com.example.campus_portal_system.utility.beans;

import java.util.Random;

public class Helper {

    public static String getPassCode(int length) {
        String passCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * passCode.length());
            salt.append(passCode.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}

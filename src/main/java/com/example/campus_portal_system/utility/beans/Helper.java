package com.example.campus_portal_system.utility.beans;

import java.util.Random;

public class Helper {

    public static String getPassCode() {
        String passCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 12) { // length of the random string.
            int index = (int) (rnd.nextFloat() * passCode.length());
            salt.append(passCode.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}

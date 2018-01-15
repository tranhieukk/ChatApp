package com.example.trantrunghieu.chatchit.Estring;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tran Trung Hieu on 1/14/18.
 */

public class Encrypt {
    private static Encrypt instance;

    private Encrypt() {
    }


    public static Encrypt getInstance() {
        if(instance == null)
            instance = new Encrypt();
        return instance;
    }

    private static void setInstance(Encrypt instance) {
        Encrypt.instance = instance;
    }

    public String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

package com.uiresource.cookit.Estring;



import android.util.Base64;

import java.io.UnsupportedEncodingException;
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

    public String TexttoBase64(String text) throws UnsupportedEncodingException {

        // Sending side
        byte[] data = text.getBytes("UTF-8");
        String base64;
        switch (base64 = Base64.encodeToString(data, Base64.DEFAULT)) {
        }
        return  base64.replaceAll("\\n","");
    }
    public String Base64toText(String base64) throws UnsupportedEncodingException {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String text = new String(data, "UTF-8");
        return  text.replaceAll("\\n","");
    }
}

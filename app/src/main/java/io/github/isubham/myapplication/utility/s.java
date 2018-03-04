package io.github.isubham.myapplication.utility;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by suraj on 2/3/18.
 */

public class s {

    // @reference https://dzone.com/articles/storing-passwords-java-web
    public static String salty(String s) {

        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(s.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

    // text
    public static String text(TextView V) {

        return V.getText().toString().trim();

    }




    public static String text(EditText V) {

        return V.getText().toString().trim();

    }



}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class test {

    public static void main(String[] args) throws ParseException {
        String first = "   start = 022 &  end = 512";
        System.out.println(first.trim());
        int[] n = null;

        String[] data = first.split("&", -2);
        String f  = data[0].replaceAll("[^0-9]", "");
        System.out.println("first num " + f);
        String s  = data[1].replaceAll("[^0-9]", "");
        System.out.println("second nums  " + s);
        System.out.println(isNumeric(f));
        System.out.println(isNumeric(f));
        

    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

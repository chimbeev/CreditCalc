/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credcalc2;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 1
 */
public class DateTest {
    
    public static void main(String[] args)
       {
        // Setting to a different date 
        Calendar calndr1 = (Calendar)Calendar.getInstance(); 
        calndr1.set(Calendar.MONTH, 5); 
        calndr1.set(Calendar.YEAR, 2006); 
        calndr1.set(Calendar.DAY_OF_WEEK, 15); 
        Date dt = calndr1.getTime(); 
        System.out.println("The modified Date:" + dt); 
       }
 
}

package credcalc;

import java.util.*;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




public class Credit {
    int id_client;
    int size;
    int percent;
    int first_pay_size;
    char typeOfPayment;
    int term;
    String termOfFirstPayment;

    
    public static void main(String[] args) throws IOException, ParseException
       {
           File input = new File("input3.csv");
           Scanner sc = new Scanner(input);
           String StrIn = sc.nextLine();
           StrIn = sc.nextLine(); //считываем целиком строку из файла
           sc.close();
           Credit NewCred = new Credit(); 
           String [] StrInArray = StrIn.split(";");
           NewCred.id_client = Integer.parseInt(StrInArray[0]);
           NewCred.size = Integer.parseInt(StrInArray[1]);
           NewCred.percent = Integer.parseInt(StrInArray[2]);
           NewCred.first_pay_size = Integer.parseInt(StrInArray[3]);
           NewCred.typeOfPayment= StrInArray[4].charAt(0);
           NewCred.term = Integer.parseInt(StrInArray[5]);
           NewCred.termOfFirstPayment = StrInArray[6];
                   
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
           LocalDate date = LocalDate.parse(NewCred.termOfFirstPayment, formatter);
           System.out.println(date); // 2010-01-02
    

           prn(NewCred.percent);
           
       }    

       public static void prn(int nc) //Для вывода на экран
       {
            System.out.println(nc);

       }
       public static void prn(Date nc) //Для вывода на экран
       {
            System.out.println(nc);

       }
    }

//обьявим класс кредит
//Кредитный калькулятор контроллер. Отвечает за прием данных, анализ, передачу другим
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Credit
    {
       int id_client;
       int size;
       int percent;
       int first_pay_size;
       int typeOfPayment;
       int term;
       Date termOfFirstPayment;
      public static void main (String[] args) throws IOException
       {
           File input = new File("input.csv");
           Scanner sc = new Scanner(input);
           String StrIn = sc.nextLine(); //считаем всю строку с данным кредита
           sc.close();
           Credit NewCred = new Credit(); 
           String [] StrInArray = StrIn.split(";");
           NewCred.id_client = Integer.parseInt(StrInArray[0]);
           NewCred.size = Integer.parseInt(StrInArray[1]);
           NewCred.percent = Integer.parseInt(StrInArray[2]);
           NewCred.first_pay_size = Integer.parseInt(StrInArray[3]);
           NewCred.typeOfPayment= Integer.parseInt(StrInArray[4]);
           NewCred.term = Integer.parseInt(StrInArray[5]);
           NewCred.termOfFirstPayment = Integer.parseInt(StrInArray[6]);
       }    

    }

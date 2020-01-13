package credcalc;

import java.util.*;
import java.util.Date;
import java.util.stream.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;

//----------------------------View Begin --------------------------------------
public class View //Представление получает данные из файла и отправляет в Model, затем забирает из Model и выводит в файл и на экран
     {    
          public static Stream<String> ReadCrd() throws IOException//считывает из файла входные данные по кредитам
          
          {
                File input = new File("input3.csv");
                Scanner sc = new Scanner(input);
                String StrIn = sc.nextLine();
                StrIn = sc.nextLine(); //считываем целиком строку из файла
                sc.close();
                Model.Credit NewCred = new Model.Credit(); 
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
           
                      
              
               return lineStream;
               
          }
     }
      //----------------------------View End----------------------------------------
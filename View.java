
package creditcalc;

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
          public Credit ReadCrd() //считывает из файла входные данные по кредитам
          {
               
          
               try ( Stream<String> lineStream = Files.newBufferedReader(Paths.get("input3.csv")).lines())
                    {
                         lineStream
                              .forEach(e -> 
                              {
                                   Credit NewCred = new Credit();     
                                   String [] StrInArray = e.split(";"); //разделяем строку по символам ; и записываем в массив
                                   NewCred.id_client = Integer.parseInt(StrInArray[0]);
                                   NewCred.size = Integer.parseInt(StrInArray[1]); // размер кредита
                                   NewCred.percent = Integer.parseInt(StrInArray[2]); //процент по кредиту
                                   NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //размер первоначального взноса
                                   NewCred.typeOfPayment= StrInArray[4].charAt(0); //вид платежа - аннуитетный или дифференцированный
                                   NewCred.term = Integer.parseInt(StrInArray[5]); //срок кредита в месяцах
                                   NewCred.termOfFirstPayment = StrInArray[6]; // дата первого платежа
                                   return NewCred;//передаем в Model 
                              });     
                    } catch (IOException ignored) {};
          }
      
     }

class Credit //класс кредит
     {
         int id_client;
         int size;
         int percent;
         int first_pay_size;
         char typeOfPayment;
         int term;
         String termOfFirstPayment;
     }
      //----------------------------View End----------------------------------------
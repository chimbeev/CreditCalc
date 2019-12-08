
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
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
     {    
          public Credit ReadCrd() //��������� �� ����� ������� ������ �� ��������
          {
               
          
               try ( Stream<String> lineStream = Files.newBufferedReader(Paths.get("input3.csv")).lines())
                    {
                         lineStream
                              .forEach(e -> 
                              {
                                   Credit NewCred = new Credit();     
                                   String [] StrInArray = e.split(";"); //��������� ������ �� �������� ; � ���������� � ������
                                   NewCred.id_client = Integer.parseInt(StrInArray[0]);
                                   NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
                                   NewCred.percent = Integer.parseInt(StrInArray[2]); //������� �� �������
                                   NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
                                   NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
                                   NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
                                   NewCred.termOfFirstPayment = StrInArray[6]; // ���� ������� �������
                                   return NewCred;//�������� � Model 
                              });     
                    } catch (IOException ignored) {};
          }
      
     }

class Credit //����� ������
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
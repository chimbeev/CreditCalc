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
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
     {    
          public static Stream<String> ReadCrd() throws IOException//��������� �� ����� ������� ������ �� ��������
          
          {
               
                    Stream<String> lineStream = Files.newBufferedReader(Paths.get("input3.csv")).lines();
              
               return lineStream;
               
          }
     }
      //----------------------------View End----------------------------------------
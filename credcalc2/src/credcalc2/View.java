package credcalc2;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
     {    
          public static Stream<String> ReadCrd() throws IOException//��������� �� ����� ������� ������ �� ��������
          
          {     Stream<String> stream = Files.lines(Paths.get("input3.csv"));//�������� ������ �� �����  
                
               return stream;
               
          }
          
          public static Stream<String> PrintToFile() throws IOException//���������� � ���� ����������� ������ �� ��������
          
          {     Stream<String> stream = Files.lines(Paths.get("input3.csv"));//�������� ������ �� �����  
                //Create a list of lines.
                List<String> lines = Arrays.asList("The first line", "The second line");     
                //Construct a path for file that will be created.
                Path file = Paths.get("D:\\demo\\java7.txt");              
                //Perform write operation.
                Files.write(file, lines, Charset.forName("UTF-8"));  
               return stream;
               
          }
     }
      //----------------------------View End----------------------------------------
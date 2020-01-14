package credcalc2;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //Представление получает данные из файла и отправляет в Model, затем забирает из Model и выводит в файл и на экран
     {    
          public static Stream<String> ReadCrd() throws IOException//считывает из файла входные данные по кредитам
          
          {     Stream<String> stream = Files.lines(Paths.get("input3.csv"));//получаем данные из файла  
                
               return stream;
               
          }
          
          public static Stream<String> PrintToFile() throws IOException//Записывает в файл расчитанные данные по кредитам
          
          {     Stream<String> stream = Files.lines(Paths.get("input3.csv"));//получаем данные из файла  
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
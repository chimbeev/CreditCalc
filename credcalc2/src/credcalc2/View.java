package credcalc2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
     {    
          public static Stream<String> ReadCrd() throws IOException//��������� �� ����� ������� ������ �� ��������
          
          {   /*---------------
              Charset charset = Charset.forName("Cp1251"); 
              Stream<String> stream = Files.lines(Paths.get("input4.csv"),charset);//�������� ������ �� �����
                //tream.forEach(System.out::println);
               return stream;
              ---------------*/
              
              try (FileInputStream fi = new FileInputStream(new File("input4.csv")))
              {     ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fi.read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }
                    //String words =  result.toString(StandardCharsets.Cp1251.name());
                    String words =  result.toString();
                    List<String> wordList = Arrays.asList(words); 
                    return wordList.stream();
              }
	         //�������� https://javarevisited.blogspot.com/2014/04/how-to-convert-byte-array-to-inputstream-outputstream-java-example.html
          }
          
          public static void PrintToFile(InputStream inputStream) throws IOException//���������� � ���� ����������� ������ �� ��������
          
          {     //Stream<String> stream = Files.lines(Paths.get("input3.csv"));//�������� ������ �� �����  
                String FILE_TO = "output3.csv";
                File file = new File(FILE_TO);
                try (FileOutputStream outputStream = new FileOutputStream(file)) 
                {
                    int read;
                    byte[] bytes = new byte[1024];
                    while ((read = inputStream.read(bytes)) != -1) 
                    {
                        outputStream.write(bytes, 0, read);
                    }

			
                }
                    
          }

    private static class StandardCharsets {

        private static Charset UTF_8;
        private static Charset Cp1251;

        public StandardCharsets() {
        }
    }

    
     }
      //----------------------------View End----------------------------------------
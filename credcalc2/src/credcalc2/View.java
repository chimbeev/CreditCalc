package credcalc2;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //Представление получает данные из файла и отправляет в Model, затем забирает из Model и выводит в файл и на экран
     {    
           public static List<String> ReadCrd() throws IOException//считывает из файла входные данные по кредитам
          
          {   
                FileInputStream fis = null;
                BufferedReader reader = null;
                List<String> wordList = new ArrayList<String>();
                try {
                        fis = new FileInputStream("input4.csv");
                        reader = new BufferedReader(new InputStreamReader(fis));
                        String line = reader.readLine();
                         
                        while(line != null){
                            //System.out.println(line);
                            line = reader.readLine();
                            wordList.add(line);
                            
                        }          
                    } catch (IOException e) {e.printStackTrace();                 
                    } finally {
                        try {
                            reader.close();
                            fis.close();
                            
                        } catch (IOException e) { e.printStackTrace();}
                            }
                    return wordList;
          }
          
          public static List<String> ReadCrd2() throws IOException//считывает из файла входные данные по кредитам быстрее
          
          {     
                byte [] buffer = new byte[8000];
                FileInputStream fis = null;
                fis = new FileInputStream("input3.csv");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                String s = null;
                while (bis.read(buffer,0,buffer.length) > 0)
                {
                    baos.write(buffer);
                    s = baos.toString("Cp1251"); //получили строку со всеми кредитами
                }
                String [] st; //поделили строку по кредитам
                st = s.split("\n");
                List<String> wordList = Arrays.asList(st);
                //System.out.println(wordList.size());
                //System.out.println(wordList.get(90));
                return wordList;
          }

          public static void PrintToFile(List<String> stream) throws IOException//Записывает в файл расчитанные данные по кредитам
          
          {     String FILE_TO = "output3.csv";
                File file = new File(FILE_TO);
                try (FileOutputStream outputStream = new FileOutputStream(file)) 
                {   String str = stream.toString();
                    byte[] strToBytes = str.getBytes();
                    outputStream.write(strToBytes);
                    outputStream.close();
                }
           }
          public static void ShowStream(Stream<String> stream) //показывает на экране стрим
          {
              stream.forEach(s -> System.out.println(s));
          }
          
          public static void ShowList(List<String> list) //показывает на экране стрим
          {
              for (String e : list) {System.out.println(e);}  
          }

    public static void WriteJson(List<String> stream) throws IOException 
    { //Записывает в формате json файл расчитанные данные по кредитам 
        String json = new Gson().toJson(stream);
        String FILE_TO = "output3.json";
        File file = new File(FILE_TO);
        try (FileOutputStream outputStream = new FileOutputStream(file)) 
        {   byte[] strToBytes = json.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
     }
      //----------------------------View End----------------------------------------
package credcalc2;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
     {    
           public static List<String> ReadCrd() throws IOException//��������� �� ����� ������� ������ �� ��������
          
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

          public static void PrintToFile(List<String> stream) throws IOException//���������� � ���� ����������� ������ �� ��������
          
          {     String FILE_TO = "output3.csv";
                File file = new File(FILE_TO);
                try (FileOutputStream outputStream = new FileOutputStream(file)) 
                {   String str = stream.toString();
                    byte[] strToBytes = str.getBytes();
                    outputStream.write(strToBytes);
                    outputStream.close();
                }
           }
          public static void ShowStream(Stream<String> stream) //���������� �� ������ �����
          {
              stream.forEach(s -> System.out.println(s));
          }

    public static void WriteJson(List<String> stream) throws IOException 
    { //���������� � ������� json ���� ����������� ������ �� �������� 
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
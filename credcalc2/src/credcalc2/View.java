package credcalc2;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
{    
                 
        public static List<String> ReadCrd2() throws IOException//��������� �� ����� ������� ������ �� �������� �������

        {     
              byte [] buffer = new byte[800000];
              FileInputStream fis = null;
              fis = new FileInputStream("input3.csv");
              BufferedInputStream bis = new BufferedInputStream(fis);
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              String s = null; 
              while (bis.read(buffer,0,buffer.length) > 0)
              {
                  baos.write(buffer);
                  s = baos.toString("Cp1251"); //�������� ������ �� ����� ���������

              }
              String [] st; //�������� ������ �� ��������
              st = s.split("\n");
              System.out.println("������ �� �������� �� ����� ���������.");
              List<String> wordList = Arrays.asList(st);

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

        public static void ShowList(List<String> list) //���������� �� ������ �����
        {
            for (String e : list) {System.out.println(e);}  
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
    
        public static void AddWriteJson(List<String> stream) throws IOException 
        { //���������� � ������� json ���� ����������� ������ �� �������� 
            String json = new Gson().toJson(stream);
            String file_to = "output3.json";
            byte[] strToBytes = json.getBytes();
            try (FileOutputStream outputStream = new FileOutputStream(file_to,true)) 
            {   
                outputStream.write(strToBytes);
                outputStream.close();

            } catch (IOException e) { e.printStackTrace(); }
        }
    
        public static void ClearJson() throws IOException
        { //������� � ������� ���� 
            String file_to = "output3.json";
            ;
            try (FileOutputStream outputStream = new FileOutputStream(file_to, false))
            {
                outputStream.write("".getBytes());
                outputStream.close();
            } catch (IOException e) { e.printStackTrace();}

        }
}
      //----------------------------View End----------------------------------------
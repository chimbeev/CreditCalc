package CredCalcTomcatServer;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import static java.net.Proxy.Type.HTTP;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //������������� �������� ������ �� ����� � ���������� � Model, ����� �������� �� Model � ������� � ���� � �� �����
{    
        public static void GetCrdFromSrv(String crd) throws IOException //���������� ������ �� ������ � ����������� ������� � �������� ������ ��������
        {
            System.out.println(crd);
            //System.out.println(URLEncoder.encode(crd, "UTF-8"));
                      
            String url = "http://localhost:8080/CredCalcTomcatServer/NewServlet/?Param1=" + URLEncoder.encode(crd, java.nio.charset.StandardCharsets.UTF_8.toString());
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response); //������� ����� ����������
        }
    
    
        public static List<String> ReadCrd() throws IOException//��������� �� ����� ������� ������ �� ��������

       {   
             FileInputStream fis = null;
             BufferedReader reader = null;
             List<String> wordList = new ArrayList<String>();
             try {
                     fis = new FileInputStream("input3.csv");
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
          
        public static List<String> ReadCrd2() throws IOException//��������� �� ����� ������� ������ �� �������� �������

        {     
              byte [] buffer = new byte[800000];
              FileInputStream fis = null;
              fis = new FileInputStream("input4.csv");
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
              System.out.println("������ �� ����� ���������");
              List<String> wordList = Arrays.asList(st);
              System.out.println("List OK");
              //System.out.println(wordList.size());
              //System.out.println(wordList.get(90));
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

        public static void ShowList(List<String> list) //���������� �� ������ ����
        {
            for (String e : list) {System.out.println(e);}  
            //System.out.println("Show list completed");
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
        
        public static void WriteJson(List<String> stream, String fileName) throws IOException 
        { //���������� � ������� json ���� ����������� ������ �� �������� � ��������� ���� json
            String json = new Gson().toJson(stream);
            String FILE_TO = fileName + ".json";
            File file = new File(FILE_TO);
            try (FileOutputStream outputStream = new FileOutputStream(file)) 
            {   byte[] strToBytes = json.getBytes();
                outputStream.write(strToBytes);
                outputStream.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
}
      //----------------------------View End----------------------------------------
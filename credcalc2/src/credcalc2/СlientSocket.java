package credcalc2;

import java.io.*;
import java.net.*;

class ClientSocket
{
    // ������ �������� - ��� �����, ����������� HTTP ������
    // ��������������, ��� ������ �� ����� ������ 64 ��������
    // ������ - ��� �����, ���� ����� ���� ����� �������
    //http://www.javaportal.ru/java/articles/java_http_web/article04.html
    public static void main(String args[])
    {
        try
        {
            byte buf[] = new byte[64*1024];
            int r;
            // ������ ���� � �������� � ���������� header
            FileInputStream fis = new FileInputStream("request.txt");
            r = fis.read(buf);
            String header = new String(buf, 0, r);
            fis.close();

            int port = 8080;
            String host = "127.0.0.1";
    
            // ��������� ����� �� �������
            Socket s = new Socket(host, port);

            // ����� ���� HTTP request
            s.getOutputStream().write(header.getBytes());

            // �������� ����� ������ �� �������
            InputStream is = s.getInputStream();

            // ��������� ��� ������ ����, ���� ����� ���� ���
            FileOutputStream fos = new FileOutputStream("reply.txt");

            // ������ ����� �������, ������������ ������ ��� � �������� ����
            r = 1;
            while(r > 0)
            {
                r = is.read(buf);
                if(r > 0)
                    fos.write(buf, 0, r);
            }

            // ��������� ����
            fos.close();
            s.close();
        }
        catch(Exception e)
        {e.printStackTrace();} // ����� ����������
    }

}

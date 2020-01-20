package credcalc2;

import java.io.*;
import java.net.*;

class ClientSocket
{
    // ������ �������� - ��� �����, ����������� HTTP ������
    // ��������������, ��� ������ �� ����� ������ 64 ��������
    // ������ - ��� �����, ���� ����� ���� ����� �������
    public static void main(String args[])
    {
        try
        {
            byte buf[] = new byte[64*1024];
            int r;
            String header = "GET http://127.0.0.1 HTTP/1.1\n" + "Host: 127.0.0.1\n" + "User-Agent: HTTPClient\n" + "\n" + "\n";
            String host = "127.0.0.1";

            int port = 8080;
    
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

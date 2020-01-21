/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credcalc2;

import java.io.*;
import java.net.*;

class Test1
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

            // ������ ���� � �������� � ���������� header
            FileInputStream fis = new FileInputStream("request.txt");
            r = fis.read(buf);
            String header = new String(buf, 0, r);
            fis.close();

            // �������� �� ������ ������� ����, ���� � URL �������
            // ��� ��������� ������������ �������������������� �-�� extract
            String host = extract(header, "Host:", "\n");

            // ���� �� ������ �������� Host - ������
            if(host == null)
            {
                System.out.println("invalid request:\n"+header);
                return;
            }

            // ������� ���� �������, �� ��������� �� - 80
            int port = host.indexOf(":",0);
            if(port < 0) port = 8080;
            else
            {
                port = Integer.parseInt(host.substring(port+1));
                host = host.substring(0, port);
            }

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

    // "��������" �� ������ str �����, ����������� ����� �������� start � end
    // ���� ������ end ���, �� ������ ������ ����� start
    // ���� ����� �� ������, ������������ null
    // ��� ������ ������ ������ �� "\n\n" ��� "\r\n\r\n", ���� ������� ������������
    protected static String extract(String str, String start, String end)
    {
        int s = str.indexOf("\n\n", 0), e;
        if(s < 0) s = str.indexOf("\r\n\r\n", 0);
        if(s > 0) str = str.substring(0, s);
        s = str.indexOf(start, 0)+start.length();
        if(s < start.length()) return null;
        e = str.indexOf(end, s);
        if(e < 0) e = str.length();
        return (str.substring(s, e)).trim();
    }
}
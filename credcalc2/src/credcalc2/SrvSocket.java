package credcalc2;

import java.net.*;
import java.io.*;

public class SrvSocket implements Runnable
{

    public static void SrvSocket () {
            int port = 8080;
            SrvSocket st = new SrvSocket();
            Thread th = new Thread(st);
            th.start(); // ��������� ������ � ��������� ������
            try
            {
                ServerSocket ss = new ServerSocket(port);
                
                System.out.println("������ �������! ������ ������ ...");
                Socket socket = ss.accept();
                System.out.println("���� �������!");
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
        }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


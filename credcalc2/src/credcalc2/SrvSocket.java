package credcalc2;

import java.net.*;
import java.io.*;
import com.sun.net.httpserver.HttpServer;

public class SrvSocket implements Runnable
{
    @Override
    public void run() 
    {
        try
            {   int port = 8080;
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
}


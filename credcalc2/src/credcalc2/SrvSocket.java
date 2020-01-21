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
                System.out.println("Сервер запущен! Ожидаю обмена ...");
                Socket socket = ss.accept();
                System.out.println("Есть контакт!");
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
    }
}


package credcalc2;

import java.net.*;
import java.io.*;

public class �lientSocket 
{

   
    public static void main(String[] ar)
    {
        int port = 8080;
        try
        {
            ServerSocket ss = new ServerSocket(port);
            Socket socket = ss.accept();
            System.out.println("���� �������!");
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
    }
}

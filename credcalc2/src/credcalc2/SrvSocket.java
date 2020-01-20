package credcalc2;
//http://www.javaportal.ru/java/articles/java_http_web/article04.html
import java.net.*;
import java.io.*;

public class SrvSocket 
{

    public SrvSocket(int port) {
    }
    public static void main(String[] ar)
    {
        int port = 8080;
        try
        {
            ServerSocket ss = new ServerSocket(port);
            Socket socket = ss.accept();
            System.out.println("Есть контакт!");
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
    }
}

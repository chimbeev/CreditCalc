package credcalc2;

import java.io.*;
import java.net.*;

class ClientSocket
{
    // первый аргумент - имя файла, содержащего HTTP запрос
    // предполагается, что запрос не будет больше 64 килобайт
    // второй - имя файла, куда будет слит ответ сервера
    public static void main(String args[])
    {
        try
        {
            byte buf[] = new byte[64*1024];
            int r;
            String header = "GET http://127.0.0.1 HTTP/1.1\n" + "Host: 127.0.0.1\n" + "User-Agent: HTTPClient\n" + "\n" + "\n";
            String host = "127.0.0.1";

            int port = 8080;
    
            // открываем сокет до сервера
            Socket s = new Socket(host, port);

            // пишем туда HTTP request
            s.getOutputStream().write(header.getBytes());

            // получаем поток данных от сервера
            InputStream is = s.getInputStream();

            // Открываем для записи файл, куда будет слит лог
            FileOutputStream fos = new FileOutputStream("reply.txt");

            // читаем ответ сервера, одновременно сливая его в открытый файл
            r = 1;
            while(r > 0)
            {
                r = is.read(buf);
                if(r > 0)
                    fos.write(buf, 0, r);
            }

            // закрываем файл
            fos.close();
            s.close();
        }
        catch(Exception e)
        {e.printStackTrace();} // вывод исключений
    }

}

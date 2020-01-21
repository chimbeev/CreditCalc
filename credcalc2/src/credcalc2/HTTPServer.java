/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credcalc2;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author тест
 */
public class HTTPServer implements Runnable
{
    @Override
    public void run() 
    {
        try
            {   HttpServer server = HttpServer.create();
                server.bind(new InetSocketAddress(8765), 0);
                server.createContext("/", new SomeHandler());
                server.start();
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
    }
}
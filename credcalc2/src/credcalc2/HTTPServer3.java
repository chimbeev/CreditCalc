/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credcalc2;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 1
 */
public class HTTPServer3 
{
  public static void main(String[] args) throws IOException {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      HttpContext context = server.createContext("/");
      context.setHandler(HTTPServer3::handleRequest);
      server.start();
 
  }

  private static void handleRequest(HttpExchange exchange) throws IOException {
      String response = "Ответ сервера:";
      exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
      InputStream is=exchange.getRequestBody();
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
      String respo = exchange.getRequestURI().getQuery();
      Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery()); 
      System.out.println("поступили данные по кредиту " + respo);
      System.out.println("ид клиента " + params.get("param1"));
      System.out.println("сумма кредита " + params.get("param2"));
      
  }
  //https://stackoverflow.com/questions/11640025/how-to-obtain-the-query-string-in-a-get-with-java-httpserver-httpexchange
  
  private static void handleRequest2(HttpExchange exchange) throws IOException {
      String response = "Hi there!";
      exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
  }
  public static Map<String, String> queryToMap(String query) 
      {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) 
        {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
      }
}   


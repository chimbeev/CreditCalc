/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credcalc2;

        import java.io.BufferedInputStream;
	import java.io.BufferedOutputStream;
	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.net.URL;
	import java.net.URLConnection;
	import java.nio.charset.MalformedInputException;

	public class ClientSocket3 
        {
	    public static void main(String[] args) 
            {
	        URL url = null;
	        URLConnection con = null;
	        int i;
	        try 
                {
	            url = new URL("https://localhost:8080/request.txt");
	            con = url.openConnection();
	            File file = new File("input10.txt");
	            BufferedInputStream bis = new BufferedInputStream(con.getInputStream()); //входящий поток от вебсервера
	            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file.getName()));
                    while ((i = bis.read()) != -1) 
                    {
	                bos.write(i);
	            }
	            bos.flush();
                    bis.close();
	        } catch (MalformedInputException malformedInputException) {malformedInputException.printStackTrace();
	        } catch (IOException ioException) {ioException.printStackTrace();
	        }
            }
	}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credcalc2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 *
 * @author 1
 */
public class ClientHTTP 
{
    public static void main(String[] args) throws IOException 
    {
        URL connection = new  URL("http://127.0.0.1:8080/computeSmth");
        HttpURLConnection urlconn;
        urlconn = (HttpURLConnection) connection.openConnection();
        urlconn.setDoOutput(true);
        urlconn.setDoInput(true);
        UUID uuid = UUID.randomUUID();
        String boundary = uuid.toString();
        urlconn.setRequestMethod("POST");
        urlconn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);

        OutputStream os = urlconn.getOutputStream();
        byte[] data=new byte[156];
        os.write(("--" + boundary + "\r\n").getBytes());
        os.write(("Content-Disposition: form-data; name=\"image\"); filename=\"ewe.jpg\"\r\n").getBytes());
        os.write(("\r\n").getBytes());
        os.write(data);
        os.write(("\r\n--" + boundary + "--\r\n").getBytes());
        os.flush();
        os.close();

        data=null;
        int responseCode=urlconn.getResponseCode();
        if (responseCode!=200)
        {
            System.out.println("Error");
        }
        InputStream instream = urlconn.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = instream.read(buffer)) != -1)
        {
            baos.write(buffer,0,bytesRead);
        }
        System.out.println(baos.toString("UTF-8"));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import CredCalcTomcatServer.Model;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


/**
 *
 * @author ����
 */
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //read input
        String sl = request.getQueryString();
        //response.setContentType("text/html");
        //response.setCharacterEncoding("Cp1251");
        //PrintWriter out = response.getWriter();
        
        //out.println(" ������ - ������� ������: " + sl);
       
        List<String> strCredOutlist = new ArrayList<>();
        List<String> strCredlist = new ArrayList<>();
        System.out.println(sl.substring(7));
        strCredOutlist.add(sl.substring(7));
        String jsonStr = "";
        strCredlist = Model.getCrd(strCredOutlist);//�������� ��� ������� ��������
        //out.println(" ������ - ���������� �������:");
        for(int i=0;i<strCredlist.size();i++)
        {
            //out.println(strCredlist.get(i));
            jsonStr = new Gson().toJson(strCredlist.get(i));
        } 
  
        JSONObject json = new JSONObject();
        json.put("credit",jsonStr);
        
        String jsonInputString = json.toString();
        
        try(OutputStream os = response.getOutputStream()) 
        {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);           
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

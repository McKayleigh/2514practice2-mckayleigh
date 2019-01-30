/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fssco
 */
public class Controller extends HttpServlet {

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
            throws ServletException, IOException 
    {

        String url = "/display.jsp";

        String action = request.getParameter("action");
        if (action == null) 
        {
            action = "first";
        }
       
        LinkedHashMap<Integer, Person> linkMap = new LinkedHashMap();
        
        
        linkMap.put(731, new Person("Pris", "", "Stratton", 731,
                LocalDate.of(2016, Month.FEBRUARY, 14), LocalDate.of(2016, Month.FEBRUARY, 10)));
        linkMap.put(734, new Person("Roy", "", "Batty", 734,
                LocalDate.of(2016, Month.JANUARY, 8), LocalDate.of(2016, Month.JANUARY, 9)));
        linkMap.put(735, new Person("Ben", "", "Benson", 735,
                LocalDate.of(2016, Month.JANUARY, 10), LocalDate.of(2018, Month.JANUARY, 20)));
        
        request.setAttribute("linkMap", linkMap);
        
        

        if (action.equals("first")) 
        {

        }

        ServletContext sc = getServletContext();

        sc.getRequestDispatcher(url)
                .forward(request, response);

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

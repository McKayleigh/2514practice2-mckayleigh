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
import javax.servlet.http.HttpSession;

/**
 *
 * @author fssco
 */
public class Controller extends HttpServlet 
{
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
        HttpSession session = request.getSession();
        
        LinkedHashMap<Integer, Person> linkMap = (LinkedHashMap) session.getAttribute("linkMap");
        //LinkedHashMap<Integer, Person> linkMap = new LinkedHashMap();
        
        if(linkMap == null)
        {
            linkMap = new LinkedHashMap();
            
            linkMap.put(731, new Person("Pris", "", "Stratton", 731,
                LocalDate.of(2016, Month.FEBRUARY, 14), LocalDate.of(2016, Month.FEBRUARY, 10)));
            linkMap.put(734, new Person("Roy", "", "Batty", 734,
                LocalDate.of(2016, Month.JANUARY, 8), LocalDate.of(2016, Month.JANUARY, 9)));
            linkMap.put(735, new Person("Ben", "", "Benson", 735,
                LocalDate.of(2016, Month.JANUARY, 10), LocalDate.of(2018, Month.JANUARY, 20)));
        }
       
        session.setAttribute("linkMap", linkMap);
        
        request.setAttribute("linkMap", linkMap);
        
        String error = "";

        if(action.equals("first"))
        {
            url = "/display.jsp";
        }
        else if(action.equals("deleteEmployee"))
        {
            int personIndex = 0;
            try
            {
                personIndex = Integer.parseInt(request.getParameter("personIndex"));
            }
            catch(Exception e)
            {
                error += "Not a vaild ID. ";
            }
            
            if(linkMap.containsKey(personIndex))
            {
                linkMap.remove(personIndex);
            }
            else
            {
                error += "There is no Employee with that ID. ";
            }
        }
        else if (action.equals("resetEmployees")) 
        {
            session.invalidate();
            //I found the next two lines here: https://stackoverflow.com/questions/23618089/how-to-reset-session-within-jsp
            // when I tried to just set the url back to "/display,jsp", it would take
            //two clicks of the reset button to kill the session and redirect to the display page
            response.sendRedirect("");
            return;
        }
         
        request.setAttribute("error", error);
        
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Person;
import business.Validation;
import java.io.IOException;
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
        else if(action.equals("addEmployee"))
        {
            error = "";
            int employeeID = 0;
            LocalDate birthDate = null;
            LocalDate hireDate = null;
            String invalidDate = null;
            
            //get values from form
            String fName = request.getParameter("fName");
            String mName = request.getParameter("mName");
            String lName = request.getParameter("lName");
            String empIDIn = request.getParameter("empID");
            String birthdayDate = request.getParameter("DOB");
            String hiredDate = request.getParameter("hireDate");
            
            //validate the form
            //fName
            if(fName == null || fName.equals("")) 
            {
                error += "First Name must not be blank. <br>";
            }
            
            //mName
            //if(mName == null || mName.equals("")) 
            //{
                //error += "Middle Name must not be blank. ";
            //}
            
            //lName
            if(lName == null || lName.equals("")) 
            {
                error += "Last Name must not be blank. <br>";
            }
            
            //employeeID
            try
            {
                employeeID = Integer.parseInt(empIDIn);
                
                if(employeeID <= 0)
                {
                    error += "Employee ID must be greater than 0. <br>";
                }
                else
                {
                    if(linkMap.containsKey(employeeID))
                    {
                        error += "There is already an Employee with that ID. <br>";
                    }
                }
            }
            catch(Exception e)
            {
                error += "Employee ID must be a number. <br>";
            }
            
            //birth date
            try
            {
                birthDate = LocalDate.parse(birthdayDate);
            }
            catch(Exception e)
            {
                error += "Birth date must be a valid date. <br>";
            }
            
            //birth date
            try
            {
                hireDate = LocalDate.parse(hiredDate);
            }
            catch(Exception e)
            {
                error += "Hire date must be a valid date. <br>";
            }
          
            if(birthDate != null && hireDate != null)
            {
                if(hireDate.isBefore(birthDate))
                {
                    invalidDate = "not valid";
                    error += "Hire date must not be before birth date. <br>";
                    request.setAttribute("invalidDate", invalidDate);
                }
            }
            
            
            //store data in Student object
            Person person = new Person(fName, mName, lName, employeeID, 
                        birthDate, hireDate);
            
            //if vaild
            if (error.equals("")) 
            {
                linkMap.put(employeeID, new Person(fName, mName, lName, employeeID, 
                        birthDate, hireDate));
                url = "/display.jsp";
            }
            //if not valid
            else
            {
                url = "/display.jsp";
                request.setAttribute("person", person);
                request.setAttribute("error2", error);
            }
            
        }
        
        ServletContext sc = getServletContext();

        sc.getRequestDispatcher(url)
                .forward(request, response);
    }
    
    private String isValid(HttpServletRequest request, HttpServletResponse response)
    {
        String errorMsg = "";
        Validation v = new Validation();
        
        errorMsg += v.isPresent(request.getParameter("fName"), "First Name");
        //middle name can be blank
        errorMsg += v.isPresent(request.getParameter("lName"), "Last Name");
        errorMsg += v.isPresent(request.getParameter("fName"), "First Name");
        return errorMsg;
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

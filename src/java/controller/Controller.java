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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
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
        
        //for edit
        int personInd = 0;
        Person person1 = new Person();
        
        //personInd = Integer.parseInt(request.getParameter("empIDE"));
        

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
            Validation v = new Validation();
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
            
            //fName
            error += v.isPresent(fName, "First Name");
            
            //lName
            error += v.isPresent(lName, "Last Name");
            
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
            else
            {
                url = "/display.jsp";
                request.setAttribute("person", person);
                request.setAttribute("error2", error);
            }
        }
        else if(action.equals("editEmployee"))
        {
            try
            {
                personInd = Integer.parseInt(request.getParameter("personIndex"));
            }
            catch(Exception e)
            {
                error += "Not a vaild ID. ";
            }
            
            if(linkMap.containsKey(personInd))
            {
                person1 = linkMap.get(personInd);
                request.setAttribute("emp", person1);
                request.setAttribute("id", personInd);
               
//                if(action.equals("editEmployee"))
//                {
//                    error = "";
//                    Validation v = new Validation();
//                    int employeeID = 0;
//                    LocalDate birthDate = null;
//                    LocalDate hireDate = null;
//                    String invalidDate = null;
//            
//                    //get values from form
//                    String fName = request.getParameter("fName");
//                    String mName = request.getParameter("mName");
//                    String lName = request.getParameter("lName");
//                    String empIDIn = request.getParameter("empID");
//                    String birthdayDate = request.getParameter("DOB");
//                    String hiredDate = request.getParameter("hireDate");
//            
//                    //fName
//                    error += v.isPresent(fName, "First Name");
//            
//                    //lName
//                    error += v.isPresent(lName, "Last Name");
//            
//                    //employeeID
//                    try
//                    {
//                        employeeID = Integer.parseInt(empIDIn);
//                
//                        if(employeeID <= 0)
//                        {
//                            error += "Employee ID must be greater than 0. <br>";
//                        }
//                        else
//                        {
//                            if(linkMap.containsKey(employeeID) && employeeID != personInd)
//                            {
//                                error += "*****. <br>";
//                            }
//                        }
//                    }
//                    catch(Exception e)
//                    {
//                        error += "Employee ID must be a number. <br>";
//                    }
//            
//                    //birth date
//                    try
//                    {
//                        birthDate = LocalDate.parse(birthdayDate);
//                    }
//                    catch(Exception e)
//                    {
//                        error += "Birth date must be a valid date. <br>";
//                    }
//            
//                    //birth date
//                    try
//                    {
//                        hireDate = LocalDate.parse(hiredDate);
//                    }
//                    catch(Exception e)
//                    {
//                        error += "Hire date must be a valid date. <br>";
//                    }
//          
//                    if(birthDate != null && hireDate != null)
//                    {
//                        if(hireDate.isBefore(birthDate))
//                        {
//                            invalidDate = "not valid";
//                            error += "Hire date must not be before birth date. <br>";
//                            request.setAttribute("invalidDate", invalidDate);
//                        }
//                    }
//        
//                    //store data in Student object
//                    Person emp = new Person(fName, mName, lName, employeeID, 
//                                birthDate, hireDate);
//                    
//                    //if vaild
//                    if (error.equals("")) 
//                    {
//                        linkMap.replace(personInd, person1, emp);
//                        url = "/display.jsp";
//                    }
//                    else
//                    {
//                        request.setAttribute("emp", emp);
//                        request.setAttribute("error3", error);
//                        url = "/display.jsp";
//                    }
//                }
            }
            else
            {
                error += "There is no Employee with that ID. ";
                request.setAttribute("error3", error);
            }
        }
        else if(action.equals("editEmp") && request.getParameter("empIDE") != null)
        {
            personInd = Integer.parseInt(request.getParameter("id"));
            error = "";
            Validation v = new Validation();
            int employeeID = 0;
            LocalDate birthDate = null;
            LocalDate hireDate = null;
            String invalidDate = null;
            
            //get values from form
            String fName = request.getParameter("fName");
            String mName = request.getParameter("mName");
            String lName = request.getParameter("lName");
            String empIDIn = request.getParameter("empIDE");
            String birthdayDate = request.getParameter("DOB");
            String hiredDate = request.getParameter("hireDate");
            
            //fName
            error += v.isPresent(fName, "First Name");
            
            //lName
            error += v.isPresent(lName, "Last Name");
            
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
                    if(linkMap.containsKey(employeeID) && employeeID != personInd)
                    {
                        error += "There is already an Employee with that ID." + personInd + "<br>";
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
                    request.setAttribute("invalidDates", invalidDate);
                }
            }
        
            //store data in Student object
            Person emp = new Person(fName, mName, lName, employeeID, 
                            birthDate, hireDate);
                    
            //if vaild
            if (error.equals("")) 
            {
                linkMap.remove(personInd);
                linkMap.put(employeeID, emp);
                url = "/display.jsp";
            }
            else
            {
                request.setAttribute("emp", emp);
                request.setAttribute("error3", error);
                request.setAttribute("id", personInd);
                url = "/display.jsp";
            }
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

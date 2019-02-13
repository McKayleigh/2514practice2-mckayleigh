<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="business.Person"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Employees</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h1>Current Employees</h1>
        <p>${error}</p>
        <br>
        <table>
            <tr>
                <th>Employee ID</th>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>Last Name</th>
                <th>Birth Date</th>
                <th>Hire Date</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>

            <c:forEach var="item" items="${linkMap}" varStatus="status">

                <tr>
                    <td>${item.value.employeeID}</td>
                    <td>${item.value.firstName}</td>
                    <td>${item.value.middleName}</td>
                    <td>${item.value.lastName}</td>
                    <td>${item.value.birthDate}</td>
                    <td>${item.value.hireDate}</td>
                    <td>
                        <form action="" method="get">
                            <input type="hidden" name="action" value="deleteEmployee">
                            <input type="submit" value="Delete Employee">
                            <input type="hidden" name="personIndex" 
                                   value="<c:out value="${item.key}"/>">
                        </form>
                    </td>
                    <td>
                        <form action="" method="get">
                            <input type="hidden" name="action" value="editEmployee">
                            <input type="submit" value="Edit Employee">
                            <input type="hidden" name="personIndex" 
                                   value="<c:out value="${item.key}"/>">
                        </form>
                    </td>
                </tr>
                <%--${status.count}.--%>
            </c:forEach>  
        </table>
        <br><br>
        <h1>Add Employee</h1>
        <p>${error2}</p>
        
        <form action="Controller" method="get">
            <label>First Name</label>
            <input type="text" name="fName" value="${person.firstName}">
            <br><br>
            <label>Middle Name</label>
            <input type="text" name="mName" value="${person.middleName}">
            <br><br>
            <label>Last Name</label>
            <input type="text" name="lName" value="${person.lastName}">
            <br><br>
            <label>Employee ID</label>
            <input type="text" name="empID" value="${person.employeeID}">
            <br><br>
            
            <label<c:if test="${invalidDate != null}">
                    class="wrongDate" </c:if>>Birth Date</label>
            <input type="date" name="DOB" value="${person.birthDate}">
            <br><br>
            <label>Hire Date</label>
            <input type="date" name="hireDate" value="${person.hireDate}">
            <br><br>
            <input type="hidden" name="action" value="addEmployee">
            <input type="submit" value="Add Employee">  
        </form>
        <br><br>
        <h1>Edit Employee</h1>
        <p>${error3}</p>
            
        <form action="Controller" method="get">
            <label>First Name</label>
            <input type="text" name="fName" value="${emp.firstName}">
            <br><br>
            <label>Middle Name</label>
            <input type="text" name="mName" value="${emp.middleName}">
            <br><br>
            <label>Last Name</label>
            <input type="text" name="lName" value="${emp.lastName}">
            <br><br>
            <label>Employee ID</label>
            <input type="text" name="empIDE" value="${emp.employeeID}">
            <br><br>
            
            <label<c:if test="${invalidDates != null}">
                    class="wrongDate" </c:if>>Birth Date</label>
            <input type="date" name="DOB" value="${emp.birthDate}">
            <br><br>
            <label>Hire Date</label>
            <input type="date" name="hireDate" value="${emp.hireDate}">
            <br><br>
            <input type="hidden" name="id" value="${id}">
            <input type="hidden" name="action" value="editEmp">
            <input type="submit" value="Edit Employee">
        </form>
        
        <br><br>
        <form action="" method="get">
            <input type="hidden" name="action" value="resetEmployees">
            <input type="submit" value="Reset Employees">
        </form>

    </body>
</html>
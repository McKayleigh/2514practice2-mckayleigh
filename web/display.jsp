<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Practice 2</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <br>
        <table>
            <tr>
                <th>Employee ID</th>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>Last Name</th>
                <th>Birth Date</th>
                <th>Hire Date</th>
                <th>&nbsp;</th>
            </tr>

            <c:forEach var="item" items="${linkMap}" varStatus="status">

                <tr>
                    <td>${item.key}</td>
                    <td>${item.value.firstName}</td>
                    <td>${item.value.middleName}</td>
                    <td>${item.value.lastName}</td>
                    <td>${item.value.birthDate}</td>
                    <td>${item.value.hireDate}</td>
                    <td>
                        <form action="" method="get">
                            <input type="hidden" name="numEmployee" value="0">
                            <input type="submit" value="Delete Employee">
                            <input type="hidden" name="personIndex" 
                                   value="<c:out value="${item.key}"/>">

                        </form>
                    </td>
                </tr>
                <%--${status.count}.--%>
            </c:forEach>  
        </table>
    </body>
</html>
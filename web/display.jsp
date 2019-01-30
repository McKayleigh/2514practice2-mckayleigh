<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Practice 2</title>
    </head>
    <body>

        <table>

            <p>Just item.lastName causes an error because item is a LinkedHashMap$Entry not a Person.

                A LinkedHashMap$Entry has a key and a value, the stored Person is in the .value so 
                
                to get the last name item.value.lastName</p>


            <br>
            <c:forEach var="item" items="${linkMap}" varStatus="status">

                ${status.count}.
                
                ${item.key}

                ${item.lastName}


            </c:forEach>  
        </table>


    </body>
</html>
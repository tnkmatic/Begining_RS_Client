<%-- 
    Document   : bookResult503
    Created on : 2014/01/31, 8:09:50
    Author     : z2050028
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table title="指定された本のリスト" 
               border="1" cellspacing="0" cellpadding="5"
               bordercolor="#333333">
            <th>ISBN</th>
            <th>タイトル</th>
            <th>ページ数</th>
            <th>金額</th>
            <th>説明</th>

            <%-- 
            <c:forEach var="book" items="${requestScope.bookMap}">
            <tr>
                <td>${book.key}</td>
                <td>${book.value}</td>
            </tr>
            </c:forEach>
           --%>

            <tr>
            <td>${requestScope.bookMap.isbn}</td>
            <td>${requestScope.bookMap.title}</td>
            <td>${requestScope.bookMap.nbOfPage}</td>
            <td>${requestScope.bookMap.price}</td>
            <td>${requestScope.bookMap.description}</td>
            </tr>
            
        </table>
    </body>
</html>

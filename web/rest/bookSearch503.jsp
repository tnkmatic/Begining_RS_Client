<%-- 
    Document   : bookSearch503.jsp
    Created on : 2014/02/01, 10:31:44
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>本の検索</title>
    </head>
    <body>
        <h1>本の検索(ISBN)</h1>
        <form id="formSearchBook" method="get" 
              action="<%= request.getContextPath() %>/BookResourceClient503">
            <label for="isbn">ISBN</label>
            <input id="isbn" type="text" name="isbn" required="true" />
            <input type="submit" value="検索" />
        </form>

    </body>
</html>

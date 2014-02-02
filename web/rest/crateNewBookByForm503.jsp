<%-- 
    Document   : crateNewBookByForm
    Created on : 2014/02/01, 12:19:17
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>本の登録</title>
    </head>
    <body>
        <h1>本の登録</h1>
        <form id="formCreateNewBookByForm" method="post"
              action="<%= request.getContextPath() %>/BookResourceClient503">
            <label for="isbn">ISBN</label>
            <input type="text" id="isbn" name="isbn" />
            <label for="title">タイトル</label>
            <input type="text" id="title" name="title" />
            <br>
            <input type="submit" value="登録" />
        </form>
        
    </body>
</html>

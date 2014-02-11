<%-- 
    Document   : createNewBookByFormResult503
    Created on : 2014/02/01, 13:09:21
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>登録完了！</h1>
        <h2>以下の本の登録完了しました</h2>
        <table border="1">
            <th>ISBN</th>
            <th>タイトル</th>            
            <tr>
                <td>${requestScope.isbn}</td>
                <td>${requestScope.title}</td>
            </tr>
        </table>
        <hr style="border-top: 4px double #ff9d9d; width:100%; height:3px;" />
        <a href="<%= request.getContextPath() %>/rest/crateNewBookByForm503.jsp">
            登録画面へ戻る
        </a>
        <a href="<%= request.getContextPath() %>/rest/index.jsp">メニューに戻る</a>
    </body>
</html>

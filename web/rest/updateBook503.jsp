<%-- 
    Document   : updateBook
    Created on : 2014/02/11, 11:46:59
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>本の更新</title>
    </head>
    <body>
        <h1>本の更新</h1>
        <form method="post"
              action="<%= request.getContextPath()%>/BookResourceClient503" >
            <input type="hidden" name="_method" value="put" />
            <table>
                <tr>
                    <td><label for="isbn">ISBN</label></td>
                    <td><input type="text" id="isbn" name="isbn" /></td>
                </tr>
                <tr>
                    <td><label for="title">タイトル</label></td>
                    <td><input type="text" id="isbn" name="title" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="更新" /></td>
                </tr>
            </table>
        </form>
        <a href="<%= request.getContextPath() %>/rest/index.jsp">メニューに戻る</a>
    </body>
</html>

<%-- 
    Document   : deleteBook503
    Created on : 2014/02/11, 12:43:37
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>本の削除</title>
    </head>
    <body>
        <h1>本の削除</h1>
        <form method="post" 
              action="<%= request.getContextPath() %>/BookResourceClient503">
            <input type="hidden" name="_method" value="delete" />
            <table>
                <tr>
                    <td><label for="isbn">ISBN</label></td>
                    <td><input type="text" id="isbn" name="isbn" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="削除" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>

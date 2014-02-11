<%-- 
    Document   : index
    Created on : 2014/02/11, 10:01:34
    Author     : Eiichi Tanaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<c:set var="path" value="rest" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JAX-RS Client Menu</title>
    </head>
    <body>
        <h1>JAX-RSサンプル</h1>
        <table border="1">
            <th>No</th>
            <th>タイトル</th>
            <tr>
                <td>1</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/getAllBooks.html">
                    本の全件検索</a>
                </td>
            </tr>
            <tr>
                <td>2</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/bookSearch503.jsp">
                    リソースキー指定の検索</a>
                </td>
            </tr>
            <tr>
                <td>3</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/createNewBook.html">
                    本の登録(AJAX-JSON)</a>
                </td>
            </tr>
            <tr>
                <td>4</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/crateNewBookByForm503.jsp">
                    本の登録(POSTボディ-application/x-www-form-urlencoded)</a>
                </td>
            </tr>
            <tr>
                <td>5</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/updateBook503.jsp">
                    本の更新</a>
                </td>
            </tr>
            <tr>
                <td>6</td>
                <td>
                    <a href="<%= request.getContextPath() %>/${path}/deleteBook503.jsp">
                    本の削除</a>
                </td>
            </tr>
        </table>
    </body>
</html>
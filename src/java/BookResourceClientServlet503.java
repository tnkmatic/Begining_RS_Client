/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author Eiichi Tanaka
 */
@WebServlet(urlPatterns = {"/BookResourceClient503"})
public class BookResourceClientServlet503 extends HttpServlet {
    private static java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(
            BookResourceClientServlet503.class.getName());
    
    private static final String END_POINT 
            = "http://localhost:8080/Begining_EJB_GF4-war/rs/books/";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String isbn = request.getParameter("isbn");
        Map<String, String> bookMap = selectBookByIsbn(isbn);
        
        response.setContentType("text/html;charset=UTF-8");
        
        if (bookMap == null 
                || bookMap.isEmpty()) {
            request.setAttribute("resultEmpty", 0);
        } else {
            request.setAttribute("bookMap", bookMap);     
        }
        
        request.getRequestDispatcher("rest/bookResult503.jsp")
                .forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String method = request.getParameter("_method");
        final String isbn   = request.getParameter("isbn");
        final String title  = request.getParameter("title");
        
        logger.log(Level.INFO
                    ,"POSTボディ：_method={0}, isbn={1}, タイトル={2}"
                    ,new String[] {
                         method
                        ,isbn
                        ,title
                    });
        
        String forwardPath = null;
        
        if ("post".equals(method)
                || "".equals(method)
                || method == null) { 
            request.setAttribute("isbn",  isbn);
            request.setAttribute("title", title);

            Response res = createNewBookByForm(isbn, title);
            
            forwardPath = "rest/createNewBookByFormResult503.jsp";
        }  else if ("put".equals(method)) {
            updateBook(isbn, title);
            forwardPath = "rest/index.jsp";
        } else if ("delete".equals(method)) {
            deleteBook(isbn);
            forwardPath = "rest/index.jsp";
        }

        request.getRequestDispatcher(forwardPath).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * 
     * @param isbn
     * @return
     * @throws ServletException 
     * リソースキー指定の検索
     * 
     */
    private Map<String, String> selectBookByIsbn(final String isbn) throws ServletException {
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(END_POINT).path(isbn);
        
        logger.log(Level.INFO
                ,"Request URI = {0}"
                ,new String[] {
                    webTarget.getUri().toASCIIString()
                });
        
        try {
            String jsonBook = webTarget.request(MediaType.APPLICATION_JSON)
                    .get(String.class);
        
            logger.log(Level.INFO
                    ,"{0}"
                    ,new String[] {
                        jsonBook
                    });
            
            //JSON→Map変換
            Map<String, String> bookMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                bookMap = mapper.readValue(
                        jsonBook, new TypeReference<HashMap<String, String>>(){});
            } catch (Exception e) {
                throw new ServletException(e);
            }
            
            return bookMap;
        } catch (Exception e) {
            logger.log(Level.SEVERE
                    ,"{0}"
                    ,new String[] {
                        e.getMessage()
                    });
            throw new ServletException(e);
        }
    }
    
    /**
     * 
     * @param isbn
     * @param title
     * @return
     * @throws ServletException 
     * POSTボディ(APPLICATION_FORM_URLENCODED)での本の登録
     * 
     */
    private Response createNewBookByForm(
            final String isbn, final String title) throws ServletException {
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(END_POINT);
        
        logger.log(Level.INFO
                ,"Request URI = {0}"
                ,new String[] {
                    webTarget.getUri().toASCIIString()
                });
        
        try {
            MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
            map.add("isbn", isbn);
            map.add("title", title);

            Form form = new Form(map);
            
            logger.log(Level.INFO
                    ,"登録データ：isbn={0}, タイトル={1}"
                    ,new String[] {
                        isbn
                        ,title
                    });
            
            Response response =
                    webTarget.request(
                        MediaType.APPLICATION_FORM_URLENCODED + " ;charset=utf-8")
                    .post(Entity.form(form));
            
            return response;
        } catch (Exception e) {
            logger.log(Level.SEVERE
                ,"{0}"
                ,new String[] {
                    e.getMessage()
                });
            throw new ServletException(e);
        }
    }
    
    /**
     * 
     * @param isbn
     * @param title
     * @throws ServletException 
     * リソースキー指定で本を更新
     */
    private void updateBook(final String isbn, final String title)
            throws ServletException {
        //httpクライアントの生成
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(END_POINT).path(isbn);
        
        logger.log(Level.INFO
                ,"Request URI = {0}"
                ,new String[] {
                    webTarget.getUri().toASCIIString()
                });
        
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("title", title);
        
        String bookJson = makeJson(valueMap);

        Response response =
                webTarget.request(
                    MediaType.APPLICATION_JSON + " ;charset=utf-8")
                .put(Entity.json(bookJson));
    }
    
    /**
     * 
     * @param isbn
     * @throws ServletException
     * 本の削除
     */
    private void deleteBook(final String isbn) throws ServletException {
        //httpクライアントの作成
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(END_POINT).path(isbn);
        
        logger.log(Level.INFO
                ,"Request URI = {0}"
                ,new String[] {
                    webTarget.getUri().toASCIIString()
                });
        
        Response response = webTarget.request().delete();
    }
    
    /**
     * 
     * @param valueMap
     * @return 
     * MapオブジェクトからJSONへの変換を行う
     */
    private String makeJson(final Map<String, Object> valueMap)
            throws ServletException {
        final ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        
        try {
            jsonString = mapper.writeValueAsString(valueMap);
        } catch (JsonGenerationException | JsonMappingException e1) {
            logger.log(Level.SEVERE, e1.getMessage());
            throw new ServletException(e1);
        } catch (IOException e2) {
            logger.log(Level.SEVERE, e2.getMessage());
            throw new ServletException(e2);
        }

        return jsonString;
    }
}

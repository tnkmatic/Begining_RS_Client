/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.type.TypeReference;
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
        final String isbn  = request.getParameter("isbn");
        final String title = request.getParameter("title");
        
        logger.log(Level.INFO
                    ,"POSTボディ：isbn={0}, タイトル={1}"
                    ,new String[] {
                        isbn
                        ,title
                    });
        
        request.setAttribute("isbn",  isbn);
        request.setAttribute("title", title);

        Response res = createNewBookByForm(isbn, title);
        
        request.getRequestDispatcher("rest/createNewBookByFormResult503.jsp")
                .forward(request, response);
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

    private Map<String, String> selectBookByIsbn(final String isbn) throws ServletException {
        String resource = "http://localhost:8080/Begining_EJB_GF4-war/rs/books/";
        
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(resource).path(isbn);
        
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
    
    private Response createNewBookByForm(
            final String isbn, final String title) throws ServletException {
        String resource = "http://localhost:8080/Begining_EJB_GF4-war/rs/books/";
        
        Client httpClient = ClientBuilder.newClient();
        WebTarget webTarget = httpClient.target(resource);
        
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
}

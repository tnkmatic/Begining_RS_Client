/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Eiichi Tanaka
 */
@WebServlet(urlPatterns = {"/BookResourceClient503"})
public class BookResourceClientServlet503 extends HttpServlet {
    private static java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(
            BookResourceClientServlet503.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        final String isbn = request.getParameter("isbn");
        Map<String, String> bookMap = selectBookByIsbn(isbn);
        
        response.setContentType("text/html;charset=UTF-8");
        
        request.setAttribute("bookMap", bookMap);        
        request.getRequestDispatcher("rest/bookResult503.jsp")
                .forward(request, response);
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
            Map<String, String> bookMap = new HashMap<String, String>();
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
}

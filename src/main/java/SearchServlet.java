import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        if (query != null && !query.trim().isEmpty()) {
            // Redirect to Google with search query
            response.sendRedirect("https://www.google.com/search?q=" + query);
        } else {
            response.sendRedirect("search.htm");
        }
    }
}
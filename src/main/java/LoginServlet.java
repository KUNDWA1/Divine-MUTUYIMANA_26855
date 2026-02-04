import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        
        if (password == null || password.length() < 8) {
            out.println("<h3>Hello " + username + ", your password is weak. Try a strong one.</h3>");
        } else {
            out.println("<h3>Welcome " + username + "</h3>");
        }
        
        out.println("</body></html>");
    }
}
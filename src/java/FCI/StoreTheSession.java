package FCI;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MrHacker
 */
@WebServlet(urlPatterns = {"/StoreTheSession"})
public class StoreTheSession extends HttpServlet {

    PrintWriter outw;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<User> userList = new ArrayList<User>();

        try (PrintWriter out = response.getWriter()) {

            outw = out;
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            out.println("<h>" + email + "</h><br>");

            Map<String, HttpSession> sessionsManager = (HashMap<String, HttpSession>) this
                    .getServletContext()
                    .getAttribute("sessionsManager");

            if (sessionsManager == null) {//sessionsManager not exist
                out.print("sessionManger == null");
                request.getServletContext().removeAttribute("MyCurrentSession");
                response.sendRedirect("intro.jsp");
            }

            //Create session
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("username", username);
            newSession.setMaxInactiveInterval(3 * 60);

            //Create user object
            User user = new User(newSession.getId(),
                    username,
                    email,
                    phone);

            //Create cookie that contains sessionId
            Cookie myCurrentSession = new Cookie("MyCurrentSession", newSession.getId());
            myCurrentSession.setMaxAge(3 * 60);
            myCurrentSession.setPath("/");

            //put session(sessId -> username) into session manager
            sessionsManager.put(newSession.getId(), newSession);
            request.getServletContext().setAttribute("sessionsManager", sessionsManager);

            //insert into db
            Database.insertIntoDB(out, user);
            response.addCookie(myCurrentSession);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StoreTheSession</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreTheSession at " + request.getContextPath() + "</h1>");

            if (sessionsManager != null) {
                out.println("<h>" + "Sessiosn = " + sessionsManager.size() + "</h><br>");

                for (String c : sessionsManager.keySet()) {
                    out.println("<h>" + c + "</h><br>");
                }
            }

            response.sendRedirect("intro.jsp");

            String form = "<form  action=\"Logout\" method=\"post\">"
                    + "<input type=\"submit\" value=\"Logout\"/>"
                    + "</form>";

            out.print(form);

            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            outw.println(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

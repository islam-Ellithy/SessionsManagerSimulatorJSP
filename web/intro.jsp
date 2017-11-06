<%-- 
    Document   : intro
    Created on : Oct 23, 2017, 12:14:29 AM
    Author     : MrHacker
--%>
<%@page import="FCI.Forms"%>
<%@page import="FCI.Database"%>
<%@page import="FCI.User"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Intro</title>
    </head>
    <body style="background: coral">
    <center style="border: brown;align-items: 
            flex-start;background-color: coral;border-image-slice: 10px">

        <h1><font color="darkred">Form registeration Yala</font></h1>
            <%
                Cookie myCurrentSession = null;

                boolean sessionsManagerNotExist = application.getAttribute("sessionsManager") == null;
                String sessionId = null;
                int numOfSessions = 0;

                Cookie[] cookies = request.getCookies();

                for (Cookie c : cookies) {
                    if (c.getName().equals("MyCurrentSession")) {
                        myCurrentSession = c;
                    }
                }

                boolean MyCurrentSessionExist = myCurrentSession != null;
                User user = new User("", "", "", "");
                if (MyCurrentSessionExist) {
                    sessionId = myCurrentSession.getValue();
                }

                String username = sessionId;
                String email = user.getEmail();
                String phone = user.getPhone();

                //out.print(MyCurrentSessionExist + "<br>");
                //out.print(sessionsManagerNotExist + "<br>");
                if (MyCurrentSessionExist) {//MyCurrentSession exist
                    // String sessionId = application.getAttribute("MyCurrentSession").toString();
                    if (!sessionsManagerNotExist) {

                        Map<String, HttpSession> sessionsManager = (HashMap<String, HttpSession>) application
                                .getAttribute("sessionsManager");

                        if (sessionsManager.containsKey(sessionId)) {
                            HttpSession newSession = (HttpSession) sessionsManager.get(sessionId);

                            out.print("<h1>sessId = " + sessionId + "</h1><br>");

                            Database.retriveFromDB(user, sessionId);

                            username = user.getUsername();
                            email = user.getEmail();
                            phone = user.getPhone();
                            numOfSessions = sessionsManager.size();

                        } else {
                            application.removeAttribute("MyCurrentSession");
                        }
                    } else {
                        application.removeAttribute("MyCurrentSession");
                    }
                } else {//MyCurrentSession doesn't exist
                    if (sessionsManagerNotExist) {
                        Map<String, HttpSession> sessionsManager = new HashMap<String, HttpSession>();
                        application.setAttribute("sessionsManager", sessionsManager);
                    }

                    out.print(Forms.inputForm());
                }
            %>

        <form type="hidden" action="Logout" method="post">
            <h1><%=username%></h1>
            <h1><%=email%></h1>
            <h1><%=phone%></h1>
            <h1><%=numOfSessions%></h1>
            <input type="submit" value="Logout"/>
        </form>

    </center>

    <script>


        function showLogout() {
            document.getElementById('logout').type = 'submit';
        }


    </script>
</body>
</html>

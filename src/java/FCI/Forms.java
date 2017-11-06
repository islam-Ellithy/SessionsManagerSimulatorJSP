/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FCI;

/**
 *
 * @author MrHacker
 */
public class Forms {

    public final static String inputForm() {
        return "<form method=\"post\" action=\"StoreTheSession\" id=\"info\">"
                + "esmk yala :"
                + "<input type=\"text\" name=\"username\" required/>"
                + "<br>Emailk yala :"
                + "<input type=\"email\" name=\"email\" required/>"
                + "<br>Phonek yala :"
                + "<input type=\"tel\" name=\"phone\" required/><br>"
                + "<input type=\"submit\" value=\"العب يلا\" />"
                + "</form>";
    }

    public final static String displayForm() {

        return "<form type=\"hidden\" action=\"Logout\" method=\"post\">"
                + "<h1><%=username%></h1>"
                + "<h1><%=email%></h1>"
                + "<h1><%=phone%></h1>"
                + "<h1><%=numOfSessions%></h1>"
                + "<input type=\"submit\" value=\"Logout\"/>"
                + " </form>";
    }

}

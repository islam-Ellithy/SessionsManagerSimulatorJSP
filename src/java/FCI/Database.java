package FCI;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MrHacker
 */
public class Database {

    public static void retriveFromDB(User userList, String sessionId) {

        Connection conn = null;
        Statement st = null;
        try {
            //connet to mysql

            Class.forName("org.gjt.mm.mysql.Driver");
            try {

                conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "");

                st = conn.createStatement();

                ResultSet users = st.executeQuery(
                        "SELECT * FROM user where sessionId = '" + sessionId + "'");

//                out.println("After Query<br>");
                while (users.next()) {
                    
                    userList.setEmail(users.getString("email"));
                    userList.setUsername(users.getString("username"));
                    userList.setPhone(users.getString("phone"));
                    userList.setSessionId(users.getString("sessionId"));

/*                    userList = new User(users.getString("sessionId"),
                            users.getString("username"),
                            users.getString("email"),
                            users.getString("phone")
                    );
  */                  //                  out.println(users.getString("username") + "<br>");
                }
            } catch (SQLException ex) {
                Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                //            out.print(ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
            //      out.print(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void insertIntoDB(PrintWriter out, User user) {
        Connection conn = null;
        Statement statement = null;
        try {
            //connet to mysql
            Class.forName("org.gjt.mm.mysql.Driver");
            try {

                conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "");

                statement = conn.createStatement();

                //insert into db
                String query = "INSERT INTO "
                        + "user(sessionId ,username,email,phone)"
                        + "VALUES ('" + user.getSessionId() + "',"
                        + "'" + user.getUsername() + "',"
                        + "'" + user.getEmail() + "',"
                        + "'" + user.getPhone() + "'"
                        + ")";

                int rowCount = statement.executeUpdate(query);

            } catch (SQLException ex) {
                Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                out.print(ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
            out.print(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteFromDB(String sessionId) {
        Connection conn = null;
        Statement statement = null;
        try {
            //connet to mysql
            Class.forName("org.gjt.mm.mysql.Driver");
            try {

                conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "");

                statement = conn.createStatement();

                //insert into db
                String query = "DELETE From "
                        + "user where sessionId = "
                        + "'" + sessionId + "'";

                int rowCount = statement.executeUpdate(query);

            } catch (SQLException ex) {
                Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
            //out.print(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StoreTheSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

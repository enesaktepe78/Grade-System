package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataManage {
    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //Connection String
    //String connStr = "jdbc:mysql://localhost:3306/YourDBName";
    private static final String connStr = "jdbc:mysql://localhost:3306/grade";

    //Connection
    private static Connection conn = null;
    private static String user = "root";
    private static String password = "";

    //Connect to DB
    public static Connection dbConnect() throws SQLException, ClassNotFoundException {
        //Setting JDBC Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Driver");
            e.printStackTrace();
            throw e;
        }
        //Establish Connection using Connection String
        try {
            conn = DriverManager.getConnection(connStr, user, password);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
        return  conn;
    }
    //Close Connection
    public static void dbDisconnect(Connection conn) {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

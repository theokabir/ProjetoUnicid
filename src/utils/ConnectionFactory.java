package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    /**
     * Função estatica que cria um objeto {@link Connection} que é utilizado para executar comandos em um banco mysql
     * @return retorna o objeto {@link Connection}
     * @throws Exception retorna os erros gerados por falha na conexão ou falha ao encontrar o driver mysql
     */
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/projeto?characterEncoding=utf-8";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }


    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        close(conn, stmt, rs);
    }

    public static void closeConnection(Connection conn, Statement stmt) throws Exception {
        close(conn, stmt, null);
    }

    public static void closeConnection(Connection conn) throws Exception {
        close(conn, null, null);
    }

    private static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        if (rs != null)
            rs.close();
        if (stmt != null)
            stmt.close();
        if (conn != null)
            conn.close();
    }
}
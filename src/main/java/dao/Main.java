package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {

            Connection connection = MySQLConnection.getConnection();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            MySQLConnection.shutdown();
        }
    }
}

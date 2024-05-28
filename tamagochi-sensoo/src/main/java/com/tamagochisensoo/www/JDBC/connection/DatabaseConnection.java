package com.tamagochisensoo.www.JDBC.connection;

import java.sql.*;

public class DatabaseConnection {
    protected final String url = "jdbc:mysql://localhost:3360/TamagochiSensoo";
    protected final String username = "root";
    protected final String password = "root";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void initializeDatabase() {

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet creatureTable = conn.getMetaData().getTables(null, null, "Creature", null);
            if (!creatureTable.next()) {
                // Create the 'Place' table
                stmt.executeUpdate("CREATE TABLE Creature (id BIGINT AUTO_INCREMENT, shape VARCHAR(20), color VARCHAR(100), life FLOAT, hunger FLOAT, confort FLOAT, PRIMARY KEY(id))");
                System.out.println("Creature table created");
            }

            ResultSet winTable = conn.getMetaData().getTables(null, null, "Win", null);
            if (!winTable.next()) {
                // Create the 'Trip' table
                stmt.executeUpdate("CREATE TABLE Win (id BIGINT AUTO_INCREMENT, creature_id BIGINT, date_win DATE, PRIMARY KEY(id), FOREIGN KEY (creature_id) REFERENCES Creature(id))");
                System.out.println("Win logs table created");
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

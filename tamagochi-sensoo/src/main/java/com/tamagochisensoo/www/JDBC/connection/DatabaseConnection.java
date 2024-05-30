package com.tamagochisensoo.www.JDBC.connection;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

import com.tamagochisensoo.www.Exceptions.NoConfigFileException;

public class DatabaseConnection {
    // Standardizes the way we connect to the MySQL DB
    
    private Ini config;
    protected final String url;
    protected final String username;
    protected final String password;

    public DatabaseConnection() throws NoConfigFileException {
        try {
            config = new Ini(new File("./tamagochi-sensoo/config.ini"));
        } catch (InvalidFileFormatException e) {
            throw new NoConfigFileException(e.toString());
        } catch (IOException e) {
            throw new NoConfigFileException(e.toString());
        }
        url = config.get("database", "url");
        username = config.get("database", "username");
        password = config.get("database", "password");
    }

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

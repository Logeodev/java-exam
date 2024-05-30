package com.tamagochisensoo.www.JDBC.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.connection.DatabaseConnection;

public class WinDao extends DatabaseConnection {

    public WinDao() throws NoConfigFileException {
        super();
    }

    public void addWin(Win w) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Win (creature_id, date_win) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, w.creature_id);
            stmt.setDate(2, w.date_win);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving creature failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Win> getWins() {
        List<Win> wins = new ArrayList<Win>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Win";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Win w = new Win();
                w.id = result.getDouble("id");
                w.creature_id = result.getDouble("creature_id");
                w.date_win = result.getDate("date_win");
                wins.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        wins.sort(new Comparator<Win>() {
            @Override
            public int compare(Win w1, Win w2) {
                return w1.date_win.compareTo(w2.date_win);
            }
        });
        return wins;
    }
}

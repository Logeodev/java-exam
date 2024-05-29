package com.tamagochisensoo.www.JDBC.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Creature.CreatureShape;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.connection.DatabaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Callback;

public class CreatureDao extends DatabaseConnection {

    public CreatureDao() throws NoConfigFileException {
        super();
    }
    
    public double save(Creature creature) {
        try (Connection conn = getConnection()) {
            String selectSql = "SELECT id FROM Creature WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setDouble(1, creature.getId());
            ResultSet selectRes = selectStmt.executeQuery();
            boolean idExists = selectRes.next() && creature.getId() > 0;

            if (idExists) {
                String updateSql = "UPDATE Creature SET shape=?, color=?, life=?, hunger=?, confort=? WHERE id=?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, creature.getShape().name());
                updateStmt.setString(2, creature.getColor().toString());
                updateStmt.setDouble(3, creature.getLife());
                updateStmt.setDouble(4, creature.getHunger());
                updateStmt.setDouble(5, creature.getConfort());
                updateStmt.setDouble(6, creature.getId());
                int updatedRows = updateStmt.executeUpdate();

                if (updatedRows == 0) {
                    throw new SQLException("Updating creature failed, no rows affected.");
                }
                return creature.getId();
            } else {
                String insertSql = "INSERT INTO Creature (shape, color, life, hunger, confort) VALUES (?,?,?,?,?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, creature.getShape().name());
                insertStmt.setString(2, creature.getColor().toString());
                insertStmt.setDouble(3, creature.getLife());
                insertStmt.setDouble(4, creature.getHunger());
                insertStmt.setDouble(5, creature.getConfort());
                int affectedRows = insertStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Saving creature failed, no rows affected.");
                }
                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getDouble(1);
                    } else {
                        throw new SQLException("Creating place failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Creature> listCreatures() {
        List<Creature> creatures = new ArrayList<Creature>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Creature";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Creature c = new Creature(Color.valueOf(result.getString("color")), CreatureShape.valueOf(result.getString("shape")));
                c.setConfort(result.getDouble("confort"));
                c.setHunger(result.getDouble("hunger"));
                c.setLife(result.getDouble("life"));
                c.setId(result.getDouble("id"));
                creatures.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creatures;
    }

    public ListView<Creature> makeListView() {
        class CreatureCell extends ListCell<Creature> {
            @Override
            public void updateItem(Creature item, boolean empty) {
                super.updateItem(item, empty);
                Shape s = new Rectangle(20, 20);
                if (item != null) {
                    switch (item.getShape()) {
                        case SQUARE:
                            s = new Rectangle(20,20);
                            break;
                        case OVAL:
                            s = new Circle(5,5,10);
                            break;
                        default:
                            break;
                    }
                    s.setFill(item.getColor());
                    setGraphic(s);
                    setText(item.toString());
                }
            }
        }

        List<Creature> gamesList = listCreatures();

        ObservableList<Creature> obsList = FXCollections.observableArrayList();
        gamesList.forEach(c -> {
            obsList.add(c); 
        });
        ListView<Creature> lv = new ListView<>(obsList);
        lv.setCellFactory(new Callback<ListView<Creature>,ListCell<Creature>>() {
            @Override
            public ListCell<Creature> call(ListView<Creature> l) {
                return new CreatureCell();
            }
        });

        return lv;
    }

    public void deleteFromId(double id) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM Win WHERE creature_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, id);
            stmt.executeUpdate();

            sql = "DELETE FROM Creature WHERE id = ?";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Saving creature failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

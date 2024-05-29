package com.tamagochisensoo.www.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tamagochisensoo.www.Controllers.Combat;
import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.FightNotFoundException;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CombatRoom extends Room {
    private Combat fightInstance;
    private Socket socket;
    private BufferedReader reader;

    public CombatRoom(double x, double y, double width, double height, Stage stage) {
        super(x, y, width, height, stage);
        
        makeJoinPage();

        Scene scene = new Scene(pane, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public Color getBackgroundColor() {
        return Color.LIGHTCORAL;
    }

    private void makeJoinPage() {
        Label serverLabel = new Label("Choose 2 creatures to make a fight");
        CreatureDao cdao = null;
        try {
            cdao = new CreatureDao();
        } catch (NoConfigFileException e) {
            e.printStackTrace();
        }
        ListView<Creature> lv = cdao != null ? cdao.makeListView() : new ListView<Creature>();

        lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox vb = new VBox();
        Button joinBtn = new Button("Create fight instance");
        joinBtn.setOnAction(evnt -> {
            Creature[] selectedAdversaries = new Creature[2];
            lv.getSelectionModel().getSelectedItems().toArray(selectedAdversaries);
            startCombat(selectedAdversaries[0], selectedAdversaries[1]);
        });
        joinBtn.disableProperty().bind(
            Bindings.size(lv.getSelectionModel().getSelectedItems()).isNotEqualTo(2)
        );
        
        vb.getChildren().addAll(serverLabel, lv, joinBtn);
        vb.setSpacing(5);
        vb.setLayoutX(300);
        vb.setLayoutY(150);
        
        this.pane.getChildren().add(vb);
    }

    private void startCombat(Creature c1, Creature c2) {
        int servPort = 9254;
        try {
            fightInstance = new Combat(servPort, c1, c2);
            socket = new Socket("localhost", servPort);
            reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
            fightInstance.start();
            String line;
            int winnerId = -1;
            while (
                (line = reader.readLine()) != null
            ) {
                System.out.println(line);
                if (line.contains("WIN")) {
                    winnerId = Integer.parseInt(line.substring(3));
                }
            }
            Creature winner;
            Creature loser;
            if (c1.getId() == winnerId) {
                winner = c1;
                loser = c2;
            } else {
                winner = c2;
                loser = c1;
            }
            closeCombat();
        } catch (FightNotFoundException fnf) {

        } catch (UnknownHostException uhe) {

        } catch (IOException ioe) {

        }
    }

    private void closeCombat() {
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}

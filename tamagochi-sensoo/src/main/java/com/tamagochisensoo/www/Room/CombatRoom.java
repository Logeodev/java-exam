package com.tamagochisensoo.www.Room;

import java.io.IOException;

import com.tamagochisensoo.www.Controllers.Combat;
import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.FightNotFoundException;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CombatRoom extends Room {
    private Combat fightInstance;

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
        ListView<Creature> lv = new ListView<>();
        try {
            lv = new CreatureDao().makeListView();
        } catch (NoConfigFileException e) {
            e.printStackTrace();
        }

        VBox vb = new VBox();
        Button joinBtn = new Button("Create fight instance");
        joinBtn.setOnAction(evnt -> {
            startCombat();
        });
        
        vb.getChildren().addAll(serverLabel, lv, joinBtn);
        vb.setSpacing(5);
        vb.setLayoutX(300);
        vb.setLayoutY(150);
        
        this.pane.getChildren().add(vb);
    }

    private void startCombat() {
        try {
            fightInstance = new Combat(9254, null, null);
            fightInstance.doCombat();
        } catch (FightNotFoundException fnf) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

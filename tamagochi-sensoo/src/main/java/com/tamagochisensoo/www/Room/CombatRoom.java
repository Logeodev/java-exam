package com.tamagochisensoo.www.Room;

import java.io.IOException;

import com.tamagochisensoo.www.Controllers.Combat;
import com.tamagochisensoo.www.Exceptions.FightNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CombatRoom extends Room {
    private Combat fightInstance;
    private TextField serverField;

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
        Label serverLabel = new Label("Enter server adress : ");
        serverField = new TextField();
        HBox hb = new HBox();
        hb.getChildren().addAll(serverLabel, serverField);
        hb.setSpacing(5);

        VBox vb = new VBox();
        Button joinBtn = new Button("Join fight instance");
        joinBtn.setOnAction(evnt -> {
            startCombat();
        });
        
        vb.getChildren().addAll(hb, joinBtn);
        vb.setLayoutX(300);
        vb.setLayoutY(300);
        
        this.pane.getChildren().add(vb);
    }

    private void startCombat() {
        try {
            fightInstance = new Combat(0, null, null);
            fightInstance.doCombat();
        } catch (FightNotFoundException fnf) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

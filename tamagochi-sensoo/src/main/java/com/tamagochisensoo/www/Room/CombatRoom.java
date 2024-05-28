package com.tamagochisensoo.www.Room;

import java.io.IOException;

import com.tamagochisensoo.www.Controllers.Combat;
import com.tamagochisensoo.www.Exceptions.FightNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CombatRoom extends Room {
    private Combat fightInstance;
    private TextField serverField;

    public CombatRoom(double x, double y, double width, double height) {
        super(x, y, width, height);
        
        makeJoinPage();
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
        hb.setSpacing(10);

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

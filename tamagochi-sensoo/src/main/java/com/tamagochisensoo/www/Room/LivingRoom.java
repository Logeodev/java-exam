package com.tamagochisensoo.www.Room;

import com.tamagochisensoo.www.Creature.Creature;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LivingRoom extends Room {
    private Creature creature;
    private Button feedBtn;
    private Button fightBtn;

    public LivingRoom(double x, double y, double width, double height, Creature creature) {
        super(x, y, width, height);
        this.creature = creature;

        makeFeedingButton();
        makeJoinFightButton();

        this.pane.getChildren().addAll(feedBtn, fightBtn);
    }

    @Override
    public Color getBackgroundColor() {
        return Color.BEIGE;
    }

    private void makeFeedingButton() {
        feedBtn = new Button("Feed");
        feedBtn.setLayoutX(700);
        feedBtn.setLayoutY(50);
        feedBtn.setOnAction(evnt -> {
            creature.eat();
            feedBtn.setDisable(true);
            PauseTransition pause = new PauseTransition(
                Duration.seconds(10)
            );
            pause.setOnFinished(e -> feedBtn.setDisable(false));
            pause.play();
        });
    }

    private void makeJoinFightButton() {
        fightBtn = new Button("Search a fight");
        fightBtn.setLayoutX(700);
        fightBtn.setLayoutY(100);
        fightBtn.setOnAction(evnt -> {

        });
    }
}

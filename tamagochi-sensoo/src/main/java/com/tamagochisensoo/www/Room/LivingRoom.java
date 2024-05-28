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
    private Button sleepBtn;

    public LivingRoom(double x, double y, double width, double height, Creature creature) {
        super(x, y, width, height);
        this.creature = creature;

        makeFeedingButton();
        makeSleepButton();
        makeJoinFightButton();

        this.pane.getChildren().addAll(feedBtn, sleepBtn, fightBtn);
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
        fightBtn.setLayoutY(150);
        fightBtn.setOnAction(evnt -> {
            this.pane.getChildren().add(new CombatRoom(0,0,800,800).getPane());
        });
    }

    private void makeSleepButton() {
        sleepBtn = new Button("Sleep");
        sleepBtn.setLayoutX(700);
        sleepBtn.setLayoutY(100);
        sleepBtn.setOnAction(evnt -> {
            creature.sleep();
            sleepBtn.setDisable(true);
            PauseTransition pause = new PauseTransition(
                Duration.seconds(20)
            );
            pause.setOnFinished(e -> sleepBtn.setDisable(false));
            pause.play();
        });
    }
}

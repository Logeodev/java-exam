package com.tamagochisensoo.www.Room;

import com.tamagochisensoo.www.Bars.Bar;
import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LivingRoom extends Room {
    private Creature creature;
    private Button feedBtn;
    private Button fightBtn;
    private Button sleepBtn;
    private Button quitBtn;

    public LivingRoom(double x, double y, double width, double height, Creature creature, Stage stage) {
        super(x, y, width, height, stage);
        this.creature = creature;
        this.creature.startLifeCycle();
        creature.setPosX(350);

        makeFeedingButton();
        makeSleepButton();
        makeJoinFightButton();
        makeQuitButton();

        this.pane.getChildren().addAll(feedBtn, sleepBtn, fightBtn, quitBtn, creature.getPane());
        for (Bar bar : creature.getBars()) {
            bar.getPane().setLayoutX(10);
            pane.getChildren().add(bar.getPane());
        }

        Scene scene = new Scene(this.pane, 800,800);
        stage.setTitle("Creature Personalization");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public Color getBackgroundColor() {
        return Color.BEIGE;
    }

    private void makeFeedingButton() {
        feedBtn = new Button("Feed");
        feedBtn.setLayoutX(600);
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
        fightBtn = new Button("Make a fight");
        fightBtn.setLayoutX(600);
        fightBtn.setLayoutY(150);
        fightBtn.setOnAction(evnt -> {
            creature.interruptLifeCycle();
            new JoiningFightRoom(0,0,800,800, stage);
        });
    }

    private void makeSleepButton() {
        sleepBtn = new Button("Sleep");
        sleepBtn.setLayoutX(600);
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

    private void makeQuitButton() {
        quitBtn = new Button("Save and quit game");
        quitBtn.setLayoutX(600);
        quitBtn.setLayoutY(200);
        quitBtn.setOnAction(evnt -> {
            try {
                new CreatureDao().save(creature);
            } catch (NoConfigFileException e) {
                e.printStackTrace();
            }
            stage.close();
        });
    }
}

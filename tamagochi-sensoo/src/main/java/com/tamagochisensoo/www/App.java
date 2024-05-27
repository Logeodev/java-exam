package com.tamagochisensoo.www;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import com.tamagochisensoo.www.Bars.*;
import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Creature.CreatureShape;
import com.tamagochisensoo.www.Room.*;


public class App extends Application {

    private static Scene scene;
    private static Room currentRoom;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        currentRoom = new LivingRoom(0, 0, 800, 800);

        Creature c = new Creature(Color.DARKBLUE, CreatureShape.OVAL);
        currentRoom.getPane().getChildren().add(c.getPane());

        double barsWidth = 200;
        double barsHeigth = 20;
        Bar[] bars = {
            new LifeBar(100, 10, 10, barsWidth, barsHeigth),
            new HungerBar(100, 10, 40, barsWidth, barsHeigth),
            new ConfortBar(100, 10, 70, barsWidth, barsHeigth)
        };

        for (Bar bar : bars) {
            currentRoom.getPane().getChildren().add(bar.getPane());
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(currentRoom.getPane());

        scene = new Scene(root, 800, 600);
        stage.setTitle("Tamagochi Sensoo");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}
package com.tamagochisensoo.www;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
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
        showLauncherScreen(stage);
    }

    private void showLauncherScreen(Stage stage) {
        VBox launcherLayout = new VBox();
        Button startNewGameButton = new Button("Start New Game");
        startNewGameButton.setOnAction(e -> {
            showPersonalizationScreen(stage);
        });

        Button resumeGameButton = new Button("Resume Game");
        resumeGameButton.setOnAction(e -> {
            // Add logic to resume a game
        });

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            stage.close();
        });

        launcherLayout.getChildren().addAll(startNewGameButton, resumeGameButton, quitButton);
        Scene launcherScene = new Scene(launcherLayout, 800, 800);
        stage.setTitle("Tamagochi Sensoo Launcher");
        stage.setScene(launcherScene);
        stage.show();
    }

    private void startNewGame(Stage stage, Creature c) {
        currentRoom = new LivingRoom(0, 0, 800, 800, c);
        currentRoom.getPane().getChildren().add(c.getPane());

        Bar[] bars = c.getBars();

        for (Bar bar : bars) {
            currentRoom.getPane().getChildren().add(bar.getPane());
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(currentRoom.getPane());

        scene = new Scene(root, 800, 800);
        stage.setTitle("Tamagochi Sensoo");
        stage.setScene(scene);
        stage.show();
    }

    private void showPersonalizationScreen(Stage stage) {
        VBox personalizationLayout = new VBox();

        ComboBox<CreatureShape> shapeCbb = new ComboBox<>();
        shapeCbb.getItems().addAll(CreatureShape.values());
        shapeCbb.setValue(CreatureShape.OVAL);

        ColorPicker colorPicker = new ColorPicker(Color.DARKBLUE);

        Button startGame = new Button("Start Game");
        startGame.setOnAction(e -> {
            CreatureShape selectedShape = shapeCbb.getValue();
            Color selectedColor = colorPicker.getValue();
            Creature c = new Creature(selectedColor, selectedShape);
            startNewGame(stage, c);
        });

        personalizationLayout.getChildren().addAll(shapeCbb, colorPicker, startGame);

        scene = new Scene(personalizationLayout, 800,800);
        stage.setTitle("Creature Personalization");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

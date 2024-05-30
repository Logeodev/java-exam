package com.tamagochisensoo.www;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Creature.CreatureShape;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.connection.DatabaseConnection;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;
import com.tamagochisensoo.www.JDBC.daos.Win;
import com.tamagochisensoo.www.JDBC.daos.WinDao;
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
        Button startGameButton = new Button("Start New Game");
        startGameButton.setOnAction(e -> {
            showPersonalizationScreen(stage);
        });

        Button resumeGameButton = new Button("Resume Game");
        resumeGameButton.setOnAction(e -> {
            showResumeGameScreen(stage);
        });

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            stage.close();
        });

        ListView<String> sl = null;
        try {
            sl = showScores();
        } catch (NoConfigFileException e1) {
            e1.printStackTrace();
        }

        launcherLayout.getChildren().addAll(startGameButton, resumeGameButton, quitButton);
        if (sl != null) {
            launcherLayout.getChildren().add(sl);
        }
        Scene launcherScene = new Scene(launcherLayout, 800, 800);
        stage.setTitle("Tamagochi Sensoo Launcher");
        stage.setScene(launcherScene);
        stage.show();
    }

    private void startGame(Stage stage, Creature c) {
        currentRoom = new LivingRoom(0, 0, 800, 800, c, stage);

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
            startGame(stage, c);
        });

        personalizationLayout.getChildren().addAll(shapeCbb, colorPicker, startGame);

        scene = new Scene(personalizationLayout, 800,800);
        stage.setTitle("Creature Personalization");
        stage.setScene(scene);
        stage.show();
    }

    private void showResumeGameScreen(Stage stage) {

        try {
            VBox vb = new VBox();
            Label listLabel = new Label("Select a creature");

            ListView<Creature> lv = new CreatureDao().makeListView();

            Button resumeBtn = new Button("Resume game");
            resumeBtn.setDisable(lv.getSelectionModel().getSelectedItem() == null);
            resumeBtn.disableProperty().bind(lv.getSelectionModel().selectedItemProperty().isNull());
            resumeBtn.setOnAction(evnt -> {
                Creature selectedCreature = lv.getSelectionModel().getSelectedItem();
                startGame(stage, selectedCreature);
            });

            vb.getChildren().setAll(listLabel, lv, resumeBtn);

            stage.setScene(new Scene(vb, 800, 800));
            stage.show();
        } catch (NoConfigFileException e) {
            e.printStackTrace();
        }
    }

    private ListView<String> showScores() throws NoConfigFileException {
        List<Win> scores = new WinDao().getWins();
        ObservableMap<Object, Long> obsScoresMap = FXCollections.observableMap(
            scores.stream()
                .collect(
                    Collectors.groupingBy(elmt -> elmt.creature_id, Collectors.counting())
                    )
        );
        ObservableList<String> obsScoresList = FXCollections.observableArrayList();
        obsScoresMap.forEach((id, count) -> obsScoresList.add(id + " : " + count + " combats gagnés."));
        ListView<String> scoresList = new ListView<String>(obsScoresList);
        
        return scoresList;
    }

    public static void main(String[] args) {
        try {
            new DatabaseConnection().initializeDatabase();
        } catch (NoConfigFileException e) {
            System.out.println("Issue with the configuration file.");
            e.printStackTrace();
        }
        launch();
    }

}

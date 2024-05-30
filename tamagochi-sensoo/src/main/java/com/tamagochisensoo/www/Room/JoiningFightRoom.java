package com.tamagochisensoo.www.Room;

import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JoiningFightRoom extends Room{

    public JoiningFightRoom(double x, double y, double width, double height, Stage stage) {
        super(x, y, width, height, stage);

        makeJoinPage();

        Scene scene = new Scene(pane, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    protected Color getBackgroundColor() {
        return Color.GREY;
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
            new CombatRoom(selectedAdversaries, 0, 0, 800, 800, stage);
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
}

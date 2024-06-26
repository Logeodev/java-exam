package com.tamagochisensoo.www.Room;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public abstract class Room {
    // Standardizes the view controllers

    protected Rectangle background;
    protected Stage stage;
    protected Pane pane;

    public Room(double x, double y, double width, double height, Stage stage) {
        this.stage = stage;
        this.background = new Rectangle(x,y,width, height);
        this.background.setFill(getBackgroundColor());

        this.pane = new Pane();
        this.pane.getChildren().add(this.background);
    }

    protected abstract Color getBackgroundColor();

    public Pane getPane() {
        return this.pane;
    }
}

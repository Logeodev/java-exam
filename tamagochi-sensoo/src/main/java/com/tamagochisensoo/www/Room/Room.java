package com.tamagochisensoo.www.Room;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Room {
    protected Rectangle background;
    protected Pane pane;

    public Room(double x, double y, double width, double height) {
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

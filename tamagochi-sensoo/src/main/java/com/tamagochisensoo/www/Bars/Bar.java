package com.tamagochisensoo.www.Bars;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Bar {
    protected double value;
    protected Rectangle background;
    protected Rectangle bar;
    protected Pane pane;

    public Bar(double value, double x, double y, double width, double height) {
        this.value = value;
        this.background = new Rectangle(x,y,width,height);
        this.background.setFill(Color.LIGHTGRAY);

        this.bar = new Rectangle(x,y,width*value/100, height);
        this.bar.setFill(getColor());

        this.pane = new Pane();
        this.pane.getChildren().addAll(this.background, this.bar);
    }

    protected abstract Color getColor();

    public void setValue(double value) {
        if (value < 100 && value > 0) {
            this.value = value;
        } else if (value >= 100) {
            this.value = 100;
        } else {
            this.value = 0;
        }
        this.bar.setWidth(
            this.background.getWidth() * this.value / 100
        );
    }

    public void display(Pane pane) {
        pane.getChildren().addAll(
            this.background, 
            this.bar
        );
    }

    public Pane getPane() {
        return this.pane;
    }
}

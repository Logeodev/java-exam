package com.tamagochisensoo.www.Bars;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Bar {
    protected DoubleProperty valueProperty;
    protected Rectangle background;
    protected Rectangle bar;
    protected Pane pane;

    public Bar(double value, double x, double y, double width, double height) {
        this.valueProperty = new SimpleDoubleProperty(value);
        this.background = new Rectangle(x, y, width, height);
        this.background.setFill(Color.LIGHTGRAY);

        this.bar = new Rectangle(x, y, width * this.valueProperty.doubleValue() / 100, height);
        this.bar.setFill(getColor());

        this.valueProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
                double newValue = newVal.doubleValue();
                double newWidth = width * newValue / 100;
                bar.setWidth(newWidth);
                bar.setFill(getColor());
            }
        });
        this.pane = new Pane();
        this.pane.getChildren().addAll(this.background, this.bar);
    }

    protected abstract Color getColor();

    public void setValue(double value) {
        if (value < 100 && value > 0) {
            this.valueProperty.set(value);
        } else if (value >= 100) {
            this.valueProperty.set(100);
        } else {
            this.valueProperty.set(0);
        }
    }

    public void display(Pane pane) {
        pane.getChildren().addAll(this.background, this.bar);
    }

    public Pane getPane() {
        return this.pane;
    }
}

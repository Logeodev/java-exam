package com.tamagochisensoo.www.Bars;

import javafx.scene.paint.Color;

public class LifeBar extends Bar {

    public LifeBar(double value, double x, double y, double width, double height) {
        super(value, x, y, width, height);
    }

    @Override
    protected Color getColor() {
        return Color.GREEN;
    }
}

package com.tamagochisensoo.www.Bars;

import javafx.scene.paint.Color;

public class HungerBar extends Bar {

    public HungerBar(double value, double x, double y, double width, double height) {
        super(value, x, y, width, height);
    }

    @Override
    protected Color getColor() {
        return Color.CHOCOLATE;
    }
}

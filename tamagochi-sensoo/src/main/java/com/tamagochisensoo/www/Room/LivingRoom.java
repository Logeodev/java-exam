package com.tamagochisensoo.www.Room;

import javafx.scene.paint.Color;

public class LivingRoom extends Room {

    public LivingRoom(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    protected Color getBackgroundColor() {
        return Color.BEIGE;
    }

}

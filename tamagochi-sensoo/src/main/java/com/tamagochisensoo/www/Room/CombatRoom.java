package com.tamagochisensoo.www.Room;

import java.io.IOException;

import com.tamagochisensoo.www.Controllers.Combat;

import javafx.scene.paint.Color;

public class CombatRoom extends Room {
    private Combat fightInstance;

    public CombatRoom(double x, double y, double width, double height) {
        super(x, y, width, height);
        
        try {
            fightInstance = new Combat(0, null, null);
            fightInstance.doCombat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Color getBackgroundColor() {
        return Color.LIGHTCORAL;
    }

}

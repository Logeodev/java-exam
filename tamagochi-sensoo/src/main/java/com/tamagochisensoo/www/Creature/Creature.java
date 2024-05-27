package com.tamagochisensoo.www.Creature;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Creature {
    private Color color;
    private CreatureShape shape;
    private double life = 100;
    private double hunger = 100;
    private double confort = 100;
    private Pane pane;

    public Creature(Color color, CreatureShape shape) {
        this.color = color;
        this.shape = shape;

        this.pane = new Pane();

        double height = 60;
        double width = 60;
        Shape displayShape;
        switch (this.shape) {
            case OVAL:
                displayShape = new Circle(400, 400, height);
                break;
            case SQUARE:
                displayShape = new Rectangle(400, 600, width, height);
                break;
            default:
                displayShape = new Rectangle();
                break;
        }
        displayShape.setFill(this.color);
        this.pane.getChildren().add(displayShape);
    }

    // --- SETTERS ---
    public void setLife(double life) {
        this.life = life;
    }
    public void setConfort(double confort) {
        this.confort = confort;
    }
    public void setHunger(double hunger) {
        this.hunger = hunger;
    }
    // --- GETTERS ---
    public Color getColor() {
        return color;
    }
    public CreatureShape getShape() {
        return shape;
    }
    public double getLife() {
        return life;
    }
    public double getHunger() {
        return hunger;
    }
    public double getConfort() {
        return confort;
    }

    // --- METHODS ---
    public void eat() {
        this.hunger += 10;
        if (this.hunger > 100) {
            this.hunger = 100;
        }
    }
    public void sleep() {
        this.life = 100;
        this.confort += 20;
        if (this.confort > 100) {
            this.confort = 100;
        }
        this.hunger -= 30;
        if (this.hunger < 0) {
            this.hunger = 0;
        }
    }
    public double getAttackPower() {
        Random rand = new Random();
        return ((this.life + this.hunger + this.confort) / 3) * (rand.nextDouble() + 0.5);
    }

    public Pane getPane() {
        return this.pane;
    }
}

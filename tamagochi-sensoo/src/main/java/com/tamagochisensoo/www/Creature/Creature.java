package com.tamagochisensoo.www.Creature;

import java.util.Random;

import com.tamagochisensoo.www.Bars.Bar;
import com.tamagochisensoo.www.Bars.ConfortBar;
import com.tamagochisensoo.www.Bars.HungerBar;
import com.tamagochisensoo.www.Bars.LifeBar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Creature {
    private Color color;
    private CreatureShape shape;
    private double life = 100;
    private double hunger = 100;
    private double confort = 100;
    private Pane pane;

    public double barsWidth = 200;
    public double barsHeigth = 20;
    private Bar[] bars = {
            new LifeBar(life, 10, 10, barsWidth, barsHeigth),
            new HungerBar(hunger, 10, 40, barsWidth, barsHeigth),
            new ConfortBar(confort, 10, 70, barsWidth, barsHeigth)
        };

    public Creature(Color color, CreatureShape shape) {
        this.color = color;
        this.shape = shape;

        this.pane = new Pane();

        // -- Main shape of creature --
        double height = 60;
        double width = 60;
        Shape displayShape = new Rectangle();
        switch (this.shape) {
            case OVAL:
                displayShape = new Circle(400, 400, height);
                break;
            case SQUARE:
                displayShape = new Rectangle(350, 400, width*2, height*2);
                break;
        }
        displayShape.setFill(this.color);
        this.pane.getChildren().add(displayShape);

        // -- Creature get eyes --
        for (int i = 0; i < 2; i++) {
            double eyeX = (i == 0) ? 370 : 430;

            Circle eyeWhite = new Circle(eyeX, 420, 10);
            eyeWhite.setFill(Color.WHITE);

            Circle eyeBlack = new Circle(eyeX, 420, 5);
            eyeBlack.setFill(Color.BLACK);

            this.pane.getChildren().addAll(eyeWhite, eyeBlack);
        }

        // -- Life is hard --
        lifeIsHard();
    }

    // --- SETTERS ---
    public void setLife(double life) {
        this.life = life;
        this.bars[0].setValue(life);
    }
    public void setConfort(double confort) {
        this.confort = confort;
        this.bars[2].setValue(confort);
    }
    public void setHunger(double hunger) {
        this.hunger = hunger;
        this.bars[1].setValue(hunger);
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
    public Bar[] getBars() {
        return bars;
    }

    // --- METHODS ---
    public void eat() {
        setHunger(hunger + 40);
        if (this.hunger > 100) {
            setHunger(100);
        }
    }
    public void sleep() {
        setLife(100);
        setConfort(confort + 60);
        if (this.confort > 100) {
            setConfort(100);
        }
        setHunger(hunger - 30);
        if (this.hunger < 0) {
            setHunger(0);
        }
    }
    public double getAttackPower() {
        Random rand = new Random();
        return ((this.life + this.hunger + this.confort) / 3) * (rand.nextDouble());
    }

    public Pane getPane() {
        return this.pane;
    }

    private void lifeIsHard() {
        Random rnd = new Random();
        Timeline timeline = new Timeline(
            new KeyFrame(
                Duration.seconds(5), 
                event -> {
                    boolean looseConfort = rnd.nextBoolean();
                    if (looseConfort) {
                        setConfort(confort - 10);
                        if (this.confort < 0) {
                            setConfort(0);
                        }
                    } else {
                        setHunger(hunger - 10);
                        if (this.hunger < 0) {
                            setHunger(0);
                        }
                    }
                    if ((this.hunger < 10 || this.confort < 8) && rnd.nextBoolean()) {
                        setLife(life - 5);
                        if (this.life < 0) {
                            setLife(0);
                        }
                    }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}

package com.tamagochisensoo.www.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tamagochisensoo.www.Controllers.Combat;
import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.FightNotCreatedException;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CombatRoom extends Room {
    private Combat fightInstance;
    private Socket socket;
    private BufferedReader reader;
    private Creature[] creatures;
    private Creature winner;
    private Creature loser;
    private BooleanProperty isFightOver;

    public CombatRoom(Creature[] creatures, double x, double y, double width, double height, Stage stage) {
        super(x, y, width, height, stage);

        this.creatures = creatures;
        this.isFightOver = new SimpleBooleanProperty(false);

        makeFightScene();
        Thread actionsThread = new Thread() {
            @Override
            public void run() {
                startCombat();
                isFightOver.set(true);
            }
        };
        actionsThread.start();
        this.isFightOver.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    actionsThread.interrupt();
                    closeCombat();
                    if (loser != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new LivingRoom(0, 0, 800, 800, loser, stage);
                            };
                        });
                    }
                }
            }
        });

        Scene scene = new Scene(pane, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public Color getBackgroundColor() {
        return Color.LIGHTCORAL;
    }

    private void makeFightScene() {
        HBox barsBox = new HBox(800);
        for (int i = 0; i < creatures.length; i++) {
            Creature creature = creatures[i];
            creature.setPosX(creature.getPosX() + Math.pow(-1, i) * 300);
            pane.getChildren().add(creature.getPane());
            barsBox.getChildren().add(creature.getBars()[0].getPane());
        }
        barsBox.setSpacing(350);
        pane.getChildren().add(barsBox);
    }
    
    public void startCombat() {
        int servPort = 9254;
        try {
            fightInstance = new Combat(servPort, creatures[0], creatures[1]);
            socket = new Socket("localhost", servPort);
            reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
            fightInstance.start();

            String line;
            double winnerId = -1;
            while (
                (line = reader.readLine()) != null
            ) {
                System.out.println(line);
                if (line.contains("WIN")) {
                    winnerId = Double.parseDouble(line.substring(4));
                }
            }

            if (creatures[0].getId() == winnerId) {
                winner = creatures[0];
                loser = creatures[1];
            } else {
                winner = creatures[1];
                loser = creatures[0];
            }
            System.out.println(winner);
            
        } catch (FightNotCreatedException fnf) {

        } catch (UnknownHostException uhe) {

        } catch (IOException ioe) {

        }
    }

    private void closeCombat() {
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fightInstance = null;
    }
}

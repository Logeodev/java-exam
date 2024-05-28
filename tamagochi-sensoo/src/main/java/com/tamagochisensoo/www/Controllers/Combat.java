package com.tamagochisensoo.www.Controllers;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.tamagochisensoo.www.Creature.Creature;

public class Combat {
    private ServerSocket server;
    private Socket[] sockets = new Socket[2];
    private BufferedReader[] ins = new BufferedReader[2];
    private PrintWriter[] outs = new PrintWriter[2];
    private List<Creature> adversaries;

    public Combat(int port, Creature adv1, Creature adv2) throws IOException {
        this.server = new ServerSocket(port);
        this.adversaries = new ArrayList<>();
        adversaries.add(adv1);
        adversaries.add(adv2);
    }

    public void doCombat() throws IOException {
        for (int i = 0; i < adversaries.size(); i++) {
            sockets[i] = server.accept();
            ins[i] = new BufferedReader(
                new InputStreamReader(
                    sockets[i].getInputStream()
                )
            );
            outs[i] = new PrintWriter(
                sockets[i].getOutputStream(), true
            );
        }

        while (!somebodyDied()) {
            combatStep();
        }

        finishCombat();

        for (int i = 0; i < 2; i++) {
            sockets[i].close();
        }
        server.close();
    }

    public void combatStep() {
        for (int i = 0; i < 2; i++) {
            double power = adversaries.get(i).getAttackPower();
            outs[i].println("Power = " + power);
            adversaries.get((i+1)%2).setLife(
                adversaries.get((i+1)%2).getLife() - power
            );
        }
    }

    public void finishCombat() {
        Creature winner = adversaries
            .stream()
            .filter(adv -> adv.getLife() > 0)
            .findFirst()
            .get();
        Creature loser = adversaries
            .stream()
            .filter(adv -> adv.getLife() <= 0)
            .findFirst()
            .get();
        winner.setConfort(100);
        loser.setConfort(1);
        loser.setHunger(1);

        outs[adversaries.indexOf(loser)].println("You lost.");
        outs[adversaries.indexOf(winner)].println("You won !");
    }

    private boolean somebodyDied () {
        return adversaries.stream().anyMatch(adv -> adv.getLife() <= 0);
    }
}
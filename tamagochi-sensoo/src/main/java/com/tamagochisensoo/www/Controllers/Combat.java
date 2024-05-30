package com.tamagochisensoo.www.Controllers;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tamagochisensoo.www.Creature.Creature;
import com.tamagochisensoo.www.Exceptions.FightNotCreatedException;
import com.tamagochisensoo.www.Exceptions.NoConfigFileException;
import com.tamagochisensoo.www.JDBC.daos.CreatureDao;
import com.tamagochisensoo.www.JDBC.daos.Win;
import com.tamagochisensoo.www.JDBC.daos.WinDao;

public class Combat extends Thread {
    private ServerSocket server;
    private Socket socket;
    private PrintWriter out;
    private List<Creature> adversaries;

    public Combat(int port, Creature adv1, Creature adv2) throws FightNotCreatedException {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new FightNotCreatedException(Integer.toString(port));
        }
        this.adversaries = new ArrayList<>();
        adversaries.add(adv1);
        adversaries.add(adv2);
    }

    public void doCombat() throws IOException {
        socket = server.accept();
        out = new PrintWriter(
            socket.getOutputStream(), true
        );

        while (!somebodyDied()) {
            combatStep();
        }

        finishCombat();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        socket.close();
        server.close();
    }

    public void combatStep() {
        for (int i = 0; i < 2; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double power = adversaries.get(i).getAttackPower();
            out.println("Power = " + power);
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
            loser.setLife(0);

        out.println("WIN " + winner.getId());
        CreatureDao saver;
        try {
            saver = new CreatureDao();
            saver.save(winner);
            saver.save(loser);
        } catch (NoConfigFileException e) {
            e.printStackTrace();
        }
        WinDao scoreSaver;
        try {
            scoreSaver = new WinDao();
            Win w = new Win();
            w.creature_id = winner.getId();
            w.date_win = Date.valueOf(LocalDate.now());
            scoreSaver.addWin(w);
        } catch (NoConfigFileException e) {
            e.printStackTrace();
        }
    }

    private boolean somebodyDied () {
        return adversaries.stream().anyMatch(adv -> adv.getLife() <= 0);
    }

    @Override
    public void run() {
        try {
            doCombat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
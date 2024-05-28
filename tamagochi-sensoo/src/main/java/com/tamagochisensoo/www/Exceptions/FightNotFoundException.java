package com.tamagochisensoo.www.Exceptions;

public class FightNotFoundException extends Exception {
    public FightNotFoundException(String server) {
        super("Fight Not Found :\nNo connection to " + server + " could be established.");
    }
}

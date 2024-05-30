package com.tamagochisensoo.www.Exceptions;

public class FightNotCreatedException extends Exception {
    public FightNotCreatedException(String server) {
        super("Fight Not Found :\nNo connection to " + server + " could be established.");
    }
}

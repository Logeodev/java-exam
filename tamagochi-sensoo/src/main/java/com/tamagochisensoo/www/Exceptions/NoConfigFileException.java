package com.tamagochisensoo.www.Exceptions;

public class NoConfigFileException extends Exception {
    public NoConfigFileException(String message) {
        super("config.ini couldn't be used.\nReason : " + message);
    }
}

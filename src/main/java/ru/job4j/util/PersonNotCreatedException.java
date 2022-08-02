package ru.job4j.util;

public class PersonNotCreatedException extends RuntimeException {
    public PersonNotCreatedException(String mes) {
        super(mes);
    }
}

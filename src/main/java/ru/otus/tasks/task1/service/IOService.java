package ru.otus.tasks.task1.service;

public interface IOService {
    String askStr();
    int askInt();
    void printMessage(String str);
    void printString(String str);
    void printlnString(String str);
    void close();
}
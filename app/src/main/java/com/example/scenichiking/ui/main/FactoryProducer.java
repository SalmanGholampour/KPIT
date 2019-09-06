package com.example.scenichiking.ui.main;

public class FactoryProducer {
    public static AbstractFactory getFactory(boolean green) {
        if (green) {
            return new GreenOptionFactory();
        } else {
            return new OptionFactory();
        }
    }
}

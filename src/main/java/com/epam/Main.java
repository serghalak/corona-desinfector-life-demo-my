package com.epam;

public class Main {
    public static void main(String[] args) {
        CoronaDesinfector desinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class); // new CoronaDesinfector();
        desinfector.start(new Room());
    }
}

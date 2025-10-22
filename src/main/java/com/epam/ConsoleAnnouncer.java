package com.epam;

public class ConsoleAnnouncer implements Announcer {

    //private Recommendator recommendator = ObjectFactory.getInstance().createObject(Recommendator.class);
    @InjectByType
    private Recommendator recommendator;

    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}

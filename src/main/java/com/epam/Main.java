package com.epam;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //CoronaDesinfector desinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class); // new CoronaDesinfector();
        ApplicationContext context = Application.run("com.epam", new HashMap<>(Map.of(Policement.class, AngryPolicement.class)));
        CoronaDesinfector desinfector = context.getObject(CoronaDesinfector.class);
        desinfector.start(new Room());
    }
}

package com.epam;

public class RecommendatorImpl implements Recommendator {

    @InjectProperty
    private String alcohol;

    @Override
    public void recommend() {
        System.out.println("to protect from covid-2019, drink "+alcohol);
    }
}

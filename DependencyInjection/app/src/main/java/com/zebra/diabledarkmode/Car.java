package com.zebra.diabledarkmode;

import javax.inject.Inject;

public class Car {
    Engine engine;

    @Inject
    public Car(Engine engine) {
        this.engine=engine;
    }

    public void start()
    {
        engine.run();
    }

}

package com.zebra.diabledarkmode;

import javax.inject.Inject;

public class Engine {
    Piston piston;
    @Inject
    public Engine(Piston piston) {
        this.piston = piston;
    }
    void run()
    {
      piston.start();
    }
}

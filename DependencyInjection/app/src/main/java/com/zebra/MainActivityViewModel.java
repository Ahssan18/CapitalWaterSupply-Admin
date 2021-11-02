package com.zebra;

import com.zebra.diabledarkmode.Car;

import javax.inject.Inject;

public class MainActivityViewModel {

    Car car;
    @Inject
    public MainActivityViewModel(Car car) {
        this.car=car;
    }
    public void testDrive()
    {
        car.start();
    }
}

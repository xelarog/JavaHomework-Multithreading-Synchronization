package ru.netology;


import java.util.ArrayList;
import java.util.List;

public class CarShowroom {
    private int carSoldCount;
    private final int carLimit;

    Seller seller = new Seller(this);
    List<Car> cars;

    public CarShowroom(int carLimit) {
        this.carLimit = carLimit;
        cars = new ArrayList<>(carLimit);
    }

    public Car sellCar() {
        return seller.sellCar();
    }

    public void acceptCar() {
        seller.recieveCar();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void CarSoldCount() {
        carSoldCount++;
    }

    public int getCarSoldCount() {
        return carSoldCount;
    }

    public int getCarLimit() {
        return carLimit;
    }


}

package ru.netology;


import java.util.ArrayList;
import java.util.List;

public class CarShowroom {

    private final int carLimit;
    private int carSoldCount;

    private Seller seller = new Seller(this);
    private List<Car> cars;

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

    public void noMoreCars() {
        seller.endCarsNotify();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void carSoldCount() {
        carSoldCount++;
    }

    public int getCarSoldCount() {
        return carSoldCount;
    }

    public int getCarLimit() {
        return carLimit;
    }


}

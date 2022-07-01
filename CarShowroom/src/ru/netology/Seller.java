package ru.netology;

public class Seller {
    private final int SELL_CAR_TIME = 1000;
    private final int PRODUCTION_TIME = 3000;

    private CarShowroom carShowroom;

    public Seller(CarShowroom carShowroom) {
        this.carShowroom = carShowroom;
    }

    public synchronized Car sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " зашёл в автосалон");
            while (carShowroom.getCars().size() == 0) {
                System.out.println("Машин нет");
                wait();
            }
            Thread.sleep(SELL_CAR_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            carShowroom.CarSoldCount();
            System.out.println("машин продано - " + carShowroom.getCarSoldCount());
            return carShowroom.getCars().remove(0);
        } catch (InterruptedException e) {
        }
        return null;
    }

    public synchronized void recieveCar() {
        try {
            Thread.sleep(PRODUCTION_TIME);
            System.out.println("Прозводитель выпустил 1 авто");
            carShowroom.getCars().add(new Car());
            notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


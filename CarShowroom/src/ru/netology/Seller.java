package ru.netology;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private final int SELL_CAR_TIME = 1000;
    private final int PRODUCTION_TIME = 3000;

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition carCondition = lock.newCondition();

    private final CarShowroom carShowroom;

    public Seller(CarShowroom carShowroom) {
        this.carShowroom = carShowroom;
    }

    public Car sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашёл в автосалон");
            while (carShowroom.getCars().size() == 0) {
                System.out.println("Машин нет");
                carCondition.await();
            }
            Thread.sleep(SELL_CAR_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            carShowroom.CarSoldCount();
            System.out.println("машин продано - " + carShowroom.getCarSoldCount());
            return carShowroom.getCars().remove(0);
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void recieveCar() {
        try {
            lock.lock();
            Thread.sleep(PRODUCTION_TIME);
            System.out.println("Прозводитель выпустил 1 авто");
            carShowroom.getCars().add(new Car());
            carCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


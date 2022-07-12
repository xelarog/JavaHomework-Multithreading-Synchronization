package ru.netology;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {

    private final int sellCarTime = 1000;
    private final int productionTime = 3000;

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
                if (carShowroom.getCarSoldCount() == carShowroom.getCarLimit())
                    return null;
                System.out.println("Машин нет");
                carCondition.await();
            }
            Thread.sleep(sellCarTime);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            carShowroom.carSoldCount();
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
            Thread.sleep(productionTime);
            System.out.println("Производитель выпустил 1 авто");
            lock.lock();
            carShowroom.getCars().add(new Car());
            carCondition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void endCarsNotify() {
        lock.lock();
        carCondition.signal();
        lock.unlock();
    }
}


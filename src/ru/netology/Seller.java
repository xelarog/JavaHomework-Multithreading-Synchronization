package ru.netology;

public class Seller {

    private final int sellCarTime = 1000;
    private final int productionTime = 3000;

    private final CarShowroom carShowroom;

    public Seller(CarShowroom carShowroom) {
        this.carShowroom = carShowroom;
    }

    public synchronized Car sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " зашёл в автосалон");
            while (carShowroom.getCars().size() == 0) {
               if (carShowroom.getCarSoldCount() == carShowroom.getCarLimit())
                   return null;
                System.out.println("Машин нет");
                wait();
            }
            Thread.sleep(sellCarTime);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            carShowroom.carSoldCount();
            System.out.println("машин продано - " + carShowroom.getCarSoldCount());
            return carShowroom.getCars().remove(0);
        } catch (InterruptedException e) {
        }
        return null;
    }

    public void recieveCar() {
        try {
            Thread.sleep(productionTime);
            System.out.println("Производитель выпустил 1 авто");
            synchronized (this) {
                carShowroom.getCars().add(new Car());
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void endCarsNotify() {
        notifyAll();
    }
}


package ru.netology;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        final int delay1 = 1000;
        final int delay3 = 3000;
        final int carLimit = 10;

        final CarShowroom carShowroom = new CarShowroom(carLimit);

        Thread buyer1 = null;
        Thread buyer2 = null;
        Thread buyer3 = null;
        Thread producer;

        boolean canBuy1 = true;
        boolean canBuy2 = true;
        boolean canBuy3 = true;
        int productionCount = 0;

        while (carShowroom.getCarSoldCount() < carLimit) {
            if (canBuy1) {
                buyer1 = new Thread(null, carShowroom::sellCar, "Покупатель 1");
                buyer1.start();
                canBuy1 = false;
            }
            if (canBuy2) {
                buyer2 = new Thread(null, carShowroom::sellCar, "Покупатель 2");
                buyer2.start();
                canBuy2 = false;
            }
            if (canBuy3) {
                buyer3 = new Thread(null, carShowroom::sellCar, "Покупатель 3");
                buyer3.start();
                canBuy3 = false;
            }
            Thread.sleep(delay1);

            if (productionCount < carLimit) {
                producer = new Thread(null, carShowroom::acceptCar, "Производитель");
                producer.start();
                productionCount++;
            }
            Thread.sleep(delay3);

            if (!buyer1.isAlive())
                canBuy1 = true;
            if (!buyer2.isAlive())
                canBuy2 = true;
            if (!buyer3.isAlive())
                canBuy3 = true;

        }
        System.out.println("Все машины проданы, поставок не ожидается.");

        while (buyer1.isAlive() || buyer2.isAlive() || buyer3.isAlive()) {
            carShowroom.noMoreCars();
        }

    }
}

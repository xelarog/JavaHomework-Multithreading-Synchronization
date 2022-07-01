package ru.netology;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int DELAY1 = 1000;
        final int DELAY3 = 3000;
        final int CAR_LIMIT = 10;

        final CarShowroom carShowroom = new CarShowroom(CAR_LIMIT);

        Thread buyer1 = new Thread(null, carShowroom::sellCar, "Покупатель 1");
        Thread buyer2 = new Thread(null, carShowroom::sellCar, "Покупатель 2");
        Thread buyer3 = new Thread(null, carShowroom::sellCar, "Покупатель 3");

        Thread producer;
        int productionCount = 0;
        while (carShowroom.getCarSoldCount() < CAR_LIMIT) {
            if (!buyer1.isAlive()) {
                buyer1 = new Thread(null, carShowroom::sellCar, "Покупатель 1");
                buyer1.start();
            }
            if (!buyer2.isAlive()) {
                buyer2 = new Thread(null, carShowroom::sellCar, "Покупатель 2");
                buyer2.start();
            }
            if (!buyer3.isAlive()) {
                buyer3 = new Thread(null, carShowroom::sellCar, "Покупатель 3");
                buyer3.start();
            }
            Thread.sleep(DELAY1);

            if (productionCount < CAR_LIMIT) {
                producer = new Thread(null, carShowroom::acceptCar, "Производитель");
                producer.start();
                productionCount++;
            }
            Thread.sleep(DELAY3);
        }
        System.out.println("Все машины проданы, поставок не ожидается.");

        if (buyer1.isAlive())
            buyer1.interrupt();
        if (buyer2.isAlive())
            buyer2.interrupt();
        if (buyer3.isAlive())
            buyer3.interrupt();
    }
}

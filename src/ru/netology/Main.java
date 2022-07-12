package ru.netology;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        final int delay1 = 1000;
        final int delay3 = 3000;
        final int carLimit = 10;
        final String[] buyerNames = {"Покупатель 1", "Покупатель 2", "Покупатель 3"};

        final CarShowroom carShowroom = new CarShowroom(carLimit);

        Thread[] buyers = {null, null, null};
        Thread producer;

        boolean[] canBuy = {true, true, true};
        int productionCount = 0;

        while (carShowroom.getCarSoldCount() < carLimit) {
            for (int i = 0; i < canBuy.length; i++) {
                if (canBuy[i]) {
                    buyers[i] = new Thread(null, carShowroom::sellCar, buyerNames[i]);
                    buyers[i].start();
                    canBuy[i] = false;
                }
            }
            Thread.sleep(delay1);

            if (productionCount < carLimit) {
                producer = new Thread(null, carShowroom::acceptCar, "Производитель");
                producer.start();
                productionCount++;
            }
            Thread.sleep(delay3);

            for (int i = 0; i < buyers.length; i++) {
                if (!buyers[i].isAlive())
                    canBuy[i] = true;
            }
        }
        System.out.println("Все машины проданы, поставок не ожидается.");

        for (Thread buyer : buyers) {
            if (buyer.isAlive())
                carShowroom.noMoreCars();
        }
    }
}


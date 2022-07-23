package ru.netology;


public class Main {

    private static final int DELAY_1 = 1000;
    private static final int DELAY_3 = 3000;
    private static final int CAR_LIMIT = 10;

    public static void main(String[] args) throws InterruptedException {

        final String[] buyerNames = {"Покупатель 1", "Покупатель 2", "Покупатель 3"};
        final CarShowroom carShowroom = new CarShowroom(CAR_LIMIT);

        Thread[] buyers = {null, null, null};
        Thread producer;

        boolean[] canBuy = {true, true, true};
        int productionCount = 0;

        while (carShowroom.getCarSoldCount() < CAR_LIMIT) {
            for (int i = 0; i < canBuy.length; i++) {
                if (canBuy[i]) {
                    buyers[i] = new Thread(null, carShowroom::sellCar, buyerNames[i]);
                    buyers[i].start();
                    canBuy[i] = false;
                }
            }
            Thread.sleep(DELAY_1);

            if (productionCount < CAR_LIMIT) {
                producer = new Thread(null, carShowroom::acceptCar, "Производитель");
                producer.start();
                productionCount++;
            }
            Thread.sleep(DELAY_3);

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


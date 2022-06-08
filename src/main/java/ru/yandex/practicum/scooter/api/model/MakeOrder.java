package ru.yandex.practicum.scooter.api.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public class Order {

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
    }

    public static String generatePhoneNumber(){
        int num1, num2, num3;
        String set1, set2, set3;
        String phoneNumber;

        Random generator = new Random();

        num1 = generator.nextInt(7) + 1;
        num2 = generator.nextInt(8);
        num3 = generator.nextInt(8);
        set1 = Integer.toString(num1) + Integer.toString(num2) + Integer.toString(num3);
        set2 = Integer.toString(generator.nextInt(643) + 100);
        set3 = Integer.toString(generator.nextInt(8999) + 1000);

        return phoneNumber = "7" + set1 + set2 + set3;
    }

    public static Order createOrder(String[] color) {
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(7);
        String address = RandomStringUtils.randomAlphabetic(15);
        String phone = generatePhoneNumber();
        Random generator = new Random();
        int rentTime = generator.nextInt(4)+1;
        String metroStation = Integer.toString(generator.nextInt(50));
        String deliveryDate = "2022-" + Integer.toString(generator.nextInt(11)+1) + "-" + Integer.toString(generator.nextInt(29)+1);
        String comment = RandomStringUtils.randomAlphabetic(15);
        return new Order(firstName,lastName,address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}

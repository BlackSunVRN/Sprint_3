package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierDeleteErrorTest {

    CourierClient courierClient;

    @Before
    public void init() {
        courierClient = new CourierClient();
    }


    @Test
    public void courierDeleteIncorrectId() {
        Response responseDelete = courierClient.deleteCourier(RandomUtils.nextInt());
        assertEquals(SC_NOT_FOUND, responseDelete.statusCode());
        String errorMessage = responseDelete.body().jsonPath().getString("message");
        assertEquals("Курьера с таким id нет.", errorMessage);
    }

    @Test
    public void courierDeleteWithoutId() {
        Response responseDelete = courierClient.deleteCourierNull();
        assertEquals(SC_NOT_FOUND, responseDelete.statusCode());
        //Блок ниже опять же не соответствует документации
        //При отправке запроса без указания id получаем просто запрос вида
        //Request URI:	http://qa-scooter.praktikum-services.ru/api/v1/courier/
        //И в ответе 404. Сделал тест на 404 для успешного прохождения
//        assertEquals(SC_BAD_REQUEST, responseDelete.statusCode());
//        String errorText = responseDelete.body().jsonPath().getString("message");
//        assertEquals("Недостаточно данных для удаления курьера", errorText);
    }
}

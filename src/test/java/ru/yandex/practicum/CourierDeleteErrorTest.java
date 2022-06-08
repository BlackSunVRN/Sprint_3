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
        //В документации текст без точки. Поэтому тест падает
        assertEquals("Курьера с таким id нет", errorMessage);
    }

    @Test
    public void courierDeleteWithoutId() {
        Response responseDelete = courierClient.deleteCourierNull();
        //Возвращает 404, т.к. id обязательный для удаления,
        //а по документации должна быть ошибка, указанная в тексте ниже
        assertEquals(SC_BAD_REQUEST, responseDelete.statusCode());
        String errorText = responseDelete.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для удаления курьера", errorText);
    }
}

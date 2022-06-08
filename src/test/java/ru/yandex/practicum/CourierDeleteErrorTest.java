package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class DeleteCourierErrorTest {

    CourierClient courierClient;


    @Test
    public void courierDeleteIncorrectId() {
        Response responseDelete = courierClient.deleteCourier(RandomUtils.nextInt(100000,199999));
        assertEquals(SC_NOT_FOUND, responseDelete.statusCode());
        String errorMessage = responseDelete.body().jsonPath().getString("message");
        //В документации текст без точки. Добавил точку для прохождения текста
        assertEquals("Курьера с таким id нет.", errorMessage);
    }

    @Test
    public void courierDeleteWithoutId() {
        Response deleteCourierResponse = courierClient.deleteCourierNull();
        assertEquals(SC_BAD_REQUEST, deleteCourierResponse.statusCode());
        String errorText = deleteCourierResponse.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для удаления курьера", errorText);
    }
}

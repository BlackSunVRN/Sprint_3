package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierErrorTest {
    int courierId;
    CourierClient courierClient;
    Courier courier;

    @Before
    public void init() {
        courierClient = new CourierClient();
        courier = getRandomCourier();
    }
    @Test
    public void courierCreateWithoutPassword() {
        //Создание курьера без указания пароля и проверка ответа
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), "", "Test Name");
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        String errorText = responseCreate.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для создания учетной записи", errorText);
    }

    @Test
    public void courierLoginUnregistered() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Учетная запись не найдена", errorText);
    }

    @Test
    public void courierLoginWithoutPassword() {
        Response responseCreate = courierClient.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), "");
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Недостаточно данных для входа", errorText);

        CourierCredentials courierCredentialsCorrect = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLoginCorrect = courierClient.login(courierCredentialsCorrect);
        courierId = responseLoginCorrect.body().jsonPath().getInt("id");

        courierClient.deleteCourier(courierId);
    }

    @Test
    public void courierLoginWithIncorrectPassword(){
        Response responseCreate = courierClient.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphabetic(10));
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        String errorText = responseLogin.body().jsonPath().getString("message");
        assertEquals("Учетная запись не найдена", errorText);

        CourierCredentials courierCredentialsCorrect = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLoginCorrect = courierClient.login(courierCredentialsCorrect);
        courierId = responseLoginCorrect.body().jsonPath().getInt("id");

        courierClient.deleteCourier(courierId);
    }
}
